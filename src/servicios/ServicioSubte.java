package servicios;

import interfaces.IAlgoritmos;
import interfaces.IGrafo;
import interfaces.IEstacion;
import models.Estacion;
import models.Grafo;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ServicioSubte {
    // Constantes de tiempos EN SEGUNDOS
    public static final int UN_MINUTO = 60;
    public static final int DOS_MINUTOS = 120;
    public static final int TRES_MINUTOS = 180;
    public static final int CUATRO_MINUTOS = 240;
    public static final int CINCO_MINUTOS = 300;

    // Tiempos para cada línea EN SEGUNDOS (estos tiempos son tomados a ojo, por experiencias propias)
    private int pesoLineaA = 120; // 2 minutos entre estaciones
    private int pesoLineaB = 135; // 2.25 minutos entre estaciones
    private int pesoLineaC = 110; // 1.83 minutos entre estaciones
    private int pesoLineaD = 125; // 2.08 minutos entre estaciones
    private int pesoLineaE = 130; // 2.17 minutos entre estaciones
    private int pesoLineaF = 115; // 1.92 minutos entre estaciones
    private int pesoLineaH = 120; // 2 minutos entre estaciones
    private int pesoLineaG = 125; // 2.08 minutos entre estaciones
    private int pesoLineaI = 130; // 2.16 minutos entre estaciones
    private int pesoLineaJ = 170; // 2.83 minutos entre estaciones
    private int pesoLineaK = 132; // 2.2 minutos entre estaciones

    private IGrafo<IEstacion> subte;
    private IAlgoritmos<IEstacion> algoritmos;

    private List<IEstacion> lineaA;
    private List<IEstacion> lineaB;
    private List<IEstacion> lineaC;
    private List<IEstacion> lineaD;
    private List<IEstacion> lineaE;
    private List<IEstacion> lineaF;
    private List<IEstacion> lineaG;
    private List<IEstacion> lineaH;
    private List<IEstacion> lineaI;
    private List<IEstacion> lineaJ;
    private List<IEstacion> lineaK;


    /// ----- Constructor, getters y setters -----

    public ServicioSubte() {
        this.subte = new Grafo<>();
        this.algoritmos = new Algoritmos<>();
        inicializarLineas();
        inicializarRedSubte();
    }

    public IGrafo<IEstacion> getSubte() { return subte; }
    public List<IEstacion> getLineaA() { return lineaA; }
    public List<IEstacion> getLineaB() { return lineaB; }
    public List<IEstacion> getLineaC() { return lineaC; }
    public List<IEstacion> getLineaD() { return lineaD; }
    public List<IEstacion> getLineaE() { return lineaE; }
    public List<IEstacion> getLineaF() { return lineaF; }
    public List<IEstacion> getLineaG() { return lineaG; }
    public List<IEstacion> getLineaH() { return lineaH; }
    public List<IEstacion> getLineaI() { return lineaI; }
    public List<IEstacion> getLineaJ() { return lineaJ; }
    public List<IEstacion> getLineaK() { return lineaK; }

    public int getTiempoEntreEstaciones(String linea) {
        switch (linea) {
            case "A": return pesoLineaA;
            case "B": return pesoLineaB;
            case "C": return pesoLineaC;
            case "D": return pesoLineaD;
            case "E": return pesoLineaE;
            case "F": return pesoLineaF;
            case "G": return pesoLineaG;
            case "H": return pesoLineaH;
            case "I": return pesoLineaI;
            case "J": return pesoLineaJ; // Nueva línea J
            case "K": return pesoLineaK; // Nueva línea K
            default: return TRES_MINUTOS;
        }
    }

    /// Utilizacion de dijkstra

    public List<IEstacion> encontrarRutaOptima(IEstacion origen, IEstacion destino) {
        return algoritmos.dijkstra(subte, origen, destino);
    }

    /// ----- METODOS PARA CALCULO DE TIEMPO -----

    public int calcularTiempoTotal(List<IEstacion> ruta) {
        int tiempoTotal = 0;
        String lineaActual = null;

        if (!ruta.isEmpty()) {
            lineaActual = ruta.get(0).getLinea();
            tiempoTotal += generarTiempoEspera(lineaActual);
        }

        for (int i = 0; i < ruta.size() - 1; i++) {
            IEstacion actual = ruta.get(i);
            IEstacion siguiente = ruta.get(i + 1);

            if (!actual.getLinea().equals(siguiente.getLinea())) {
                lineaActual = siguiente.getLinea();
                tiempoTotal += generarTiempoEspera(lineaActual);
            }

            tiempoTotal += getTiempoEntreEstaciones(lineaActual);
        }

        return tiempoTotal;
    }

    /// Genero tiempo random de espera, simulando cuanto esperar hasta que venga la proxima formacion

    private int generarTiempoEspera(String linea) {
        Random random = new Random();
        return (random.nextInt(5) + 1) * UN_MINUTO;
    }

    /// ----- FIN METODOS PARA CALCULO DE TIEMPO -----


    private IEstacion buscar(List<IEstacion> linea, String nombre) {
        for (IEstacion est : linea) {
            if (est.getNombre().equals(nombre)) {
                return est;
            }
        }
        return null;
    }

    /// Metodo para conectar vertices SOLO de una misma linea de subte.

    private void conectarLinea(IGrafo<IEstacion> grafo, List<IEstacion> linea, int peso) {
        for (int i = 0; i < linea.size() - 1; i++) {
            grafo.agregarArista(linea.get(i), linea.get(i + 1), peso);
        }
    }

    /// Creo las lineas SIN las conexiones

    private void inicializarLineas() {

        lineaA = Arrays.asList(
                new Estacion("Plaza de Mayo", "A"), new Estacion("Peru", "A"), new Estacion("Piedras", "A"),
                new Estacion("Lima", "A"), new Estacion("Sáenz Peña", "A"), new Estacion("Congreso", "A"),
                new Estacion("Pasco", "A"), new Estacion("Alberti", "A"), new Estacion("Plaza Miserere", "A"),
                new Estacion("Loria", "A"), new Estacion("Castro Barros", "A"), new Estacion("Río de Janeiro", "A"),
                new Estacion("Acoyte", "A"), new Estacion("Primera Junta", "A"), new Estacion("Puan", "A"),
                new Estacion("Carabobo", "A"), new Estacion("San José de Flores", "A"), new Estacion("San Pedrito", "A")
        );

        lineaB = Arrays.asList(
                new Estacion("Leandro N. Alem", "B"), new Estacion("Florida", "B"), new Estacion("Carlos Pellegrini", "B"),
                new Estacion("Uruguay", "B"), new Estacion("Callao", "B"), new Estacion("Pasteur", "B"),
                new Estacion("Pueyrredón", "B"), new Estacion("Carlos Gardel", "B"), new Estacion("Medrano", "B"),
                new Estacion("Ángel Gallardo", "B"), new Estacion("Malabia", "B"), new Estacion("Dorrego", "B"),
                new Estacion("Federico Lacroze", "B"), new Estacion("Tronador-Villa Ortúzar", "B"),
                new Estacion("Los Incas-Parque Chas", "B"), new Estacion("Echeverría", "B"),
                new Estacion("Juan Manuel de Rosas", "B")
        );

        lineaC = Arrays.asList(
                new Estacion("Retiro", "C"), new Estacion("General San Martín", "C"), new Estacion("Lavalle", "C"),
                new Estacion("Diagonal Norte", "C"), new Estacion("Avenida de Mayo", "C"), new Estacion("Moreno", "C"),
                new Estacion("Independencia", "C"), new Estacion("Constitución", "C")
        );

        lineaD = Arrays.asList(
                new Estacion("Catedral", "D"), new Estacion("9 de Julio", "D"), new Estacion("Tribunales", "D"),
                new Estacion("Callao", "D"), new Estacion("Facultad de Medicina", "D"), new Estacion("Pueyrredón", "D"),
                new Estacion("Aguero", "D"), new Estacion("Bulnes", "D"), new Estacion("Scalabrini Ortiz", "D"),
                new Estacion("Plaza Italia", "D"), new Estacion("Palermo", "D"), new Estacion("Ministro Carranza", "D"),
                new Estacion("Olleros", "D"), new Estacion("José Hernández", "D"), new Estacion("Juramento", "D"),
                new Estacion("Congreso de Tucumán", "D")
        );

        lineaE = Arrays.asList(
                new Estacion("Retiro", "E"), new Estacion("Catalinas", "E"), new Estacion("Correo Central", "E"),
                new Estacion("Bolívar", "E"), new Estacion("Belgrano", "E"), new Estacion("Independencia", "E"),
                new Estacion("San Jose", "E"), new Estacion("Entre Rios", "E"), new Estacion("Pichincha", "E"),
                new Estacion("Jujuy", "E"), new Estacion("Gral Urquiza", "E"), new Estacion("Boedo", "E"),
                new Estacion("Av. La Plata", "E"), new Estacion("Jose Maria Moreno", "E"),
                new Estacion("Emilio Mitre", "E"), new Estacion("Medalla Milagrosa", "E"),
                new Estacion("Varela", "E"), new Estacion("Plaza de los virreyes", "E")
        );

        lineaF = Arrays.asList(
                new Estacion("Plaza Italia", "F"), new Estacion("Parque las Heras", "F"),
                new Estacion("Hospital Rivadavia", "F"), new Estacion("Recoleta", "F"),
                new Estacion("Santa Fe", "F"), new Estacion("Tucuman", "F"), new Estacion("Rivadavia", "F"),
                new Estacion("Chile", "F"), new Estacion("Cochabamba", "F"), new Estacion("Constitucion", "F"),
                new Estacion("Brandsen", "F")
        );

        lineaG = Arrays.asList(
                new Estacion("Retiro", "G"), new Estacion("Lavalle", "G"), new Estacion("Tucuman", "G"),
                new Estacion("Entre Rios", "G"), new Estacion("Pueyrredon", "G"), new Estacion("Plaza Almagro", "G"),
                new Estacion("Angel Gallardo", "G"), new Estacion("Cid Campeador", "G")
        );

        lineaH = Arrays.asList(
                new Estacion("Hospitales", "H"), new Estacion("Parque Patricios", "H"), new Estacion("Caseros", "H"),
                new Estacion("Inclán", "H"), new Estacion("Humberto I", "H"), new Estacion("Venezuela", "H"),
                new Estacion("Once", "H"), new Estacion("Corrientes", "H"), new Estacion("Córdoba", "H"),
                new Estacion("Santa Fe", "H"), new Estacion("Las Heras", "H"), new Estacion("Facultad de Derecho", "H")
        );

        lineaI = Arrays.asList(
                new Estacion("Parque Chacabuco", "I"), new Estacion("Rivadavia", "I"), new Estacion("Neuquen", "I"),
                new Estacion("Cid Campeador", "I"), new Estacion("Corrientes", "I"), new Estacion("Niceto Vega", "I"),
                new Estacion("Juan B Justo", "I"), new Estacion("Plaza Italia", "I")
        );

        lineaJ = Arrays.asList(
                new Estacion("Plaza Italia", "J"), new Estacion("Palermo Soho", "J"), new Estacion("Dorrego", "J"),
                new Estacion("Paternal", "J"), new Estacion("Villa General Mitre", "J"), new Estacion("San Pedrito", "J"),
                new Estacion("Plaza de los virreyes", "J"), new Estacion("Barrio Illia", "J"), new Estacion("Hospitales", "J")
        );

        lineaK = Arrays.asList(
                new Estacion("Córdoba", "K"), new Estacion("Tucuman", "K"), new Estacion("Sáenz Peña", "K"),
                new Estacion("Independencia", "K"), new Estacion("Jujuy", "K"), new Estacion("Mexico", "K"),
                new Estacion("Castro Barros", "K"), new Estacion("Medrano", "K")
        );
    }

    private void inicializarRedSubte() {

        /// Creo nodos para todas las estaciones de cada linea

        for (IEstacion est : lineaA) subte.agregarNodo(est);
        for (IEstacion est : lineaB) subte.agregarNodo(est);
        for (IEstacion est : lineaC) subte.agregarNodo(est);
        for (IEstacion est : lineaD) subte.agregarNodo(est);
        for (IEstacion est : lineaE) subte.agregarNodo(est);
        for (IEstacion est : lineaF) subte.agregarNodo(est);
        for (IEstacion est : lineaG) subte.agregarNodo(est);
        for (IEstacion est : lineaH) subte.agregarNodo(est);
        for (IEstacion est : lineaI) subte.agregarNodo(est);
        for (IEstacion est : lineaJ) subte.agregarNodo(est);
        for (IEstacion est : lineaK) subte.agregarNodo(est);

        /// Conecto estaciones consecutivas por línea

        conectarLinea(subte,lineaA,pesoLineaA);
        conectarLinea(subte,lineaB,pesoLineaB);
        conectarLinea(subte,lineaC,pesoLineaC);
        conectarLinea(subte,lineaD,pesoLineaD);
        conectarLinea(subte,lineaE,pesoLineaE);
        conectarLinea(subte,lineaF,pesoLineaF);
        conectarLinea(subte,lineaG,pesoLineaG);
        conectarLinea(subte,lineaH,pesoLineaH);
        conectarLinea(subte,lineaI,pesoLineaI);
        conectarLinea(subte,lineaJ,pesoLineaJ);
        conectarLinea(subte,lineaK,pesoLineaK);

        /// Aca si creo las conexiones entre estaciones

        /// Conexiones entre líneas
        /// Línea A
        subte.agregarArista(buscar(lineaA,"Plaza Miserere"), buscar(lineaH,"Once"), pesoLineaA + CINCO_MINUTOS);
        subte.agregarArista(buscar(lineaA,"Lima"), buscar(lineaC,"Avenida de Mayo"), pesoLineaA + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaA,"Peru"), buscar(lineaD,"Catedral"), pesoLineaA + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaA,"Peru"), buscar(lineaE,"Bolívar"), pesoLineaA + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaA,"Congreso"), buscar(lineaF,"Rivadavia"), pesoLineaA + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaA,"Puan"), buscar(lineaI,"Rivadavia"), pesoLineaA + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaA,"San Pedrito"), buscar(lineaJ,"San Pedrito"), pesoLineaA + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaA,"Sáenz Peña"), buscar(lineaK,"Sáenz Peña"), pesoLineaA + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaA,"Castro Barros"), buscar(lineaK,"Castro Barros"), pesoLineaA + DOS_MINUTOS + 15);

        /// Línea B
        subte.agregarArista(buscar(lineaB,"Pueyrredón"), buscar(lineaH,"Corrientes"), pesoLineaB + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaB,"Carlos Pellegrini"), buscar(lineaC,"Diagonal Norte"), pesoLineaB + CINCO_MINUTOS);
        subte.agregarArista(buscar(lineaB,"Carlos Pellegrini"), buscar(lineaD,"9 de Julio"), pesoLineaB + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaB,"Leandro N. Alem"), buscar(lineaE,"Correo Central"), pesoLineaB + UN_MINUTO + 30);
        subte.agregarArista(buscar(lineaB,"Ángel Gallardo"), buscar(lineaG,"Angel Gallardo"), pesoLineaB + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaB,"Ángel Gallardo"), buscar(lineaI,"Corrientes"), pesoLineaB + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaB,"Dorrego"), buscar(lineaJ,"Dorrego"), pesoLineaB + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaB,"Callao"), buscar(lineaK,"Tucuman"), pesoLineaB + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaB,"Medrano"), buscar(lineaK,"Medrano"), pesoLineaB + DOS_MINUTOS);

        /// Línea C
        subte.agregarArista(buscar(lineaC,"Independencia"), buscar(lineaE,"Independencia"), pesoLineaC + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaC,"Avenida de Mayo"), buscar(lineaA,"Lima"), pesoLineaC + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaC,"Diagonal Norte"), buscar(lineaB,"Carlos Pellegrini"), pesoLineaC + CINCO_MINUTOS);
        subte.agregarArista(buscar(lineaC,"Diagonal Norte"), buscar(lineaD,"9 de Julio"), pesoLineaC + DOS_MINUTOS + 45);
        subte.agregarArista(buscar(lineaC,"Retiro"), buscar(lineaE,"Retiro"), pesoLineaC + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaC,"Retiro"), buscar(lineaG,"Retiro"), pesoLineaC + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaC,"Constitución"), buscar(lineaF,"Constitucion"), pesoLineaC + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaC,"Independencia"), buscar(lineaK,"Independencia"), pesoLineaC + TRES_MINUTOS + 15);

        /// Línea D
        subte.agregarArista(buscar(lineaD,"Pueyrredón"), buscar(lineaH,"Santa Fe"), pesoLineaD + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaD,"Pueyrredón"), buscar(lineaG,"Pueyrredon"), pesoLineaD + DOS_MINUTOS + 30);
        subte.agregarArista(buscar(lineaD,"9 de Julio"), buscar(lineaB,"Carlos Pellegrini"), pesoLineaD + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaD,"9 de Julio"), buscar(lineaC,"Diagonal Norte"), pesoLineaD + DOS_MINUTOS + 45);
        subte.agregarArista(buscar(lineaD,"Catedral"), buscar(lineaA,"Peru"), pesoLineaD + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaD,"Catedral"), buscar(lineaE,"Bolívar"), pesoLineaD + CINCO_MINUTOS);
        subte.agregarArista(buscar(lineaD,"Plaza Italia"), buscar(lineaF,"Plaza Italia"), pesoLineaD + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaD,"Plaza Italia"), buscar(lineaI,"Plaza Italia"), pesoLineaD + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaD,"Plaza Italia"), buscar(lineaJ,"Plaza Italia"), pesoLineaD + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaD,"Callao"), buscar(lineaK,"Tucuman"), pesoLineaD + TRES_MINUTOS + 45);

        /// Línea E
        subte.agregarArista(buscar(lineaE,"Jujuy"), buscar(lineaH,"Humberto I"), pesoLineaE + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Independencia"), buscar(lineaC,"Independencia"), pesoLineaE + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Bolívar"), buscar(lineaA,"Peru"), pesoLineaE + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Bolívar"), buscar(lineaD,"Catedral"), pesoLineaE + CINCO_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Correo Central"), buscar(lineaB,"Leandro N. Alem"), pesoLineaE + UN_MINUTO + 30);
        subte.agregarArista(buscar(lineaE,"Retiro"), buscar(lineaC,"Retiro"), pesoLineaE + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Retiro"), buscar(lineaG,"Retiro"), pesoLineaE + DOS_MINUTOS + 30);
        subte.agregarArista(buscar(lineaE,"Entre Rios"), buscar(lineaF,"Cochabamba"), pesoLineaE + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Emilio Mitre"), buscar(lineaI,"Parque Chacabuco"), pesoLineaE + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Plaza de los virreyes"), buscar(lineaJ,"Plaza de los virreyes"), pesoLineaE + TRES_MINUTOS + 30);
        subte.agregarArista(buscar(lineaE,"Independencia"), buscar(lineaK,"Independencia"), pesoLineaE + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaE,"Jujuy"), buscar(lineaK,"Jujuy"), pesoLineaE + DOS_MINUTOS);

        /// Línea F
        subte.agregarArista(buscar(lineaF,"Plaza Italia"), buscar(lineaD,"Plaza Italia"), pesoLineaF + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaF,"Recoleta"), buscar(lineaH,"Las Heras"), pesoLineaF + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaF,"Tucuman"), buscar(lineaG,"Tucuman"), pesoLineaF + DOS_MINUTOS + 45);
        subte.agregarArista(buscar(lineaF,"Rivadavia"), buscar(lineaA,"Congreso"), pesoLineaF + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaF,"Cochabamba"), buscar(lineaE,"Entre Rios"), pesoLineaF + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaF,"Constitucion"), buscar(lineaC,"Constitución"), pesoLineaF + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaF,"Plaza Italia"), buscar(lineaJ,"Plaza Italia"), pesoLineaF + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaF,"Tucuman"), buscar(lineaK,"Tucuman"), pesoLineaF + DOS_MINUTOS);

        /// Línea G
        subte.agregarArista(buscar(lineaG,"Retiro"), buscar(lineaC,"Retiro"), pesoLineaG + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaG,"Retiro"), buscar(lineaE,"Retiro"), pesoLineaG + DOS_MINUTOS + 30);
        subte.agregarArista(buscar(lineaG,"Tucuman"), buscar(lineaF,"Tucuman"), pesoLineaG + DOS_MINUTOS + 45);
        subte.agregarArista(buscar(lineaG,"Pueyrredon"), buscar(lineaD,"Pueyrredón"), pesoLineaG + DOS_MINUTOS + 30);
        subte.agregarArista(buscar(lineaG,"Pueyrredon"), buscar(lineaH,"Santa Fe"), pesoLineaG + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaG,"Angel Gallardo"), buscar(lineaB,"Ángel Gallardo"), pesoLineaG + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaG,"Angel Gallardo"), buscar(lineaI,"Corrientes"), pesoLineaG + DOS_MINUTOS + 15);
        subte.agregarArista(buscar(lineaG,"Cid Campeador"), buscar(lineaI,"Cid Campeador"), pesoLineaG + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaG,"Tucuman"), buscar(lineaK,"Tucuman"), pesoLineaG + DOS_MINUTOS + 30);

        /// Línea H
        subte.agregarArista(buscar(lineaH,"Humberto I"), buscar(lineaE,"Jujuy"), pesoLineaH + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaH,"Once"), buscar(lineaA,"Plaza Miserere"), pesoLineaH + CINCO_MINUTOS);
        subte.agregarArista(buscar(lineaH,"Corrientes"), buscar(lineaB,"Pueyrredón"), pesoLineaH + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaH,"Santa Fe"), buscar(lineaD,"Pueyrredón"), pesoLineaH + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaH,"Santa Fe"), buscar(lineaG,"Pueyrredon"), pesoLineaH + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaH,"Las Heras"), buscar(lineaF,"Recoleta"), pesoLineaH + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaH,"Hospitales"), buscar(lineaJ,"Hospitales"), pesoLineaH + DOS_MINUTOS + 15);
        subte.agregarArista(buscar(lineaH,"Humberto I"), buscar(lineaK,"Jujuy"), pesoLineaH + TRES_MINUTOS + 30);
        subte.agregarArista(buscar(lineaH,"Córdoba"), buscar(lineaK,"Córdoba"), pesoLineaH + DOS_MINUTOS);

        /// Línea I
        subte.agregarArista(buscar(lineaI,"Parque Chacabuco"), buscar(lineaE,"Emilio Mitre"), pesoLineaI + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaI,"Rivadavia"), buscar(lineaA,"Puan"), pesoLineaI + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaI,"Cid Campeador"), buscar(lineaG,"Cid Campeador"), pesoLineaI + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaI,"Corrientes"), buscar(lineaG,"Angel Gallardo"), pesoLineaI + DOS_MINUTOS + 15);
        subte.agregarArista(buscar(lineaI,"Corrientes"), buscar(lineaB,"Ángel Gallardo"), pesoLineaI + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaI,"Plaza Italia"), buscar(lineaD,"Plaza Italia"), pesoLineaI + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaI,"Plaza Italia"), buscar(lineaF,"Plaza Italia"), pesoLineaI + DOS_MINUTOS + 30);
        subte.agregarArista(buscar(lineaI,"Plaza Italia"), buscar(lineaJ,"Plaza Italia"), pesoLineaI + DOS_MINUTOS + 30);

        /// Línea J (Nueva)
        subte.agregarArista(buscar(lineaJ,"Plaza Italia"), buscar(lineaF,"Plaza Italia"), pesoLineaJ + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaJ,"Plaza Italia"), buscar(lineaD,"Plaza Italia"), pesoLineaJ + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaJ,"Plaza Italia"), buscar(lineaI,"Plaza Italia"), pesoLineaJ + DOS_MINUTOS + 30);
        subte.agregarArista(buscar(lineaJ,"Dorrego"), buscar(lineaB,"Dorrego"), pesoLineaJ + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaJ,"San Pedrito"), buscar(lineaA,"San Pedrito"), pesoLineaJ + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaJ,"Plaza de los virreyes"), buscar(lineaE,"Plaza de los virreyes"), pesoLineaJ + TRES_MINUTOS + 30);
        subte.agregarArista(buscar(lineaJ,"Hospitales"), buscar(lineaH,"Hospitales"), pesoLineaJ + DOS_MINUTOS + 15);

        /// Línea K
        subte.agregarArista(buscar(lineaK,"Córdoba"), buscar(lineaH,"Córdoba"), pesoLineaK + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaK,"Tucuman"), buscar(lineaF,"Tucuman"), pesoLineaK + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaK,"Tucuman"), buscar(lineaG,"Tucuman"), pesoLineaK + DOS_MINUTOS + 30);
        subte.agregarArista(buscar(lineaK,"Tucuman"), buscar(lineaB,"Callao"), pesoLineaK + CUATRO_MINUTOS);
        subte.agregarArista(buscar(lineaK,"Tucuman"), buscar(lineaD,"Callao"), pesoLineaK + TRES_MINUTOS + 45);
        subte.agregarArista(buscar(lineaK,"Sáenz Peña"), buscar(lineaA,"Sáenz Peña"), pesoLineaK + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaK,"Independencia"), buscar(lineaE,"Independencia"), pesoLineaK + TRES_MINUTOS);
        subte.agregarArista(buscar(lineaK,"Independencia"), buscar(lineaC,"Independencia"), pesoLineaK + TRES_MINUTOS + 15);
        subte.agregarArista(buscar(lineaK,"Jujuy"), buscar(lineaE,"Jujuy"), pesoLineaK + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaK,"Jujuy"), buscar(lineaH,"Humberto I"), pesoLineaK + TRES_MINUTOS + 30);
        subte.agregarArista(buscar(lineaK,"Castro Barros"), buscar(lineaA,"Castro Barros"), pesoLineaK + DOS_MINUTOS + 15);
        subte.agregarArista(buscar(lineaK,"Medrano"), buscar(lineaB,"Medrano"), pesoLineaK + DOS_MINUTOS);
        subte.agregarArista(buscar(lineaK,"Medrano"), buscar(lineaK,"Córdoba"), pesoLineaK);
    }

    /// Componentes para la interfaz del servicio

    /// Inicio
    public void ejecutarServicio() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("------------------------------------------");
            System.out.println("        Servicio de Red de Subtes         ");
            System.out.println("------------------------------------------");
            System.out.println();

            /// Selecciono el origen
            System.out.println("Selecciona la estacion de origen");
            IEstacion origen = seleccionarEstacion(scanner);
            System.out.println();

            /// Selecciono el destino
            System.out.println("Selecciona la estacion de destino");
            IEstacion destino = seleccionarEstacion(scanner);
            System.out.println();

            /// Buscar y mostrar el camino óptimo
            List<IEstacion> rutaOptima = encontrarRutaOptima(origen, destino);

            int tiempoTotal = calcularTiempoTotal(rutaOptima);

            mostrarResultados(rutaOptima, tiempoTotal);

            System.out.println();
            System.out.print("¿Quiere hacer otro viaje? (si/no): ");
            String respuesta = scanner.nextLine();
            salir = respuesta.equalsIgnoreCase("no");
        }
    }


    private List<IEstacion> seleccionarLinea(Scanner scanner) {
        System.out.println("Líneas disponibles:");
        System.out.println("1) Línea A");
        System.out.println("2) Línea B");
        System.out.println("3) Línea C");
        System.out.println("4) Línea D");
        System.out.println("5) Línea E");
        System.out.println("6) Línea F");
        System.out.println("7) Línea G");
        System.out.println("8) Línea H");
        System.out.println("9) Línea I");
        System.out.println("10) Línea J");
        System.out.println("11) Línea K");

        System.out.print("\nSeleccione número de línea: ");
        int opcion = obtenerNumero(scanner, 1, 11);

        switch(opcion) {
            case 1: return lineaA;
            case 2: return lineaB;
            case 3: return lineaC;
            case 4: return lineaD;
            case 5: return lineaE;
            case 6: return lineaF;
            case 7: return lineaG;
            case 8: return lineaH;
            case 9: return lineaI;
            case 10: return lineaJ;
            case 11: return lineaK;
            default: return lineaA; // Por defecto
        }
    }

    private IEstacion seleccionarEstacion(Scanner scanner) {
        /// Le pregunto al usuario la linea (metodo de arriba)
        List<IEstacion> lineaSeleccionada = seleccionarLinea(scanner);

        /// Selecciono la estación dentro de la línea

        System.out.println("\nEstaciones: ");

        for (int i = 0; i < lineaSeleccionada.size(); i++) {
            System.out.println((i + 1) + ") " + lineaSeleccionada.get(i).getNombre());
        }

        System.out.print("\nSeleccione número de estación: ");

        int indiceEstacion = obtenerNumero(scanner, 1, lineaSeleccionada.size()) - 1;

        return lineaSeleccionada.get(indiceEstacion);
    }



    private int obtenerNumero(Scanner scanner, int min, int max) {
        int seleccion = 0;

        String entrada = scanner.nextLine();
        seleccion = Integer.parseInt(entrada);

        return seleccion;
    }

    private void mostrarResultados(List<IEstacion> ruta, int tiempoTotal) {
        if (ruta.isEmpty() || ruta.size() == 1) {
            System.out.println("No se pudo encontrar una ruta entre estas estaciones.");
            return;
        }

        System.out.println("======================");

        System.out.println("Inicio: ");
        System.out.println("- "+ ruta.get(0));

        for (int i = 1; i < ruta.size(); i++) {
            IEstacion actual = ruta.get(i);
            IEstacion anterior = ruta.get(i - 1);

            if (!anterior.getLinea().equals(actual.getLinea())) {
                System.out.println("Cambio de " + anterior.getLinea() + " a " + actual.getLinea());
            }

            System.out.println("- " + actual);
        }

        // Mostrar tiempo total
        int minutos = tiempoTotal / 60;
        int segundos = tiempoTotal % 60;

        System.out.println("\nTiempo total estimado: " + minutos + " minutos y " + segundos + " segundos");
        System.out.println("Total de estaciones: " + (ruta.size() - 1));
    }
}