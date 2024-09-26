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

    private Token[] prefija;

    private Token[] posfija;

    /**
     * Clase que almacena la notacion de una sentencia o instruccion generada por la gramatica.
     *
     * @param infija Expresion Infija de la sentencia.
     */
    public Sentencia(Token[] infija) {
        this.INFIJA = infija;
        prefija = null;
        posfija = null;
    }

    @Override
    public String toString() {
        String resultado = "INFIJA: ";

        for (Token token : INFIJA) {
            resultado += token.LEXEMA + " ";
        }
/*
        resultado += "- PREFIJA: ";

        for (Token token : PREFIJA) {
            resultado += token.LEXEMA + " ";
        }

        resultado = "- POSFIJA: ";

        for (Token token : POSFIJA) {
            resultado += token.LEXEMA + " ";
        }

 */

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS & SETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    public Token[] getPrefija() {
        return prefija;
    }

    public void setPrefija(Token[] prefija) {
        this.prefija = prefija;
    }

    public Token[] getPosfija() {
        return posfija;
    }

    public void setPosfija(Token[] posfija) {
        this.posfija = posfija;
    }
}
