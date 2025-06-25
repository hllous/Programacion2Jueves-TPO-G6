package interfaces;
import java.util.List;

public interface INodo<T> {
    T getValor();
    List<INodo<T>> getVecinos();
    List<Integer> getPesos();
    void agregarVecino(INodo<T> vecino, int peso);
}
