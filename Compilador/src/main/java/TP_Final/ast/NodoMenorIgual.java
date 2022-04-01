package TP_Final.ast;

public class NodoMenorIgual extends NodoCondicion{
    public NodoMenorIgual (NodoExpresion izquierda, NodoExpresion derecha) {
    super("<=", izquierda, derecha); }
}
