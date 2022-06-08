include macros2.asm
include number.asm

.MODEL	LARGE
.386
.STACK 200h

.DATA
	_0	dd	0
	_5	dd	5
	_6	dd	6
	_4	dd	4
	_3	dd	3
	_2	dd	2
	_8	dd	8
	_49	dd	49
	_50	dd	50
	_correcto	db	"correcto",'$'
	var1	dd	?
	var2	dd	?
	var3	dd	?
	cad	db	?,'$'
	cad2	db	?,'$'
	@c	dd	?
	@aux	dd	?

.CODE

START:
mov ax, @DATA
mov ds, ax
mov es, ax

fild _5

fstp var2

fild _6

fstp var3

fild _4

fstp @aux

fld var2



fild _3



fadd

fld @aux

fxch
fcomp
fstsw ax
sahf
jnbe IF_Nro14ELSE

fld var2

fild _3

fadd

fstp @c

jmp IF_Nro14END

IF_Nro14ELSE:

fld var2



fild _2



fmulp

fld @aux

fxch
fcomp
fstsw ax
sahf
jnbe IF_Nro13ELSE

fld var2

fild _2

fmulp

fstp @c

jmp IF_Nro13END

IF_Nro13ELSE:

fld var3



fild _2



fsub

fild _4



fdiv

fld @aux

fxch
fcomp
fstsw ax
sahf
jnbe IF_Nro12ELSE

fld var3

fild _2

fsub

fild _4

fdiv

fstp @c

jmp IF_Nro12END

IF_Nro12ELSE:

fild _8



fld @aux

fxch
fcomp
fstsw ax
sahf
jnbe IF_Nro11ELSE

fild _8

fstp @c

jmp IF_Nro11END

IF_Nro11ELSE:

fild _0

fstp @c

IF_Nro11END:

IF_Nro12END:

IF_Nro13END:

IF_Nro14END:

fld @c

fild _49

fadd

fstp var1

fild _50

fld var1

fcomp
fstsw ax
sahf
jne IF_Nro15END

mov dx, OFFSET _correcto
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro15END

IF_Nro15END:

mov ax, 4C00h
int 21h
END START

