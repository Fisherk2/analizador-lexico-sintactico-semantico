package semantico;

import lexico.Token;

/**
 * Clase que almacena la notacion de una sentencia o instruccion generada por la gramatica.
 */
public class Sentencia {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ ATRIBUTOS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    /**
     * Expresion infija de la sentencia
     */
    public final Token[] INFIJA;

    /**
     * Expresion prefija de la sentencia
     */
    public final Token[] PREFIJA;

    /**
     * Expresion posfija de la sentencia.
     */
    public final Token[] POSFIJA;

    /**
     * Clase que almacena la notacion de una sentencia o instruccion generada por la gramatica.
     *
     * @param infija Expresion Infija de la sentencia.
     */
    public Sentencia(Token[] infija) {
        this.INFIJA = infija;
        this.PREFIJA = convertirPrefija(infija);
        this.POSFIJA = convertirPosfija(infija);
    }

    @Override
    public String toString() {
        String resultado = "INFIJA: ";

        for (Token token : INFIJA) {
            resultado += token.LEXEMA + " ";
        }

        resultado += "- PREFIJA: ";

        for (Token token : PREFIJA) {
            resultado += token.LEXEMA + " ";
        }

        resultado = "- POSFIJA: ";

        for (Token token : POSFIJA) {
            resultado += token.LEXEMA + " ";
        }

        return resultado;
    }

    /**
     * Funcion que convierte cualquier expresion infija a prefija.
     *
     * @param expresion_infija Lista o arreglo de tokens que conforma la expresion infija
     * @return Arreglo de tokens ordenado de forma prefija.
     */
    private Token[] convertirPrefija(Token[] expresion_infija) {
        //TODO: GENERAR ALGORITMO DE CONVERSION PREFIJA
        return null;
    }

    /**
     * Funcion que convierte cualquier expresion infija a posfija.
     *
     * @param expresion_infija Lista o arreglo de tokens que conforma la expresion infija
     * @return Arreglo de tokens ordenado de forma posfija.
     */
    private Token[] convertirPosfija(Token[] expresion_infija) {
        //TODO: GENERAR ALGORITMO DE CONVERSION POSFIJA
        return null;
    }

}
