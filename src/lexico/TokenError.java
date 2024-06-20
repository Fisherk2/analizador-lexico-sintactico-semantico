package lexico;

public class TokenError extends Token {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Linea de codigo donde se encuentra el error lexico del lexema.
     */
    public final int LINEA_DE_CODIGO;

    /**
     * Clase que almacena las propiedades de un Token analizado erroneo.
     *
     * @param lexema        Nombre propio del elemento analizado.
     * @param lineaDeCodigo Numero de linea de codigo donde se ubica el error lexico del lexema.
     * @implNote Estos tokens SIEMPRE tendran un atributo de -1.
     */
    public TokenError(String lexema, int lineaDeCodigo) {
        super(lexema, -1);
        this.LINEA_DE_CODIGO = lineaDeCodigo;
    }

    @Override
    public String toString() {
        return "[" + LEXEMA + "][" + ATRIBUTO + "] - Error en la linea: " + LINEA_DE_CODIGO;
    }
}
