/* a4.c
 * CSC Fall 2022
 * 
 * Student name:Sunpreet Singh
 * Student UVic ID:V00997303
 * Date of completed work:
 *
 *
 * Code provided for Assignment #4
 *
 * Author: Mike Zastre (2022-Nov-22)
 *
 * This skeleton of a C language program is provided to help you
 * begin the programming tasks for A#4. As with the previous
 * assignments, there are "DO NOT TOUCH" sections. You are *not* to
 * modify the lines within these section.
 *
 * You are also NOT to introduce any new program-or file-scope
 * variables (i.e., ALL of your variables must be local variables).
 * YOU MAY, however, read from and write to the existing program- and
 * file-scope variables. Note: "global" variables are program-
 * and file-scope variables.
 *
 * UNAPPROVED CHANGES to "DO NOT TOUCH" sections could result in
 * either incorrect code execution during assignment evaluation, or
 * perhaps even code that cannot be compiled.  The resulting mark may
 * be zero.
 */


/* =============================================
 * ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
 * =============================================
 */

#define __DELAY_BACKWARD_COMPATIBLE__ 1
#define F_CPU 16000000UL

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>

#define DELAY1 0.000001
#define DELAY3 0.01

#define PRESCALE_DIV1 8
#define PRESCALE_DIV3 64
#define TOP1 ((int)(0.5 + (F_CPU/PRESCALE_DIV1*DELAY1))) 
#define TOP3 ((int)(0.5 + (F_CPU/PRESCALE_DIV3*DELAY3)))

#define PWM_PERIOD ((long int)500)

volatile long int count = 0;
volatile long int slow_count = 0;


ISR(TIMER1_COMPA_vect) {
	count++;
}


ISR(TIMER3_COMPA_vect) {
	slow_count += 5;
}

/* =======================================
 * ==== END OF "DO NOT TOUCH" SECTION ====
 * =======================================
 */


/* *********************************************
 * **** BEGINNING OF "STUDENT CODE" SECTION ****
 * *********************************************
 */

void led_state(uint8_t LED, uint8_t state) {
	/* set direction for bits on port */
	DDRL = 0xFF;
	
	uint8_t bits_L = 0;
	
	

	
	if(state == 0)
	{
		
		switch(LED){
			case 3: PORTL &= 0b11111101; 
					break;
			case 2: PORTL &= 0b11110111;
					break;
			case 1: PORTL &= 0b11011111;
					break;
			case 0: PORTL &= 0b01111111;
					break;
			default: PORTL &= 0b11111111;
					break;
		
		}
	}
	else
	{
		
		switch(LED){
			case 3: bits_L |= 0b00000010; // led 3
					PORTL |= bits_L;
					break;
			case 2: bits_L |= 0b00001000; // led 2
					PORTL |= bits_L;
					break;
			case 1: bits_L |= 0b00100000; // led 1
					PORTL |= bits_L;
					break;
			case 0: bits_L |= 0b10000000; //led 0
					PORTL |= bits_L;
					break;
			default: PORTL |= bits_L;
					break;
		}
	}
	
}




void SOS() {
    uint8_t light[] = {
        0x1, 0, 0x1, 0, 0x1, 0,
        0xf, 0, 0xf, 0, 0xf, 0,
        0x1, 0, 0x1, 0, 0x1, 0,
        0x0
    };

    int duration[] = {
        100, 250, 100, 250, 100, 500,
        250, 250, 250, 250, 250, 500,
        100, 250, 100, 250, 100, 250,
        250
    };

	int length = 19;
	
	
	for(int i = 0; i < length; i++ ){
		//checking for bit 0 first which is the state for led 0
		
		uint8_t led_value = light[i];
		uint8_t time_limit = duration[i];
		
		
		if(led_value == 0x1){
			led_state(0,1);
			_delay_ms(time_limit);
		}
		else if(led_value == 0xf){
				led_state(0,1);
				led_state(1,1);
				led_state(2,1);
				led_state(3,1);
				_delay_ms(time_limit);
		}	
		else{
			led_state(0,0);
			led_state(1,0);
			led_state(2,0);
			led_state(3,0);
			_delay_ms(time_limit);
		}
	}
}


void glow(uint8_t LED, float brightness) {
	long int thresh;
	int led_off = 0;
	int led_on;
	
	
	
	thresh = PWM_PERIOD * brightness;
	for(; ;){
		// if count is less than thresh and led is off then turn on the led and load the value 1 into led on, which just means led is on
		if( (count < thresh) && (led_off == 0) ){
			led_on = 1;
			led_state(LED,1);
		}
		//now if count is less than pwm period and led is on load 0 into led off and turn off the led
		else if( (count < PWM_PERIOD) && (led_on == 1) ){
			led_off = 0;
			led_state(LED,0);
		}
		//if count is greater simply turn on the led and make counter 0
		else if( count > PWM_PERIOD ) {
			led_on =1;
			led_state(LED,1);
			count = 0;
		}
	}
}



void pulse_glow(uint8_t LED) {
	long int threshold = 0;
	int led_off = 0;
	int led_on;
	
	/*
	for(; ;){
		for(;threshold < PWM_PERIOD; threshold++){
				if( (count < threshold) && (led_off == 0) ){
					
					led_on = 1;
					led_state(LED,1);
					//threshold++;
					
				}
				else if( (count < PWM_PERIOD) && (led_on == 1) ){
					led_off = 0;
					led_state(LED,0);
					//threshold ++;
				}
				else if( count > PWM_PERIOD ) {
					led_on =1;
					led_state(LED,1);
					count = 0;
				}
		}
		
		
	}
}
*/
	/*
	for(; ;){
		
		// if count is less than thresh and led is off then turn on the led and load the value 1 into led on, which just means led is on
		
		if( (count < threshold) && (led_off == 0) ){
			
			led_on = 1;
			led_state(LED,1);
			//threshold++;
			
		}
		//now if count is less than pwm period and led is on load 0 into led off and turn off the led
		else if( (count < PWM_PERIOD) && (led_on == 1) ){
			led_off = 0;
			led_state(LED,0);
			//threshold ++;
		}
		//if count is greater simply turn on the led and make counter 0
		else if( count > PWM_PERIOD ) {
			led_on =1;
			led_state(LED,1);
			
			threshold --;
			//slow_count = 0;
			count = 0;
			slow_count = 0;
			
		}
	
	}
} */
	
	// if i modify the value of threshold then i can make the led glow slowly from dim to bright and so on
	
	for(; ;){
		
		// if count is less than thresh and led is off then turn on the led and load the value 1 into led on, which just means led is on
		
		if( (count < threshold) && (led_off == 0) ){
			
			led_on = 1;
			led_state(LED,1);
			//threshold++;
			
		}
		//now if count is less than pwm period and led is on load 0 into led off and turn off the led
		else if( (count < PWM_PERIOD) && (led_on == 1) ){
			led_off = 0;
			led_state(LED,0);
			//threshold ++;
		}
		//if count is greater simply turn on the led and make counter 0
		else if( count > PWM_PERIOD ) {
			led_on =1;
			led_state(LED,1);
			
			threshold ++;
			//slow_count = 0;
			
			count = 0;
			
			if(threshold == 500){
				for(; threshold > 0; threshold --){
				
					if( (count < threshold) && (led_off == 0) ){
					
						led_on = 1;
						led_state(LED,1);
						//threshold++;
					
					}
					else if( (count < PWM_PERIOD) && (led_on == 1) ){
						led_off = 0;
						led_state(LED,0);
						//threshold ++;
					}
					else if( count > PWM_PERIOD ) {
						led_on =1;
						led_state(LED,1);
						
						//slow_count = 0;
						count = 0;
						slow_count = 0;
						
						if(threshold == 0){
							threshold = 500;
						}
					}
					
					
				}
				
				
			}
			
			
		}
			// what to do when threshold reaches top value which is 500
			 
	
	} 
	
}


void light_show() {
	 uint8_t light[] = {
		 0xf, 0, 0xf, 0, 0xf, 0,
		 0x1, 0, 0x2, 0, 0x4, 0,
		 0x8, 0, 0xf, 0, 0xf, 0,
		 0xc , 0, 0x6, 0, 0x3, 0,
		 0xf, 0, 0xf,0, 0x8, 0 ,
		 0x4, 0, 0x2, 0, 0x1, 0,  
		 0xa, 0, 0xa, 0, 0xa, 0,
		 0x9, 0, 0x9, 0, 0x9, 0,
		 0x5, 0, 0x5, 0, 0x5, 0
	 };

	 int duration[] = {
		 250, 250, 250, 250, 250, 500,
		 100, 250, 100, 250, 100, 500,
		 100, 250, 250, 250, 250, 500,
		 300, 250, 300, 250, 300, 500,
		 250, 250, 250, 250, 100, 500,
		 100, 250, 100, 250, 100, 500,
		 300, 250, 300, 250, 300, 500,
		 300, 250, 300, 250, 300, 500,
		 300, 250, 300, 250, 300, 500
	 };
	 
	 int length = 54;
	 
	 
	 
	 for(int i = 0; i < length; i++ ){
		 //checking for bit 0 first which is the state for led 0
		 
		 uint8_t led_value = light[i];
		 uint8_t time_limit = duration[i];
		 
		 
		 if(led_value == 0x1){
			 led_state(0,1);
			 _delay_ms(time_limit);
		 }
		 else if(led_value == 0xf){
			 led_state(0,1);
			 led_state(1,1);
			 led_state(2,1);
			 led_state(3,1);
			 _delay_ms(time_limit);
		 }
		 else if(led_value == 0x2){
			 led_state(1,1);
			  _delay_ms(time_limit);
		 }
		  else if(led_value == 0x4){
			  led_state(2,1);
			  _delay_ms(time_limit);
		  }
		  else if(led_value == 0x4){
			   led_state(3,1);
			   _delay_ms(time_limit);
		  }
		  else if(led_value == 0xc){
			  led_state(3,1);
			  led_state(2,1);
			  _delay_ms(time_limit);
		  }
		  
		  else if(led_value == 0x6){
			   led_state(2,1);
			   led_state(1,1);
			   _delay_ms(time_limit);
		   }
		   else if(led_value == 0x3){
			    led_state(1,1);
			    led_state(0,1);
			    _delay_ms(time_limit);
		   }
		   else if(led_value == 0xa){
			     led_state(1,1);
			     led_state(3,1);
			     _delay_ms(time_limit);
		   }
		   else if(led_value == 0x9){
			    led_state(3,1);
			    led_state(0,1);
			    _delay_ms(time_limit);
		   }
		  
		  else if(led_value == 0x5){
			  led_state(0,1);
			  led_state(2,1);
			  _delay_ms(time_limit);
		  }
		 else{
			 led_state(0,0);
			 led_state(1,0);
			 led_state(2,0);
			 led_state(3,0);
			 _delay_ms(time_limit);
		 }
	 }
}


/* ***************************************************
 * **** END OF FIRST "STUDENT CODE" SECTION **********
 * ***************************************************
 */


/* =============================================
 * ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
 * =============================================
 */

int main() {
    /* Turn off global interrupts while setting up timers. */

	cli();

	/* Set up timer 1, i.e., an interrupt every 1 microsecond. */
	OCR1A = TOP1;
	TCCR1A = 0;
	TCCR1B = 0;
	TCCR1B |= (1 << WGM12);
    /* Next two lines provide a prescaler value of 8. */
	TCCR1B |= (1 << CS11);
	TCCR1B |= (1 << CS10);
	TIMSK1 |= (1 << OCIE1A);

	/* Set up timer 3, i.e., an interrupt every 10 milliseconds. */
	OCR3A = TOP3;
	TCCR3A = 0;
	TCCR3B = 0;
	TCCR3B |= (1 << WGM32);
    /* Next line provides a prescaler value of 64. */
	TCCR3B |= (1 << CS31);
	TIMSK3 |= (1 << OCIE3A);


	/* Turn on global interrupts */
	sei();

/* =======================================
 * ==== END OF "DO NOT TOUCH" SECTION ====
 * =======================================
 */


/* *********************************************
 * **** BEGINNING OF "STUDENT CODE" SECTION ****
 * *********************************************
 */

// This code could be used to test your work for part A.
/*
	led_state(0, 1);
	_delay_ms(1000);
	led_state(2, 1);
	_delay_ms(1000);
	led_state(1, 1);
	_delay_ms(1000);
	led_state(2, 0);
	_delay_ms(1000);
	led_state(0, 0);
	_delay_ms(1000);
	led_state(1, 0);
	_delay_ms(1000);
 */

// This code could be used to test your work for part B.

	//SOS();
 

// This code could be used to test your work for part C.

	//glow(2, 0.0);
	//glow(2, 0.1);
	//glow(2, 0.5);
	//glow(2, 0.9);



// This code could be used to test your work for part D.

	//pulse_glow(3);
 


//This code could be used to test your work for the bonus part.

	light_show();
 

/* ****************************************************
 * **** END OF SECOND "STUDENT CODE" SECTION **********
 * ****************************************************
 */
}
