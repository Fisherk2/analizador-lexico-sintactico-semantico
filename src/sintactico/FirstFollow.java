package sintactico;

import java.util.LinkedList;

/**
 * Clase que recibe el conjunto de first o follows de la gramatica.
 *
 * @implNote Este conjunto se debe rellenarse una vez instanciado un First o Follow.
 */
public class FirstFollow {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Simbolo no terminal
     */
    public final String NO_TERMINAL;
    /**
     * Conjunto de simbolos terminales, no terminales y/o vacios.
     */
    public final LinkedList<NuT> CONJUNTO_SIMBOLOS;

    /**
     * Clase que recibe el conjunto de first o follows de la gramatica de forma dinamica.
     *
     * @param noTerminal        Simbolo No terminal que contiene al conjunto.
     * @param simboloNuT        Simbolo terminal o no terminal de preferencia que sera el primer elemento del conjunto First o Follow.
     * @param numero_produccion Numero de produccion de la gramatica que pertenece al simbolo de First o Follow.
     * @implNote Este conjunto se debe rellenar una vez instanciado un First o Follow.
     */
    public FirstFollow(String noTerminal, String simboloNuT, int numero_produccion) {
        this.NO_TERMINAL = noTerminal;
        CONJUNTO_SIMBOLOS = new LinkedList<>();

        // ◂ ◂ ◂ ◂ Agregamos el primer elemento first o follow de la produccion con su identificacion ▸ ▸ ▸ ▸ //
        agregarSimbolo(new NuT(numero_produccion, simboloNuT));
    }


    /**
     * Funcion que agrega al conjunto de simbolos terminales o no terminales al First o Follow del no terminal sin duplicar.
     *
     * @param simboloNuT Simbolo no terminal o terminal de preferencia.
     */
    public void agregarSimbolo(NuT simboloNuT) {

        if (!hayDuplicados(simboloNuT.SIMBOLO)) {
            CONJUNTO_SIMBOLOS.add(simboloNuT);
        }
    }

    @Override
    public String toString() {
        String resultado = "(" + NO_TERMINAL + "):";

        for (NuT simbolosNuT : CONJUNTO_SIMBOLOS) {
            resultado += " [ " + simbolosNuT + " ]";
        }

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ INTEGRIDAD DE UN CONJUNTO ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que verifica si hay duplicados en nuestra lista enlazada dependiendo del simbolo NuT.
     *
     * @param simboloNuT Simbolo No terminal o Terminal (NuT)
     * @return ¿Ya existe en el conjunto First o Follow del no terminal?.
     */
    private boolean hayDuplicados(String simboloNuT) {

        if (CONJUNTO_SIMBOLOS.isEmpty()) {
            return false;
        }

        // ◂ ◂ ◂ ◂ Si existe en el conjunto de First o Follow ▸ ▸ ▸ ▸ //
        for (NuT nut : CONJUNTO_SIMBOLOS) {
            if (nut.SIMBOLO.equals(simboloNuT)) {
                return true;
            }
        }

        return false;
    }

}

