; a2-signalling.asm
; CSC 230: Fall 2022
;
; Student name:Sunpreet Singh
; Student ID:V00997303
; Date of completed work:10/29/2022
;
; *******************************
; Code provided for Assignment #2
;
; Author: Mike Zastre (2022-Oct-15)
;
 
; This skeleton of an assembly-language program is provided to help you
; begin with the programming tasks for A#2. As with A#1, there are "DO
; NOT TOUCH" sections. You are *not* to modify the lines within these
; sections. The only exceptions are for specific changes changes
; announced on Brightspace or in written permission from the course
; instructor. *** Unapproved changes could result in incorrect code
; execution during assignment evaluation, along with an assignment grade
; of zero. ****

.include "m2560def.inc"
.cseg
.org 0



; ***************************************************
; **** BEGINNING OF FIRST "STUDENT CODE" SECTION ****
; ***************************************************
		

		
				
	; initializion code will need to appear in this
    ; section
		
		
		; Initializing my stack and portL and portB that i am going to use through out this assignment
		
		;initializing my ports
		ldi r16,0xff
		sts DDRL, r16
		out DDRB, r16

		;initializing my stack here SPL pointing to oxFF and SPH pointing to 0x21
		.def temp = r22
		.def n4 =r0
		ldi temp, low(RAMEND) ;.equ	RAMEND	= 0x21ff 
		out SPL, temp
		ldi temp, high(RAMEND)
		out SPH, temp

; ***************************************************
; **** END OF FIRST "STUDENT CODE" SECTION **********
; ***************************************************

; ---------------------------------------------------
; ---- TESTING SECTIONS OF THE CODE -----------------
; ---- TO BE USED AS FUNCTIONS ARE COMPLETED. -------
; ---------------------------------------------------
; ---- YOU CAN SELECT WHICH TEST IS INVOKED ---------
; ---- BY MODIFY THE rjmp INSTRUCTION BELOW. --------
; -----------------------------------------------------

	rjmp test_part_e
	; Test code


test_part_a:
	ldi r16, 0b00100001
	rcall set_leds
	rcall delay_long

	clr r16
	rcall set_leds
	rcall delay_long

	ldi r16, 0b00111000
	rcall set_leds
	rcall delay_short

	clr r16
	rcall set_leds
	rcall delay_long

	ldi r16, 0b00100001
	rcall set_leds
	rcall delay_long

	clr r16
	rcall set_leds

	rjmp end


test_part_b:
	ldi r17, 0b00101010
	rcall slow_leds
	ldi r17, 0b00010101
	rcall slow_leds
	ldi r17, 0b00101010 
	rcall slow_leds
	ldi r17, 0b00010101
	rcall slow_leds

	rcall delay_long
	rcall delay_long

	ldi r17, 0b00101010 
	rcall fast_leds
	ldi r17, 0b00010101
	rcall fast_leds
	ldi r17, 0b00101010 
	rcall fast_leds
	ldi r17, 0b00010101 ;working
	rcall fast_leds
	ldi r17, 0b00101010
	rcall fast_leds
	ldi r17, 0b00010101
	rcall fast_leds
	ldi r17, 0b00101010
	rcall fast_leds
	ldi r17, 0b00010101
	rcall fast_leds

	rjmp end

test_part_c:
	ldi r16, 0b11111000
	push r16
	rcall leds_with_speed
	pop r16

	ldi r16, 0b11011100
	push r16
	rcall leds_with_speed
	pop r16

	ldi r20, 0b00100000
test_part_c_loop:
	push r20
	rcall leds_with_speed
	pop r20
	lsr r20
	brne test_part_c_loop

	rjmp end


test_part_d:
	ldi r21, 'E'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long

	ldi r21, 'A'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long


	ldi r21, 'M'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long

	ldi r21, 'H'
	push r21
	rcall encode_letter
	pop r21
	push r25
	rcall leds_with_speed
	pop r25

	rcall delay_long

	rjmp end


test_part_e:
	ldi r25, HIGH(WORD02 << 1)
	ldi r24, LOW(WORD02 << 1)
	rcall display_message
	rjmp end

end:
    rjmp end






; ****************************************************
; **** BEGINNING OF SECOND "STUDENT CODE" SECTION ****
; ****************************************************

; seting up my leds turning on the respective bits on in port L and port B so that only the bits we want on or the encoded bits of the respective
; letters are on
set_leds:
		; r20 os going to set the bits on in portB and r21 is going to set the bits on in portL
		.def port_b  = r20
		.def port_l  = r21
		;making sure the registers are cleared
		clr port_b
		clr port_l

	
	;check bit 5 of r16 if its on turn on bit 1 on port b which turns on led 1
		sbrc r16, 5
		ori port_b, 0b00000010
		
	;check bit 4 of r16, if its set turn on bit 3 on port b which turns on led 2
		sbrc r16, 4
		ori port_b, 0b00001000
		
	;check bit 3 of r 16 if its set then set bit 1 of portL which turns on led 3
		sbrc r16, 3
		ori port_l, 0b00000010
		
	;check bit 2 on r16 if it is on then turn on bit 3 on portL which turn on led 4
		sbrc r16, 2
		ori port_l, 0b00001000
		
	;check bit 1 of r16 if it is on then turn bit 5 on port L and turns on led5
		sbrc r16, 1
		ori port_l, 0b00100000

	;if bit 0 is set on r16 then turn on the bit 7 of portL and turn led 6 on otherwise skip
		sbrc r16, 0
		ori port_l, 0b10000000
		
		; turning on the leds now corresponding to their respective bits
		sts PORTL, port_l
		out PORTB, port_b
		;making sure everything is clear so the registers are empty when they start working again
		clr port_l
		clr port_b
		clr r16

		ret
		
	
	; after we have turned on the respective bits i am turning on the leds for 1 second in slow leds
slow_leds:
		
		; making a copy of r17 given in test b into register 16 because we are calling set_ledds and set_leds work with register 16
		mov r16, r17
		rcall set_leds
		;delay long is about 1 second long so the leds will be turned on for 1 second
		rcall delay_long
		;now i am turning off the leds for a bit as shown in the video and then the fast jumping leds will start blinking
		clr r16
		sts PORTL, r16
		out PORTB, r16
		ret
	
	;after the respective bits are turned on i am turning the leds on for quarter of a second in fast_leds	
fast_leds:
		mov r16, r17
		rcall set_leds
		; delay_short is quarter of a second so it turns on the leds for quarter of a second
		rcall delay_short
		;so that the leds dont stay on forever, take a break leds
		clr r16
		sts PORTL, r16
		out PORTB, r16
		ret
		
		; 11 on the 7th and 6th bit means turn on the led for 1 second 
		; and 00 on the 7th and 6th bit means turn on the leds for quarter of a second
leds_with_speed:
			
				; first protect the Z register, since we will use it
				push ZL
				push ZH
				; now protect r0 since it will be used
				; to store the parameter
				push n4 ;r0
				;pointing to low of stack now
				in ZH, SPH
				in ZL, SPL
				;value at the top of stack is stored in n4
				ldd n4, Z+7
				mov r17, n4
				; now we only check if the 7th bit is on or off because of the 7th bit os on 6th bit is on as we are told, either 6 and 7 bits are on
				;or either they both are off
				
				;if 7th bit is set skip the next line and execute slow leds
				;and if the 7th bit is off then jump to next instruction and jump to fast_leds which will turn on the leds for quarter second
				sbrs r17, 7
				rcall fast_leds
				sbrc r17, 7
				rcall slow_leds
				; popping all the values from the stack in reverse order, keeping our stack nice and clean
				pop n4
				pop ZH
				pop ZL
				clr r17
				ret
					
				
					
; Note -- this function will only ever be tested
; with upper-case letters, but it is a good idea
; to anticipate some errors when programming (i.e. by
; accidentally putting in lower-case letters). Therefore
; the loop does explicitly check if the hyphen/dash occurs,
; in which case it terminates with a code not found
; for any legal letter.

encode_letter:
				; defining some variables
				.def n = r2
				; tnp reads every single characer starting with A and reading every dot and o until it reaches the end
				.def tnp = r18
				; i am using a convertible r23 which helps me convert my bits so the appropriate led is on 
				.def conver = r23
				ldi conver, 0b00100000
				;THIS is the value that this function returns which is initialized  to 0 in the begining
				ldi r25, 0b00000000
				; pushing values onto the stack
				push YH
				push YL
				push n
				in YH, SPH
				in YL, SPL
				; getting the value from top of the stack
				ldd n, Y+7
				mov r20, n
				; gettng the address of the pattern
				ldi ZL, low(2*PATTERNS)
				ldi ZH, high(2*PATTERNS)		
				
				
arrlp:			
				; tnp is going to point to point to the first letter that starts in the Pattern which is A
				lpm tnp, Z+
				;then i am matching the letter given which is E in first case for example, E is in r20 i am going to match the letter 
				; if they dont match keeo looping until the letter matches 
				cp r20, tnp
				;cpi tnp, 0x2D
				;BREQ is_dash 
				;once the letter matches, for example E in tnp and E in r20 matches i am jumping to a function letter_matches
				breq letter_matches
				jmp arrlp
				;when the number matches call set leds
				mov r16,r20
				rcall set_leds
				clr tnp 
				clr r20
				
				ret
letter_matches:	
				; now the letter for example E was matched now i am encoding the letter E from the patterns going through every dot(0x2E) and 
				; o(0x6F)
				lpm tnp,Z+
				; if tnp is pointing to a dot jump to a function called its_dot (. in ASCII is 0x2E)
				cpi tnp, 0x2E
				BREQ its_dot
				; and if tnp is pointing to o jump to a function called small_o(o in ASCII is 0x6F)
				cpi tnp, 0x6F
				BREQ small_o
				; now if we reach the end of the encoding there's gonna be either 1 or 2
				;in case theres one in the end i am making sure the value returned in the end has 11 at 7th and 6th bit so the led stays on 
				;for 1 second
				cpi tnp, 1
				BREQ end_one
				; if theres 2 at the end of encoding i am making sure the value returned in r25 has 00 at 7th and 6th bit so the led stays 
				;on for quarter of a second
				cpi tnp, 2
				BREQ end_two
				;popping the values keeping our stack nice and clean
				pop n
				pop YL
				pop YH
				ret

end_one:
				; if the encoding ends with one 7th and 6th bit are going to be on
				ori r25, 0b11000000
				
				pop n
				pop YL
				pop YH
				
				ret
end_two:		
				; if the encoding ends with 2 the 6th and 7th bit are going to be off in the value returned in r25 as described in the assignment
				ori r25, 0b00000000
				
				pop n
				pop YL
				pop YH
				
				ret 
				
				
its_dot:
				; if its a dot keep the bit off 
				ori r25, 0b00000000
				; keeo shifting right my converter in order to turn on the bits and keep up with the encoding process
				lsr conver
				; jmp back to letter matches and see if the next is dor, small o or 1 or 2
				jmp letter_matches		
small_o:
				;if theres small o that means that we have to turn on the respective bit
				; we made sure that we shift right our converter to keep up with the encoding process
				or r25, conver
				;keep shifting right to keep up with the encoding process
				lsr conver
				jmp letter_matches
;is_dash:		
				;lpm tnp, Z+8
				;rjmp arrlp
				
				ret
	
		; after i have set up encoding now i am encoding a message which is in the form of a string		
display_message:
				; pushing values onto the stack
				push ZL
				push ZH
				mov ZH, r25
				mov	ZL, r24
				push XH
				push XL

get_the_word:
				; i am pointing to the first letter for example Q , and if the letter was 0 i am jumping to done and i am done
				lpm r20, Z
				cpi r20, 0
				BREQ done
				; now i am getting Q
				lpm r20, Z+
				mov r21, r20
				;i am saving the address in X register because i am using Z register in encoding_letters function so x
				;is going to rememebr that i was at Q
				mov XH,ZH
				mov XL,ZL
				push r21
				;calling encode letter,  which is going to encode Q for example 
				rcall encode_letter
				pop r21
				push r25
				;when q gets encoded its going to come back with an encoded value of Q in r25
				;which is gonna go to leds_with_speed and turn on the respective bits for Q and if the encoding of Q ends with 1, leds are going to stay on for 1 second
				;or if the encoding ends with 2 the led for that respective letter are gonna stay on for quarter second
				rcall leds_with_speed
				pop r25
				rcall delay_long
				mov ZH, XH
				mov ZL, XL
				; now i am jumping back to get_the_word which is gonna point to U, then I then C then K for example and so on, and when the letter is 0 we jump out 
				;and we are done encoding our message
				rjmp get_the_word
done:			
				pop XL
				pop XH
				pop ZL
				pop ZH
				ret
				


; ****************************************************
; **** END OF SECOND "STUDENT CODE" SECTION **********
; ****************************************************




; =============================================
; ==== BEGINNING OF "DO NOT TOUCH" SECTION ====
; =============================================

; about one second
delay_long:
	push r16

	ldi r16, 14
delay_long_loop:
	rcall delay
	dec r16
	brne delay_long_loop

	pop r16
	ret


; about 0.25 of a second
delay_short:
	push r16

	ldi r16, 4
delay_short_loop:
	rcall delay
	dec r16
	brne delay_short_loop

	pop r16
	ret

; When wanting about a 1/5th of a second delay, all other
; code must call this function
;
delay:
	rcall delay_busywait
	ret


; This function is ONLY called from "delay", and
; never directly from other code. Really this is
; nothing other than a specially-tuned triply-nested
; loop. It provides the delay it does by virtue of
; running on a mega2560 processor.
;
delay_busywait:
	push r16
	push r17
	push r18

	ldi r16, 0x08
delay_busywait_loop1:
	dec r16
	breq delay_busywait_exit

	ldi r17, 0xff
delay_busywait_loop2:
	dec r17
	breq delay_busywait_loop1

	ldi r18, 0xff
delay_busywait_loop3:
	dec r18
	breq delay_busywait_loop2
	rjmp delay_busywait_loop3

delay_busywait_exit:
	pop r18
	pop r17
	pop r16
	ret


; Some tables
.cseg
.org 0x600

PATTERNS:
	; LED pattern shown from left to right: "." means off, "o" means
    ; on, 1 means long/slow, while 2 means short/fast.
	.db "A", "..oo..", 1
	.db "B", ".o..o.", 2
	.db "C", "o.o...", 1
	.db "D", ".....o", 1
	.db "E", "oooooo", 1
	.db "F", ".oooo.", 2
	.db "G", "oo..oo", 2
	.db "H", "..oo..", 2
	.db "I", ".o..o.", 1
	.db "J", ".....o", 2
	.db "K", "....oo", 2
	.db "L", "o.o.o.", 1
	.db "M", "oooooo", 2
	.db "N", "oo....", 1
	.db "O", ".oooo.", 1
	.db "P", "o.oo.o", 1
	.db "Q", "o.oo.o", 2
	.db "R", "oo..oo", 1
	.db "S", "....oo", 1
	.db "T", "..oo..", 1
	.db "U", "o.....", 1
	.db "V", "o.o.o.", 2
	.db "W", "o.o...", 2
	.db "W", "oo....", 2
	.db "Y", "..oo..", 2
	.db "Z", "o.....", 2
	.db "-", "o...oo", 1   ; Just in case!

WORD00: .db "HELLOWORLD", 0, 0
WORD01: .db "THE", 0
WORD02: .db "QUICK", 0
WORD03: .db "BROWN", 0
WORD04: .db "FOX", 0
WORD05: .db "JUMPED", 0, 0
WORD06: .db "OVER", 0, 0
WORD07: .db "THE", 0
WORD08: .db "LAZY", 0, 0
WORD09: .db "DOG", 0

; =======================================
; ==== END OF "DO NOT TOUCH" SECTION ====
; =======================================

