.data
int1: 	.word	0
int2: 	.word	0
d1: 	.double	0.0
d2: 	.double	0.0
message0: 	.asciiz	"\n"
double0: 	.double	1.0
double1: 	.double	2.0
double2: 	.double	2.9
character:	.asciiz	" "
integer:	.word	0
double:	.double	0.0

.text
.globl main

main:
li $t0, 1
sw $t0, int1
li $t0, 2
sw $t0, int2
L0:
l.d $f2, double0
s.d $f2, d1

l.d $f2, double1
s.d $f2, d2

L1:
lw $t0, int1
lw $t1, int2
add $t2, $t0, $t1

lw $t0, int1
lw $t1, int2
sub $t3, $t0, $t1

mul $t0, $t2, $t3

lw $t1, int1
li $t2, 2
sub $t3, $t1, $t2

div $t1, $t0, $t3

li $v0, 1
sw $t1, integer
lw $a0, integer
syscall

L2:
li $v0, 4
la $a0, message0
syscall

L3:
l.d $f2, d1
l.d $f4, d2
add.d $f6, $f2, $f4

l.d $f2, d1
l.d $f4, d2
sub.d $f8, $f2, $f4

mul.d $f2, $f6, $f8

l.d $f4, d1
l.d $f6, double2
sub.d $f8, $f4, $f6

div.d $f4, $f2, $f8

li $v0, 3
mov.d $f12, $f4
syscall

L4:
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