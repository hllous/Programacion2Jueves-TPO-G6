package servicios;

import interfaces.IAlgoritmos;
import interfaces.IGrafo;
import interfaces.INodo;
import models.NodoDistancia;

import java.util.*;

public class Algoritmos<T> implements IAlgoritmos<T> {

    @Override
    public List<T> dijkstra(IGrafo<T> grafo, T origen, T destino) {
        Map<T, INodo<T>> nodos = grafo.getNodos();
        Map<T, Double> distancias = new HashMap<>();
        Map<T, T> anterior = new HashMap<>();
        Set<T> visitados = new HashSet<>();
        PriorityQueue<NodoDistancia<T>> cola = new PriorityQueue<>();

        for (T nodo : nodos.keySet()) {
            distancias.put(nodo, Double.POSITIVE_INFINITY);
        }

        distancias.put(origen, 0.0);
        cola.add(new NodoDistancia<>(origen, 0.0));

        while (!cola.isEmpty()) {
            NodoDistancia<T> actual = cola.poll();

            if (visitados.contains(actual.getNodo())) continue;

            if (actual.getNodo().equals(destino)) break;

            visitados.add(actual.getNodo());

            INodo<T> nodoActual = nodos.get(actual.getNodo());
            List<? extends INodo<T>> vecinos = nodoActual.getVecinos();
            List<Integer> pesos = nodoActual.getPesos();

            for (int i = 0; i < vecinos.size(); i++) {
                T vecino = vecinos.get(i).getValor();
                double peso = pesos.get(i);

                double nuevaDistancia = distancias.get(actual.getNodo()) + peso;

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    anterior.put(vecino, actual.getNodo());
                    cola.add(new NodoDistancia<>(vecino, nuevaDistancia));
                }
            }
        }

        return reconstruirCamino(anterior, destino);
    }

    private List<T> reconstruirCamino(Map<T, T> anterior, T actual) {
        LinkedList<T> camino = new LinkedList<>();
        T nodoActual = actual;

        if (!anterior.containsKey(actual) && anterior.size() > 0) {
            return camino;
        }

        while (nodoActual != null) {
            camino.addFirst(nodoActual);
            nodoActual = anterior.get(nodoActual);
        }

        return camino;
    }

}
