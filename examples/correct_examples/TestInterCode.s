.data
num1: 	.byte	' '
num2: 	.byte	' '
character:	.asciiz	" "
integer:	.word	0
double:	.double	0.0

.text
.globl main

main:
li $t0, 'C'
sb $t0, num1
li $t0, 'D'
sb $t0, num2
L0:
lb $t0, num2
sb $t0, num1

L1:
li $v0, 4
lb $t0, num1
la $a0, character
sb $t0, ($a0)
syscall

L2:
li $v0, 10
syscall

L3:
__str_copy:
	lb $s0, ($a0)
	beqz $s0, __str_copy_end
	b __str_copy_char

__str_copy_char:
	sb $s0, ($a1)
	addi $a0, $a0, 1
	addi $a1, $a1, 1
	b __str_copy

__str_copy_end:
     sb $zero, ($a1)
	jr $ra