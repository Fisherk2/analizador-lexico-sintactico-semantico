package lenguaje;

import javax.swing.*;
import java.util.LinkedList;

/**
 * Clase que recibe las propiedades de un automata finito no determinista.
 */
public class Automata {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Alfabeto.
     */
    public final String[] sigma;
    /**
     * Conjunto de estados.
     */
    public final String[] k;
    /**
     * Estado inicial.
     */
    public final String s;
    /**
     * Conjunto de estados finales.
     */
    public final String[] F;
    /**
     * Conjunto de relaciones de transiciones.
     */
    public final Transicion[] delta;

    /**
     * Clase que recibe las propiedades del automata generado en JSON.
     *
     * @param sigma Alfabeto
     * @param k     Conjunto de estados
     * @param s     Estado inicial
     * @param F     Conjunto de estados finales
     * @param delta Conjunto de relaciones de transiciones del automata.
     */
    public Automata(String[] sigma,
                    String[] k,
                    String s,
                    String[] F,
                    Transicion[] delta) {

        this.sigma = sigma;
        this.k = k;
        this.s = s;
        this.F = F;
        this.delta = delta;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ ALGORITMO DE RECORRIDO DEL AFN ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que evalua el lexema en base al automata finito no determinista.
     * @param lexema Nombre propio, cadena, palabra a evaluar.
     * @return Aceptacion o rechazo del lexema devuelto por el automata.
     */
    public boolean esAceptada(String lexema) {

        // ◂ ◂ ◂ ◂ Preparacion del recorrido del automata ▸ ▸ ▸ ▸ //
        //LinkedList<String[]> conjunto_estados_recorridos = new LinkedList<>();
        LinkedList<String[]> conjunto_estados_siguientes = new LinkedList<>(); //Estados que se añadiran en estados actuales.
        LinkedList<String> estados_actuales = new LinkedList<>();
        LinkedList<Transicion> transiciones_disponibles = new LinkedList<>();

        if (s.isEmpty()) {
            System.err.println("ERROR, NO SE ESTABLECIO UN ESTADO INICIAL, VERIFIQUE SUS CONFIGURACIONES DEL AUTOMATA");
            return false;
        }

        // ◂ ◂ ◂ ◂ Agregamos el estado inicial ▸ ▸ ▸ ▸ //
        estados_actuales.add(s);

        // ◂ ◂ ◂ ◂ Recorrido del arreglo de caracteres ▸ ▸ ▸ ▸ //
        for (char caracter : lexema.toCharArray()) {

            // ◂ ◂ Vaciamos listas enlazadas para una nueva busqueda de estados proximos ▸ ▸ //
            conjunto_estados_siguientes.clear();
            transiciones_disponibles.clear();

            // ◂ ◂ Obtenemos las transiciones disponibles para los estados actuales ▸ ▸ //
            for(String estadoActual: estados_actuales){
                for(Transicion transicion: delta){
                    // ◂ Si el estado actual es igual al de la transicion, se agrega a transiciones disponibles ▸ //
                    if(estadoActual.equalsIgnoreCase(transicion.estadoActual)){
                        transiciones_disponibles.add(transicion);
                    }
                }
            }

            // ◂ ◂ Si no encuentra una sola transicion por defecto la palabra sera rechazada ▸ ▸ //
            if (transiciones_disponibles.isEmpty()) {
                return false;
            }

            // ◂ ◂ Encontrar los estados siguientes dependiendo si existe alguna entrada de la transicion ▸ ▸ //
            for (Transicion transicion: transiciones_disponibles){
                for(String estadoActual: estados_actuales){

                    // ◂ Como tenemos multiples estados actuales, evaluaremos uno por uno y tratamos sus entradas respectivas ▸ //
                    if(estadoActual.equalsIgnoreCase(transicion.estadoActual)){

                        // ◂ Añadimos los estados siguientes dependiendo de la entrada del caracter ▸ //
                        if(transicion.entrada.equals(String.valueOf(caracter))){
                            //conjunto_estados_recorridos.add(transicion.estadosSiguientes);
                            conjunto_estados_siguientes.add(transicion.estadosSiguientes);
                        }
                    }
                }
            }

            // ◂ ◂ Si despues de buscar en todas las transiciones no coincide el caracter de entrada, la palabra sera rechazada ▸ ▸ //
            if(conjunto_estados_siguientes.isEmpty()){
                return false;
            }

            // ◂ ◂ Establecemos los nuevos estados actuales ▸ ▸ //
            estados_actuales.clear();
            for(String[] estadosSiguientes: conjunto_estados_siguientes){
                for(String estadoSiguiente: estadosSiguientes){
                    estados_actuales.add(estadoSiguiente); //Añadimos todos los estados nuevos a evaluar en la proxima iteracion
                }
            }

        } // ❯❯❯❯❯❯ Termina de recorrer el automata ❮❮❮❮❮❮ //

        return esEstadoFinal(estados_actuales);
    }

    /**
     * Evaluamos si en el recorrido del automata, los ultimos estados recorridos son finales o no.
     * @param ultimos_estados Lista enlazada de ultimos estados actuales.
     * @return Si alguno de los estados actuales pertenece al conjunto de estados finales = TRUE.
     */
    private boolean esEstadoFinal(LinkedList<String> ultimos_estados) {
        for(String ultimoEstado: ultimos_estados){

            // ◂ ◂ Verificamos si el ultimo estado pertenece al conjunto de estados finales ▸ ▸ //
            for(String estadoFinal: F){
                if(ultimoEstado.equalsIgnoreCase(estadoFinal)){
                    return true;
                }
            }
        }
        return false;
    }
}

