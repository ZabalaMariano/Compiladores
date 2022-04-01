include macros2.asm
include number.asm

.MODEL	LARGE
.386
.STACK 200h

.DATA
	_0	dd	0
	_10	dd	10
	_5	dd	5
	_2	dd	2
	_4	dd	4
	_1	dd	1
	_3	dd	3
	_7	dd	7
	_9	dd	9
	_100	dd	100
	_107	dd	107
	_Hola_mundo_CTE_STRING	db	"Hola mundo CTE STRING",'$'
	_Hola_mundo_ID_String	db	"Hola mundo ID String",'$'
	_Division_da_2%x2e5	db	"Division da 2.5",'$'
	_Division_da_2	db	"Division da 2",'$'
	_While_imprime_3_veces%x2e	db	"While imprime 3 veces.",'$'
	_While%x2c_Print_1%x2e	db	"While, Print 1.",'$'
	_While%x2c_Print_2%x2e	db	"While, Print 2.",'$'
	_While%x2c_Print_3%x2e	db	"While, Print 3.",'$'
	_a1_%x3d%x3d_107%x2c_Exito_Filter%x2e	db	"a1 == 107, Exito Filter.",'$'
	_2%x2b2_%x3d%x3d_5	db	"2+2 == 5",'$'
	_2%x2b2_%x21%x3d_5	db	"2+2 != 5",'$'
	_7%x2e5	dd	7.5
	_2%x2e5	dd	2.5
	a1	dd	?
	c1	dd	?
	d1	dd	?
	e1	dd	?
	f1	dd	?
	@c	dd	?
	@aux	dd	?
	s1	db	?,'$'

.CODE

START:
mov ax, @DATA
mov ds, ax
mov es, ax

mov dx, OFFSET _Hola_mundo_CTE_STRING
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

MOV SI, OFFSET _Hola_mundo_ID_String
MOV DI, OFFSET s1
LOOP1:
	MOV AX, [SI]
	CMP AL, '$'
	JE EXIT1
	PUSH [SI]
	INC SI
	POP DX
	XOR DH, DH
	MOV [DI], DX
	INC DI
	JMP LOOP1
EXIT1:
MOV [DI],'$'

mov dx, OFFSET s1
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

fild _10

fild _5

fsub

fstp a1

fld _7%x2e5

fstp c1

fld a1

fild _2

fdiv

fld _2%x2e5

fxch
fcomp
fstsw ax
sahf
jne IF_Nro21END

mov dx, OFFSET _Division_da_2%x2e5
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro21END

IF_Nro21END:

fild _4

fild _2

fdiv

fild _2

fxch
fcomp
fstsw ax
sahf
jne IF_Nro22END

mov dx, OFFSET _Division_da_2
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro22END

IF_Nro22END:

fild _1

fstp a1

mov dx, OFFSET _While_imprime_3_veces%x2e
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

WHILE_Nro3START:

fild _3

fld a1

fcomp
fstsw ax
sahf
jnbe WHILE_Nro3END

fild _1

fld a1

fcomp
fstsw ax
sahf
jne IF_Nro23END

mov dx, OFFSET _While%x2c_Print_1%x2e
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro23END

IF_Nro23END:

fild _2

fld a1

fcomp
fstsw ax
sahf
jne IF_Nro24END

mov dx, OFFSET _While%x2c_Print_2%x2e
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro24END

IF_Nro24END:

fild _3

fld a1

fcomp
fstsw ax
sahf
jne IF_Nro25END

mov dx, OFFSET _While%x2c_Print_3%x2e
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro25END

IF_Nro25END:

fld a1

fild _1

fadd

fstp a1

jmp WHILE_Nro3START
WHILE_Nro3END:

fild _5

fstp @aux

fild _1



fild _5



fmulp

fld @aux

fxch
fcomp
fstsw ax
sahf
jna IF_Nro28ELSE

fild _1

fild _5

fmulp

fstp @c

jmp IF_Nro28END

IF_Nro28ELSE:

fild _7



fld @aux

fxch
fcomp
fstsw ax
sahf
jna IF_Nro27ELSE

fild _7

fstp @c

jmp IF_Nro27END

IF_Nro27ELSE:

fild _3



fild _9



fadd

fld @aux

fxch
fcomp
fstsw ax
sahf
jna IF_Nro26ELSE

fild _3

fild _9

fadd

fstp @c

jmp IF_Nro26END

IF_Nro26ELSE:

fild _0

fstp @c

IF_Nro26END:

IF_Nro27END:

IF_Nro28END:

fld @c

fild _100

fadd

fstp a1

fild _107

fld a1

fcomp
fstsw ax
sahf
jne IF_Nro29END

mov dx, OFFSET _a1_%x3d%x3d_107%x2c_Exito_Filter%x2e
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro29END

IF_Nro29END:

fild _2

fild _2

fadd

fild _5

fxch
fcomp
fstsw ax
sahf
jne IF_Nro30ELSE

mov dx, OFFSET _2%x2b2_%x3d%x3d_5
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

jmp IF_Nro30END

IF_Nro30ELSE:

mov dx, OFFSET _2%x2b2_%x21%x3d_5
mov ah, 9
int 21h
mov ah, 2
mov dl, 13
int 21h
mov dl, 10
int 21h

IF_Nro30END:

mov ax, 4C00h
int 21h
END START

