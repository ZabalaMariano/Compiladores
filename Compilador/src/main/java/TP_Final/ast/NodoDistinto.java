package TP_Final.ast;

public class NodoDistinto extends NodoCondicion{
    public NodoDistinto (NodoExpresion izquierda, NodoExpresion derecha) {
    super("!=", izquierda, derecha); }
}
