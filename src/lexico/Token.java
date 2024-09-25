package lexico;

import java.util.Objects;

public class Token {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Nombre propio del token.
     */
    public final String LEXEMA;

    /**
     * Clasificacion lexica donde pertenece ese token.
     */
    public final String CLASIFICACION_LEXICA;

    /**
     * Propiedad numerica que pertenece de la tabla de clasificacion lexica.
     */
    public final int ATRIBUTO;

    /**
     * Linea de codigo donde se encuentra el lexema analizado.
     */
    public final int LINEA_DE_CODIGO;

    /**
     * Clase que almacena las propiedades de un Token analizado.
     *
     * @param lexema               Nombre propio del elemento analizado.
     * @param clasificacion_lexica Titulo de la clasificacion lexica que pertenece al elemento analizado.
     * @param atributo             Propiedad numerica que pertenece de la tabla de clasificacion lexica.
     * @param linea_de_codigo      Numero de linea donde se encuentra el lexema analizado.
     */
    public Token(String lexema, String clasificacion_lexica, int atributo, int linea_de_codigo) {
        this.LEXEMA = lexema;
        this.CLASIFICACION_LEXICA = clasificacion_lexica;
        this.ATRIBUTO = atributo;
        this.LINEA_DE_CODIGO = linea_de_codigo;
    }

    /**
     * Clase que almacena las propiedades de un Token analizado.
     *
     * @param lexema Nombre propio del elemento analizado.
     */
    public Token(String lexema) {
        this.LEXEMA = lexema;
        this.CLASIFICACION_LEXICA = "SIN CLASIFICAR";
        this.ATRIBUTO = 0;
        this.LINEA_DE_CODIGO = 0;
    }

    @Override
    public String toString() {
        return "[" + LEXEMA + "][" + CLASIFICACION_LEXICA + "][" + ATRIBUTO + "] - [" + LINEA_DE_CODIGO + "]";
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ EQUALS POR LEXEMA ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(LEXEMA, token.LEXEMA);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(LEXEMA);
    }
}
