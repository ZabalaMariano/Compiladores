package TP_Final.ast;

public class NodoIgual extends NodoCondicion{
    public NodoIgual (NodoExpresion izquierda, NodoExpresion derecha) {
    super("==", izquierda, derecha); }
}
