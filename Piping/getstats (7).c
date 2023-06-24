/* getstats.c 
 *
 * CSC 360, Summer 2023
 *
 * - If run without an argument, dumps information about the PC to STDOUT.
 *
 * - If run with a process number created by the current user, 
 *   dumps information about that process to STDOUT.
 *
 *
 * Student name: Sunpreet Singh
 * Student Id: V00997303
 * date of Submission: 2023-06-04 
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>


/*
 * Function that prints required information when the get stats file is run with a process number
 * Also when the get stats file is run with an invalid process number it prints the process number and
 * it says that the process number is not found 

*/
void print_process_info(char * process_num) {
    char file_path[50];//stores the file path of the /proc file to be read 
    char line_buffer[100];//buffer to read and store each line from the file
    FILE *file;//pointer to the file being read
    
    // Check if process number exists
    sprintf(file_path, "/proc/%s/comm", process_num);
    file = fopen(file_path, "r");
    if (!file) {
        printf("Process number %s not found.\n", process_num);
        exit(1);
    }
    fclose(file);

    // Process number
    printf("Process number: %s\n", process_num);

    // Process name
    sprintf(file_path, "/proc/%s/comm", process_num);
    file = fopen(file_path, "r");
    if (!file) {
        printf("Error opening file.\n");
        exit(1);
    }

    fgets(line_buffer, sizeof(line_buffer), file);
    printf("Name: %s", line_buffer);
    fclose(file);
    
    //file name if any
    sprintf(file_path, "/proc/%s/cmdline", process_num);
    file = fopen(file_path,"r");
    if(file == NULL){
        printf("Process number %s not found.\n", process_num);
        exit(1);
    }
    while(fgets(line_buffer, sizeof(line_buffer),file)){
        //find the first occurence of -
        char *p = strchr(line_buffer, '-');
        printf("Filename (if any): ");
        //if "-" is located
        if(p){
            //locate the position of "-" in the string
            int hyphen_index = p - line_buffer;
            //now print everything that comes befor "-"
            for(int i = 0; i < hyphen_index; i++){
                //print every charcater before "-"
                printf("%c", line_buffer[i]);
                break;
            }
        }
        else{
            printf("%s", line_buffer);//print the entire line if cant locate "-"
        }
    }
    fclose(file);
    
    // Number of Threads
    sprintf(file_path, "/proc/%s/status", process_num);
    file = fopen(file_path,"r");
    if(file == NULL){
        printf("Process number %s not found.\n", process_num);
        exit(1);
    }
    while(fgets(line_buffer, sizeof(line_buffer),file)){
        if(strncmp(line_buffer, "Threads:", 8) == 0){
            printf("\n%s", line_buffer);
            break;
        }
    }
    fclose(file);
    
    //Total context switches
    /*
     * First i am going to open the status file in /proc/process_number
     * This file is going to have something like
     * voluntary_ctxt_switches:        15890
     * nonvoluntary_ctxt_switches:     2
     * I am going to reads these two lines and add up the the integer value in
     * the string and print the total number of switches
     */
    sprintf(file_path, "/proc/%s/status", process_num);
    file = fopen(file_path, "r");
    if(!file){
        printf("Process number %s not found.\n", process_num);
        exit(1);
    }
    int total_switches = 0;
    char *context_switch;
    
    while(fgets(line_buffer, sizeof(line_buffer), file)){
        if(strncmp(line_buffer, "voluntary_ctxt_switches:", 24) == 0){
            context_switch = strstr(line_buffer, ":");
            if(context_switch!=NULL){
                context_switch++;
                while(*context_switch == ' '){
                    context_switch++;
                }
                total_switches += atoi(context_switch);
            }
        }
        if(strncmp(line_buffer,"nonvoluntary_ctxt_switches:", 27) == 0){
           context_switch = strstr(line_buffer, ":");
            if(context_switch!=NULL){
                context_switch++;
                while(*context_switch == ' '){
                    context_switch++;
                }
                total_switches += atoi(context_switch);
            }
        }
   
    
    } 
      printf("Total context switches: %d\n", total_switches);
      fclose(file);
}

/*
 * This is the function that prints required information when the getstats file is run with no
 * arguments.
*/
void print_full_info() {
    char line_buffer[100];//buffer to read and store each line from the file
    FILE *file;//pointer to the file being read
    
    //model name
    file = fopen("/proc/cpuinfo","r");
    //now start reading
   if(file == NULL){
         printf("No file to read From! Please open the file to read");
        exit(1);
    }
    while(fgets(line_buffer, sizeof(line_buffer),file)){
        if(strncmp(line_buffer, "model name", 10) == 0){
            printf("%s", line_buffer);
            break;
        }
    }
    fclose(file);
    
    //Cpu cores
    file = fopen("/proc/cpuinfo","r");
   if(file == NULL){
        printf("No file to read From! Please open the file to read");
        exit(1);
    }
    //start reading now and match the strings using strncmp and print the current line being read
    while(fgets(line_buffer, sizeof(line_buffer),file)){
        if(strncmp(line_buffer, "cpu cores", 9) == 0){
            printf("%s", line_buffer);
            break;
        }
    }
    fclose(file);//close the file
    
    //linux version
    file = fopen("/proc/version","r");
    if(file == NULL){
        printf("No file to read From! Please open the file to read");
        exit(1);
    }
    //start reading now and match the strings using strncmp and print the current line being read
    else{
    fgets(line_buffer, sizeof(line_buffer),file);
    printf("%s\n", line_buffer);
    fclose(file);//close the file
    }
    //mem totals
    
    file = fopen("/proc/meminfo","r");
    if(file == NULL){
        printf("No file to read From! Please open the file to read");
        exit(1);
    }
    
    while(fgets(line_buffer, sizeof(line_buffer),file)){
        if(strncmp(line_buffer, "MemTotal:", 9) == 0){
            printf("%s", line_buffer);
            break;
        }
    }
    fclose(file);//close the file
    
    //uptime
    file = fopen("/proc/uptime","r");
    if(file == NULL){
        printf("No file to read From! Please open the file to read");
        exit(1);
    }
    /* start reading now and match the strings using strncmp and print the current line being read
     * When the line in the file uptime is read, i am going to convert that line to integer using atoi
     * and then will display the integer as days, hours, minutes, seconds as shown in the assignment
     * description
     */
    else{
        fgets(line_buffer, sizeof(line_buffer), file);
        //concert the line read to integer
        int uptime = atoi(line_buffer);
        printf("Uptime: %d days, %d hours, %d minutes, %d seconds\n", uptime/60/60/24, uptime/60/60%24, 
              uptime/60%60, uptime%60);
        fclose(file);
    }

}

/*
 * Main function which calls two functions as required 
 * when theres an argument with the getstats file then then the print_process_info function is called
 * and when there's no argument then the  print_full_info function is called
*/
int main(int argc, char ** argv) {  
    if (argc == 1) {
        print_full_info();
    } else {
        print_process_info(argv[1]);
    }
}
