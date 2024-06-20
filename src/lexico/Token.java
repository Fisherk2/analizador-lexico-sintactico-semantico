package lexico;

import java.util.Objects;

public class Token {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Nombre propio del token.
     */
    public final String LEXEMA;

    /**
     * Propiedad numerica que pertenece de la tabla de clasificacion lexica.
     */
    public final int ATRIBUTO;

    /**
     * Clase que almacena las propiedades de un Token analizado.
     *
     * @param lexema   Nombre propio del elemento analizado.
     * @param atributo Propiedad numerica que pertenece de la tabla de clasificacion lexica.
     */
    public Token(String lexema, int atributo) {
        this.LEXEMA = lexema;
        this.ATRIBUTO = atributo;
    }

    @Override
    public String toString() {
        return "[" + LEXEMA + "][" + ATRIBUTO + "]";
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
