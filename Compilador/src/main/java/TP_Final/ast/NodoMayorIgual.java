package TP_Final.ast;

public class NodoMayorIgual extends NodoCondicion{
    public NodoMayorIgual (NodoExpresion izquierda, NodoExpresion derecha) {
    super(">=", izquierda, derecha); }
}
