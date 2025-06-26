
import servicios.ServicioSubte;


public class Main {
    public static void main(String[] args) {
        /*
        *
        * Corregimos el error de utilizar logica en el main (en el TP3), asi
        * que pasamos toda la logica de creacion de las lineas e interfaz para
        * seleccionar los caminos a "ServicioSubte.java"
        *
        * */


        ServicioSubte servicioSubte = new ServicioSubte();
        servicioSubte.ejecutarServicio();
    }
}