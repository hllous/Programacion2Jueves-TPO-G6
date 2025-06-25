package models;

import interfaces.INodo;
import java.util.ArrayList;
import java.util.List;

public class Nodo<T> implements INodo<T> {

    private T valor;
    private List<INodo<T>> vecinos = new ArrayList<>();
    private List<Integer> pesos = new ArrayList<>();

    public Nodo(T valor) {
        this.valor = valor;
    }

    // Getters y Setters

    @Override
    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    @Override
    public List<INodo<T>> getVecinos() {
        return vecinos;
    }

    @Override
    public List<Integer> getPesos() {
        return pesos;
    }

    // Metodos y Procedimientos

    @Override
    public void agregarVecino(INodo<T> vecino, int peso) {
        vecinos.add(vecino);
        pesos.add(peso);
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "valor=" + valor +
                '}';
    }
}