package interfaces;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public interface IAlgoritmos<T> {
    List<T> dijkstra(IGrafo<T> grafo, T origen, T destino);
}
