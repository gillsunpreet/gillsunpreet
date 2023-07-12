/*
 * kosmos-mcv.c (mutexes & condition variables)
 *
 * UVic CSC 360, Summer 2023
 *
 * Student name = Sunpreet Singh
 * Student number = V00997303
 * Date Submitted = 2023-06-29 
 * Here is some code from which to start.
 *
 * PLEASE FOLLOW THE INSTRUCTIONS REGARDING WHERE YOU ARE PERMITTED
 * TO ADD OR CHANGE THIS CODE. Read from line 133 onwards for
 * this information.
 */

#include <assert.h>
#include <pthread.h>
#include <sched.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "logging.h"


/* Random # below threshold indicates H; otherwise C. */
#define ATOM_THRESHOLD 0.55
#define DEFAULT_NUM_ATOMS 40

#define MAX_ATOM_NAME_LEN 10
#define MAX_KOSMOS_SECONDS 5

/* Global / shared variables */
int  cNum = 0, hNum = 0;
long numAtoms;


/* Function prototypes */
void kosmos_init(void);
void *c_ready(void *);
void *h_ready(void *);
void make_radical(int, int, int, char *);
void wait_to_terminate(int);


/* Needed to pass legit copy of an integer argument to a pthread */
int *dupInt( int i )
{
	int *pi = (int *)malloc(sizeof(int));
	assert( pi != NULL);
	*pi = i;
	return pi;
}


int main(int argc, char *argv[])
{
	long seed;
	numAtoms = DEFAULT_NUM_ATOMS;
	pthread_t **atom;
	int i;
	int status;

	if ( argc < 2 ) {
		fprintf(stderr, "usage: %s <seed> [<num atoms>]\n", argv[0]);
		exit(1);
	}

	if ( argc >= 2) {
		seed = atoi(argv[1]);
	}

	if (argc == 3) {
		numAtoms = atoi(argv[2]);
		if (numAtoms < 0) {
			fprintf(stderr, "%ld is not a valid number of atoms\n",
				numAtoms);
			exit(1);
		}
	}

    kosmos_log_init();
	kosmos_init();

	srand(seed);
	atom = (pthread_t **)malloc(numAtoms * sizeof(pthread_t *));
	assert (atom != NULL);
	for (i = 0; i < numAtoms; i++) {
		atom[i] = (pthread_t *)malloc(sizeof(pthread_t));
		if ( (double)rand()/(double)RAND_MAX < ATOM_THRESHOLD ) {
			hNum++;
			status = pthread_create (
					atom[i], NULL, h_ready,
					(void *)dupInt(hNum)
				);
		} else {
			cNum++;
			status = pthread_create (
					atom[i], NULL, c_ready,
					(void *)dupInt(cNum)
				);
		}
		if (status != 0) {
			fprintf(stderr, "Error creating atom thread\n");
			exit(1);
		}
	}

    /* Determining the maximum number of ethynyl radicals is fairly
     * straightforward -- it will be the minimum of the number of
     * hNum and cNum/2.
     */
    
    int max_radicals = (hNum < cNum/2 ? hNum : (int)(cNum/2));
#ifdef VERBOSE
    printf("Maximum # of radicals expected: %d\n", max_radicals);
#endif

    wait_to_terminate(max_radicals);
}


/*
* Now the tricky bit begins....  All the atoms are allowed
* to go their own way, but how does the Kosmos ethynyl-radical
* problem terminate? There is a non-zero probability that
* some atoms will not become part of a radical; that is,
* many atoms may be blocked on some condition variable of
* our own devising. How do we ensure the program ends when
* (a) all possible radicals have been created and (b) all
* remaining atoms are blocked (i.e., not on the ready queue)?
*/


/*
 * ^^^^^^^
 * DO NOT MODIFY CODE ABOVE THIS POINT.
 *
 *************************************
 *************************************
 *
 * ALL STUDENT WORK MUST APPEAR BELOW.
 * vvvvvvvv
 */


/* 
 * DECLARE / DEFINE NEEDED VARIABLES IMMEDIATELY BELOW.
 */
//shared cariables between thread routines c_ready and h_ready
int radicals =0;
int num_free_c = 0;
int num_free_h = 0;
int combining_c1 = -1;
int combining_c2 = -1;
int combining_h = -1;
char combiner[MAX_ATOM_NAME_LEN];

//mutexes and condition variables
pthread_mutex_t mut; // mutex lock
pthread_cond_t c_waiting;  
pthread_cond_t h_waiting; 
pthread_mutex_t staging_area;//mutex lock for the make radicl process

//creating queue data structures to keep track of carbon and hydrogen atoms
typedef struct Node {
    int id;
    struct Node* next;
} Node;

typedef struct Queue {
    Node* front;
    Node* rear;
} Queue;

Queue* createQueue() {
    Queue* q = (Queue*)malloc(sizeof(Queue));
    q->front = q->rear = NULL;
    return q;
}

void enqueue(Queue* q, int id) {
    Node* temp = (Node*)malloc(sizeof(Node));
    temp->id = id;
    temp->next = NULL;
 
    if (q->rear == NULL) {
        q->front = q->rear = temp;
        return;
    }
 
    q->rear->next = temp;
    q->rear = temp;
}

int dequeue(Queue* q) {
    if (q->front == NULL)
        return -1;
 
    Node* temp = q->front;
    int id = temp->id;
 
    q->front = q->front->next;
 
    if (q->front == NULL)
        q->rear = NULL;
 
    free(temp);
 
    return id;
}

int isQueueEmpty(Queue* q) {
    return (q->front == NULL);
}

//queue for carbon and hydrogen atoms
Queue* CarbonQueue;
Queue* HydrogenQueue;

/*
 * Initializer function used to initialize all the mutexes and condition variables, this function 
 * will be executed once only in the begining of program
 */

void kosmos_init() {
    pthread_mutex_init(&mut, NULL);
    pthread_mutex_init(&staging_area, NULL);
    pthread_cond_init(&c_waiting, NULL);
    pthread_cond_init(&h_waiting, NULL);
    //now creating queues for carbon and hydrogen atoms
    CarbonQueue = createQueue();
    HydrogenQueue = createQueue();
}
void printQueue(Queue* q) {
    pthread_mutex_lock(&mut);
    Node* temp = q->front;
    printf("Queue contents: ");
    while (temp != NULL) {
        printf("%d ", temp->id);
        temp = temp->next;
    }
    printf("\n");
    pthread_mutex_unlock(&mut);
}


/*
 * When a hydrogen thread is executed, the hydrogen  thread will always execute this function, 
 * As soon as a hydrogen thread enters this function, I am checking if there are enough atoms
 * waiting already, if yes then go aheaad and make a radical. if there are not enough atoms 
 * waiting then I am making the hydrogen atoms sleep and then passing the lock to the next thread as necessary
 *
 */
void *h_ready( void *arg )
{
    pthread_mutex_lock(&mut);
    int id = *((int *)arg);
    enqueue(HydrogenQueue, id);//put it in the queue
    num_free_h++;//now i have a hydrogen
    char name[MAX_ATOM_NAME_LEN];
    
    sprintf(name, "h%03d", id);
    
    //if not enough atoms to form a radical then wait 
    if (!(isQueueEmpty(HydrogenQueue)) && !(isQueueEmpty(CarbonQueue)) && CarbonQueue->front->next != NULL){
        while(!(num_free_c >=2 && num_free_h >= 1)){
            pthread_cond_wait(&h_waiting, &mut);//this will wait on the thread and will unlock the mutex for the next thread
        }
    }
    //now make the radical if there are enugh atoms
     if(num_free_c >= 2 && num_free_h >= 1){
        
        pthread_mutex_lock(&staging_area);//staging area locked
        strcpy(combiner,name);
         
        combining_h = dequeue(HydrogenQueue);
        combining_c1 = dequeue(CarbonQueue);
        combining_c2 = dequeue(CarbonQueue);
        make_radical(combining_c1, combining_c2, combining_h, combiner);
        num_free_c -= 2;
        num_free_h--;
        combining_c1 = -1;
        combining_c2 = -1;
        combining_c2 = -1;
        pthread_mutex_unlock(&staging_area);// staging area unlocked
    }
    
    pthread_cond_broadcast(&c_waiting);//wake up carbon atoms
    pthread_cond_signal(&h_waiting);//wake up hydrogen atoms
    
    
#ifdef VERBOSE
	printf("\n%s now exists\n", name);
#endif
    
    
    pthread_mutex_unlock(&mut);//unlock mutex lock for the next available thread
    
	return NULL;
}

/*
 * When a carbon thread is executed,the carbon thread will always execute this function, 
 * As soon as a carbon thread enters this function, I am checking if there are enough atoms 
 * waiting already to  make a redical, if yes then go aheaad and make a radical. 
 * if there are not enough atoms waiting then I am making the carbon atoms sleep 
 * and passing the mtex lock to the next available threads as necessary
 *
 */
void *c_ready( void *arg )
{
    pthread_mutex_lock(&mut);
	int idd = *((int *)arg);
    enqueue(CarbonQueue, idd);
    num_free_c++;// now I have a carbon
    char name[MAX_ATOM_NAME_LEN];
    sprintf(name, "c%03d", idd);

    //if not enough atoms to form a radical then wait 
    if (!(isQueueEmpty(HydrogenQueue)) && !(isQueueEmpty(CarbonQueue)) && CarbonQueue->front->next != NULL){
        while(!(num_free_c >=2 && num_free_h >= 1)){
            pthread_cond_wait(&c_waiting, &mut);// this makes the thread sleep and also unlocks the mutex for the next thread
        }
    }
    //if enough atoms then go ahead and make the radical
    if(num_free_c >= 2 && num_free_h >= 1){
        pthread_mutex_lock(&staging_area);//staginf area mutex locked
        strcpy(combiner,name);
       
        combining_h = dequeue(HydrogenQueue);
        combining_c1 = dequeue(CarbonQueue);
        combining_c2 = dequeue(CarbonQueue);
        
        make_radical(combining_c1, combining_c2, combining_h, combiner);//makes the radical
        //resetting values of shared variables
        num_free_c -= 2;
        num_free_h--;
        combining_c1 = -1;
        combining_c2 = -1;
        combining_c2 = -1;
        pthread_mutex_unlock(&staging_area);//staging area mutex unlocked
        }
        pthread_cond_signal(&h_waiting);//signal the waiting hydrogen thread to wake up
        pthread_cond_broadcast(&c_waiting);//signal the waiting carbon atoms to wake up
    
#ifdef VERBOSE
	printf("\n%s now exists\n", name);
#endif
    pthread_mutex_unlock(&mut);//unlocks the mutex for the next thread
    
	return NULL;
}

/*
 * function to make the radical and ad it to the log entry also I am 
 * icrementng the radicals everytime the program makes a radical
 * calling this function in c-ready and h_ready
*/
void make_radical(int c1, int c2, int h, char *maker){
#ifdef VERBOSE
	fprintf(stdout, "A ethynyl radical was made: c%03d  c%03d  h%03d\n",
		c1, c2, h);
#endif
    kosmos_log_add_entry(radicals, c1, c2, h, maker);
    radicals++;
}

void wait_to_terminate(int expected_num_radicals) {
    /* A rather lazy way of doing it, but good enough for this assignment. */
    sleep(MAX_KOSMOS_SECONDS);
    kosmos_log_dump();
    exit(0);
}
