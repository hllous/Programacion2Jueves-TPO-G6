package models;

import interfaces.IEstacion;
import java.util.Objects;

public class Estacion implements IEstacion {
    private final String nombre;
    private final String linea;

    public Estacion(String nombre, String linea) {
        this.nombre = nombre;
        this.linea = linea;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getLinea() {
        return linea;
    }

    @Override
    public String toString() {
        return nombre + " (" + linea + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IEstacion)) return false;
        IEstacion est = (IEstacion) o;
        return Objects.equals(nombre, est.getNombre()) && Objects.equals(linea, est.getLinea());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, linea);
    }
}