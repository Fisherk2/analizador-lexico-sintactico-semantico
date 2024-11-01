package intermedio;

import lexico.Token;
import semantico.Simbolo;

/**
 * Clase que almacena las propiedades de un Cuarteto del codigo intermedio.
 */
public class Cuarteto {
    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    public final Token OPERADOR;
    public final Simbolo OPERANDO_A;
    public final Simbolo OPERANDO_B;
    public final Simbolo RESULTADO;

    /**
     * Clase que almacena las propiedades de un Cuarteto del codigo intermedio.
     *
     * @param OPERADOR   Operador o instruccion.
     * @param OPERANDO_A Primer operando.
     * @param OPERANDO_B Segundo operando.
     * @param RESULTADO  Resultado de la operacion.
     */
    public Cuarteto(Token OPERADOR, Simbolo OPERANDO_A, Simbolo OPERANDO_B, Simbolo RESULTADO) {
        this.OPERADOR = OPERADOR;
        this.OPERANDO_A = OPERANDO_A;
        this.OPERANDO_B = OPERANDO_B;
        this.RESULTADO = RESULTADO;
    }

    @Override
    public String toString() {
        return "[" + OPERADOR.LEXEMA + "]["
                + OPERANDO_A.getLEXEMA() + "]["
                + OPERANDO_B.getLEXEMA() + "]["
                + RESULTADO.getLEXEMA() + "]";
    }
}
