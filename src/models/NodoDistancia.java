package models;

public class NodoDistancia<E> implements Comparable<NodoDistancia<E>> {

    /// Utilizado en Algoritmos.java

    private E nodo;
    private double distancia;

    public NodoDistancia(E nodo, double distancia) {
        this.nodo = nodo;
        this.distancia = distancia;
    }

    public E getNodo() {
        return nodo;
    }

    public double getDistancia() {
        return distancia;
    }

    @Override
    public int compareTo(NodoDistancia<E> otro) {
        return Double.compare(this.distancia, otro.distancia);
    }
}
