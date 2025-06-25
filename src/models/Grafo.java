package models;

import interfaces.IGrafo;
import interfaces.INodo;
import java.util.*;

public class Grafo<T> implements IGrafo<T> {
    private Map<T, INodo<T>> nodos = new HashMap<>();

    /// Constructor

    public Grafo() {
    }

    /// Metodos Grafo

    @Override
    public void agregarNodo(T valor) {
        nodos.putIfAbsent(valor, new Nodo<>(valor));
    }

    @Override
    public void agregarArista(T origen, T destino, int peso) {
        INodo<T> nodoOrigen = nodos.get(origen);
        INodo<T> nodoDestino = nodos.get(destino);
        if (nodoOrigen != null && nodoDestino != null) {
            nodoOrigen.agregarVecino(nodoDestino, peso);
            nodoDestino.agregarVecino(nodoOrigen, peso);
        }
    }

    @Override
    public Map<T, INodo<T>> getNodos() {
        return nodos;
    }

    public void setNodos(Map<T, INodo<T>> nodos) {
        this.nodos = nodos;
    }
    /// Recorridos

    /// Depth First Search

    @Override
    public void dfs(T inicio) {
        if (!nodos.containsKey(inicio)) return; // precondición

        Set<T> visitados = new HashSet<>();
        System.out.println("Recorrido DFS:");
        dfsRec(nodos.get(inicio), visitados);
        System.out.println();
    }

    private void dfsRec(INodo<T> actual, Set<T> visitados) {
        visitados.add(actual.getValor());
        System.out.print(actual.getValor() + " -> ");

        List<INodo<T>> vecinos = actual.getVecinos();
        for (int i = vecinos.size() - 1; i >= 0; i--) {
            INodo<T> vecino = vecinos.get(i);
            if (!visitados.contains(vecino.getValor())) {
                dfsRec(vecino, visitados);
            }
        }
    }

    /// Breadth First Search

    @Override
    public void bfs(T inicio) {
        if (!nodos.containsKey(inicio)) {
            return;
        }

        Set<T> visitados = new HashSet<>();
        Queue<INodo<T>> cola = new LinkedList<>();

        INodo<T> nodoInicio = nodos.get(inicio);
        cola.add(nodoInicio);
        visitados.add(inicio);

        System.out.println("Recorrido BFS:");
        while (!cola.isEmpty()) {
            INodo<T> actual = cola.poll();
            System.out.print(actual.getValor() + " -> ");

            for (INodo<T> vecino : actual.getVecinos()) {
                if (!visitados.contains(vecino.getValor())) {
                    visitados.add(vecino.getValor());
                    cola.add(vecino);
                }
            }
        }
        System.out.println();
    }

    public void matrizDeAdyacencia() {
        System.out.println("Matriz de Adyacencia:");
        List<T> claves = new ArrayList<>(nodos.keySet());

        /// No podemos sortear claves por nombre
        //Collections.sort((List)claves);

        /// Encabezado
        System.out.print("   ");
        for (T key : claves) System.out.print(key + " ");
        System.out.println();

        /// Filas de la matriz
        for (T i : claves) {
            System.out.print(i + ": ");
            INodo<T> nodoI = nodos.get(i);

            for (T j : claves) {
                INodo<T> nodoJ = nodos.get(j);
                boolean esVecino = false;

                for (INodo<T> vecino : nodoI.getVecinos()) {
                    if (vecino == nodoJ) {
                        esVecino = true;
                        break;
                    }
                }

                System.out.print(esVecino ? "1 " : "0 ");
            }

            System.out.println();

        }
    }

    @Override
    public List<T> dijkstra(T origen, T destino) {
        Map<T, Double> dist = new HashMap<>();
        Map<T, T> anterior = new HashMap<>();
        PriorityQueue<T> cola = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        Set<T> visitados = new HashSet<>();

        for (T nodo : getNodos().keySet()) {
            dist.put(nodo, Double.POSITIVE_INFINITY);
        }
        dist.put(origen, 0.0);
        cola.add(origen);

        while (!cola.isEmpty()) {
            T actual = cola.poll();
            if (visitados.contains(actual)) continue;
            visitados.add(actual);
            if (actual.equals(destino)) break;

            INodo<T> nodoActual = getNodos().get(actual);
            List<? extends INodo<T>> vecinos = nodoActual.getVecinos();
            List<Integer> pesos = nodoActual.getPesos();

            for (int i = 0; i < vecinos.size(); i++) {
                T vecino = vecinos.get(i).getValor();
                double peso = pesos.get(i);
                double nuevaDist = dist.get(actual) + peso;
                if (nuevaDist < dist.get(vecino)) {
                    dist.put(vecino, nuevaDist);
                    anterior.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        // Reconstruir camino
        LinkedList<T> camino = new LinkedList<>();
        T actual = destino;
        while (actual != null && anterior.containsKey(actual)) {
            camino.addFirst(actual);
            actual = anterior.get(actual);
        }
        if (actual != null && actual.equals(origen)) {
            camino.addFirst(origen);
        }
        return camino;
    }

    @Override
    public List<T> aEstrella(T origen, T destino) {
        return List.of();
    }
}