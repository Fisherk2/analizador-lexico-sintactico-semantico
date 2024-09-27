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

    public final Token[] PREFIJA;

    public final Token[] POSFIJA;

    /**
     * Clase que almacena la notacion de una sentencia o instruccion generada por la gramatica.
     *
     * @param infija  Expresion Infija de la sentencia.
     * @param prefija Expresion prefija de la sentencia.
     * @param posfija Expresion posfija de la sentencia.
     * @implNote Si solo quieres utilizar dos tipos de expresion, puedes dejar en NULL tanto la prefija o la posfija.
     */
    public Sentencia(Token[] infija, Token[] prefija, Token[] posfija) {
        this.INFIJA = infija;
        this.PREFIJA = prefija;
        this.POSFIJA = posfija;
    }

    @Override
    public String toString() {
        String resultado = "INFIJA: ";

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
}
