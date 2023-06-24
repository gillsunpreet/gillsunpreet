/* gopipe.c
 *
 * CSC 360, Summer 2023
 *
 * Execute up to four instructions, piping the output of each into the
 * input of the next.
 *
 * 
 *
 * Student name: Sunpreet Singh
 * Student Id: V00997303
 * date of Submission: 2023-06-04 
 */


/* Note: The following are the **ONLY** header files you are
 * permitted to use for this assignment! */

#include <unistd.h>
#include <string.h>
#include <wait.h>
//#include <stdio.h>
#include <stdlib.h>

#define MAX_COMMANDS 4
#define MAXIMUM_ARGS 8
#define MAXIMUM_LINE_LENGTH 80


int main() {
    /* Array to store file descriptors of commands on both ends of      
     * pipe,  one end for writing and the other end for reading
     */
    int pipe_fds[2 * MAX_COMMANDS];
   
    //2d array used to read the commands from user input 
    char *commands[MAX_COMMANDS][MAXIMUM_ARGS];
    char line_buffer[MAXIMUM_LINE_LENGTH];//buffer used to store data
    int command_counter = 0;
    //gettig commands from user
    /*
     * Now in this for loop if a user presses enter without entering
     * any command then a newline character will be read 
     * and passed to standard input, in this case the program would
     * not read any commands and program woulld end smoothly
     * also if enter was not pressed first then it will keep getting
     * 4 commands from the user and woud keep tokenizinf the comands 
     * and storing in the command array
    
     */
    int should_quit = 0;
    for (int i = 0; i<MAX_COMMANDS; i++){
        //memset making sure linebuffer is clean before reading 
        memset(line_buffer, 0, sizeof(line_buffer)); 
        ssize_t num_read = read(0, line_buffer, MAXIMUM_LINE_LENGTH);
        if(num_read <= 0 || line_buffer[0] == '\n'){
            should_quit = 1; //set the flag because enter was pressed
            break;
        }
        
   
  //Tokenize commands and store them in the commands array
    char *token = strtok(line_buffer, " \n");
    int arg_counter = 0;
    while (token != NULL && arg_counter < MAXIMUM_ARGS) {
        size_t token_length = strlen(token);
        if (token[token_length - 1] == '\n') {
            token[token_length - 1] = '\0'; // Remove newline character
        }
        
        // Allocate memory for the token and store it in commands array
        commands[command_counter][arg_counter] = malloc(strlen(token) + 1);
        strcpy(commands[command_counter][arg_counter], token);
        
        token = strtok(NULL, " \n");
        arg_counter++;
    }

    commands[command_counter][arg_counter] = NULL; // Mark the end of arguments
    command_counter++;
    }
    
    if (should_quit){//flag for checking if enter was pressed
     return 0;   
    }
    
    
    //create pipe for each command but the last so 3 pipes and 4 commands in total 
   for(int i = 0; i < command_counter -1; i++){
        //pipe fds points to the starting location of ppefd array
        if(pipe(pipe_fds + i*2) < 0 ){
            const char* error_message = "Failed to create a pipe.\n";
            write(2, error_message, strlen(error_message));
            _exit(1);
        }
        
    }
     // Now create child process to execute commands
    for(int i=0; i < command_counter; i++){
        
        pid_t pid;//process id
        pid = fork();//creates a child process 
        if(pid < 0){
            const char* child_error = "Fork process failed.\n";
            write(2, child_error, strlen(child_error) );
            _exit(1);
        }
        
        //Now if its not the first command then it needs to take its input from previous command
         // If its not the last command then it needs to pass its output the the next Command
        
        if(pid == 0){//in child process
            if(i != 0){
                if(dup2(pipe_fds[(i-1)*2] , 0) < 0){//replace stdin of current command with read end of pipe 
                    _exit(1);
                }
                
            }
            // now replace stdout of command with write end of pipe so that the next command can read the output later
            if(i != command_counter -1){
                if( dup2(pipe_fds[i*2 + 1],1) < 0){//i*2+ 1indicating the current comamand
                    _exit(1);
                }
            }
            
            //now closing all the pipe file descriptors that were opened, done after we have finsished creating
            //any child processes and setting up their pipes
            for(int j = 0; j < 2*command_counter;j++){
                close(pipe_fds[j]);
            }
            /* now exece the command
            *  command i points to the command[i][0] which is the name of my command
            *  command[i] is the command itself with its arguments and NULL indicating the end
            *  execvp("/usr/bin/gcc", ["/usr/bin/gcc", "--help", NULL])
            */
           
            if(execvp(*commands[i], commands[i]) < 0){//if theres an error
                _exit(1);
            }
        }
    
    }
    
    for(int i = 0; i<command_counter-1; i++){
         close(pipe_fds[i*2+1]);//close the write end 
    }
    
    //close the write end of last command, done separetely to signal that no more output is coming now
    close(pipe_fds[(command_counter-1) * 2 + 1]);
     
    // Read and display output of the last command
    
    char final_output[1024];
    int total_num = read(pipe_fds[(command_counter - 2) * 2], final_output, sizeof(final_output) - 1);
    if (total_num < 0) {
        write(2, "Error reading from pipe.\n", 25);
        _exit(1);
    }
    final_output[total_num] = '\0'; // Add null terminator
    
    write(1, final_output, strlen(final_output));
                
    close(pipe_fds[(command_counter-1)*2]);//close read end of last pipe
    
    for(int i = 0; i < command_counter; i++){
        wait(NULL);//waiting for all child processes to finish
    }
     
    //Free the allocated memeory for the command array
    for(int i = 0; i < command_counter; i++){
        for(int j = 0; commands[i][j] != NULL; j++){
            free(commands[i][j]);
        }
    }
    //char *message = "Program is finished executing.\n";
    //write(1, message, strlen(message));
    return 0;
    
}
