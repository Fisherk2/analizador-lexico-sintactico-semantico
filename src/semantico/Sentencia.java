package semantico;

import lexico.Token;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Clase que almacena la notacion de una sentencia o instruccion generada por la gramatica.
 */
public class Sentencia {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ ATRIBUTOS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    /**
     * Tipo de sentencia de la gramatica dependiendo de su clasificacion en el JSON de notaciones.
     */
    public final int TYPE_STATEMENT;
    /**
     * Expresion infija de la sentencia
     */
    public final Token[] INFIJA;
    /**
     * Expresion prefija de la sentencia.
     */
    public final Token[] PREFIJA;
    /**
     * Expresion posfija de la sentencia.
     */
    public final Token[] POSFIJA;

    /**
     * Clase que almacena la notacion de una sentencia o instruccion generada por la gramatica.
     *
     * @param tipo_sentencia Tipo de sentencia de la gramatica dependiendo de su clasificacion en el JSON de notaciones.
     * @param infija         Expresion Infija de la sentencia.
     * @param prefija        Expresion prefija de la sentencia.
     * @param posfija        Expresion posfija de la sentencia.
     * @implNote Si solo quieres utilizar dos tipos de expresion, puedes dejar en NULL tanto la prefija o la posfija.
     */
    public Sentencia(int tipo_sentencia, Token[] infija, Token[] prefija, Token[] posfija) {
        this.TYPE_STATEMENT = tipo_sentencia;
        this.INFIJA = infija;
        this.PREFIJA = prefija;
        this.POSFIJA = posfija;
    }

    @Override
    public String toString() {
        String resultado = "Tipo: [" + TYPE_STATEMENT + "] INFIJA: ";

        for (Token token : INFIJA) {
            resultado += token.LEXEMA + " ";
        }

        if (PREFIJA != null) {
            resultado += "- PREFIJA: ";

            for (Token token : PREFIJA) {
                resultado += token.LEXEMA + " ";
            }
        }

        if (POSFIJA != null) {
            resultado += "- POSFIJA: ";

            for (Token token : POSFIJA) {
                resultado += token.LEXEMA + " ";
            }
        }

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que devuelve el token de instruccion de la sentencia, ya sea prefija o posfija.
     *
     * @param es_posfija ¿La sentencia es posfija?
     * @return Token de instruccion de la sentencia.
     */
    public Token getInstruccion(boolean es_posfija) {
        if (es_posfija) {
            // ◂ ◂ ◂ ◂ El ultimo elemento del arreglo ▸ ▸ ▸ ▸ //
            return POSFIJA[POSFIJA.length - 1];
        }

        // ◂ ◂ ◂ ◂ El primer elemento del arreglo ▸ ▸ ▸ ▸ //
        return PREFIJA[0];
    }

    /**
     * Funcion que devuelve los tokens que no sean la instruccion de la sentencia.
     *
     * @param es_posfija ¿La sentencia es posfija?
     * @return Tokens de la sentencia que no sean la instruccion de la sentencia.
     */
    public LinkedList<Token> getOperandos(boolean es_posfija) {
        LinkedList<Token> operandos = new LinkedList<>();

        if (es_posfija) {

            // ◂ ◂ ◂ ◂ No se agrega el ultimo elemento ▸ ▸ ▸ ▸ //
            for (int i = 0; i < POSFIJA.length; i++) {

                if (i >= POSFIJA.length - 1) {
                    return operandos;
                }
                operandos.add(POSFIJA[i]);
            }
            //operandos.addAll(Arrays.asList(POSFIJA).subList(0, POSFIJA.length-1));
        }

        // ◂ ◂ ◂ ◂ No se agrega el primer elemento ▸ ▸ ▸ ▸ //
        operandos.addAll(Arrays.asList(POSFIJA).subList(1, POSFIJA.length));

        return operandos;
    }
}
