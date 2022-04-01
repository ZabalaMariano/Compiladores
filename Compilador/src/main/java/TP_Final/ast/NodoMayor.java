package TP_Final.ast;

public class NodoMayor extends NodoCondicion{
    public NodoMayor (NodoExpresion izquierda, NodoExpresion derecha) {
    super(">", izquierda, derecha); }
}

