package tests;

import models.Grafo;

public class TestGrafo {
    public static void main(String[] args) {
        Grafo<Integer> grafo = new Grafo<>();

        grafo.agregarNodo(1);
        grafo.agregarNodo(2);
        grafo.agregarNodo(3);
        grafo.agregarNodo(4);
        grafo.agregarNodo(5);
        grafo.agregarNodo(6);
        grafo.agregarNodo(7);
        grafo.agregarNodo(8);

        grafo.agregarArista(1, 2, 4);
        grafo.agregarArista(1, 3, 2);
        grafo.agregarArista(2, 3, 1);
        grafo.agregarArista(2, 4, 5);
        grafo.agregarArista(3, 4, 8);
        grafo.agregarArista(3, 5, 10);
        grafo.agregarArista(4, 5, 2);
        grafo.agregarArista(4, 6, 6);
        grafo.agregarArista(5, 6, 3);
        grafo.agregarArista(5, 7, 7);
        grafo.agregarArista(6, 8, 1);
        grafo.agregarArista(7, 8, 4);

        System.out.println("----- Recorrido DFS -----");
        grafo.dfs(1);
        System.out.println("----- Recorrido BFS  -----");
        grafo.bfs(2);
        System.out.println("----- Matriz de Adyacencia -----");
        grafo.matrizDeAdyacencia();
    }
}