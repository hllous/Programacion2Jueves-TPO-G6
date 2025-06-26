package interfaces;
import java.util.List;
import java.util.Map;

public interface IGrafo<T> {
    void agregarNodo(T valor);
    void agregarArista(T origen, T destino, int peso);
    Map<T, INodo<T>> getNodos();
    void dfs(T inicio);
    void bfs(T inicio);
    void matrizDeAdyacencia();
}
