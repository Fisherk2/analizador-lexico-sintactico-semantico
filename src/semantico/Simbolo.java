package semantico;

import lexico.Token;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Clase que almacena las propiedades de un simbolo para generacion de codigo intermedio.
 */
public class Simbolo {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private final String LEXEMA;

    private final int ATRIBUTO;

    private String tipoDeDato;

    private String valor;

    private int iteraciones;

    private final LinkedList<Integer> LINEAS;


    /**
     * Clase que almacena las propiedades de un simbolo para generacion de codigo intermedio.
     *
     * @param token_lexico Token identificador o constante que proveniente del analizador lexico.
     */
    public Simbolo(Token token_lexico) {

        LINEAS = new LinkedList<>();

        LEXEMA = token_lexico.LEXEMA;
        ATRIBUTO = token_lexico.ATRIBUTO;
        tipoDeDato = "";
        valor = "";
        iteraciones = 1;
        LINEAS.add(token_lexico.LINEA_DE_CODIGO);

    }

    /**
     * Clase que almacena las propiedades de un simbolo para generacion de codigo intermedio.
     *
     * @param token_lexico Token identificador o constante que proveniente del analizador lexico.
     * @param valor        Valor que almacena el simbolo.
     */
    public Simbolo(Token token_lexico, String valor) {

        LINEAS = new LinkedList<>();

        LEXEMA = token_lexico.LEXEMA;
        ATRIBUTO = token_lexico.ATRIBUTO;
        tipoDeDato = token_lexico.CLASIFICACION_LEXICA;
        this.valor = valor;
        iteraciones = 1;
        LINEAS.add(token_lexico.LINEA_DE_CODIGO);

    }

    @Override
    public String toString() {
        String resultado = "[" + LEXEMA + "][" + ATRIBUTO + "][" + tipoDeDato + "][" + valor + "] Numeros de linea [" + iteraciones + "] ->";

        for (int linea : LINEAS) {
            resultado += " {" + linea + "}";
        }

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ EQUALS POR LEXEMA ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simbolo simbolo = (Simbolo) o;
        return Objects.equals(LEXEMA, simbolo.LEXEMA);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(LEXEMA);
    }


    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS & SETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Metodo que identifica que tipo de dato fue declarado el simbolo en concreto.
     */
    public void setTipoDeDato(String tipo_de_dato) {
        //TODO: SI ES UN IDENTIFICADOR, CHECAR QUE TIPO DE DATO FUE DECLARADO DICHO IDENTIFICADOR.
        this.tipoDeDato = tipo_de_dato;
    }

    /**
     * Metodo que actualiza el valor que almacena el simbolo.
     */
    public void setValor(String valor) {
        //TODO: SI ES UNA CONSTANTE, ASIGNARLO POR DEFAULT, si no, actualizarlo cada que se evalua en el arbol de expresiones
        this.valor = valor;
    }

    /**
     * Metodo que actualiza el numero de lineas del simbolo.
     *
     * @param num_linea Numero de linea en donde se ubica el simbolo en el codigo fuente.
     */
    public void setLineas(int num_linea) {
        iteraciones++;
        LINEAS.add(num_linea);
    }

    public String getLEXEMA() {
        return LEXEMA;
    }

    public int getATRIBUTO() {
        return ATRIBUTO;
    }

    public String getTipoDeDato() {
        return tipoDeDato;
    }

    public String getValor() {
        return valor;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public LinkedList<Integer> getLINEAS() {
        return LINEAS;
    }
}
