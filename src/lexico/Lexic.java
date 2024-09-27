package lexico;

import lenguaje.Automata;
import lenguaje.Clasificacion;

import java.util.LinkedList;

/**
 * Clase que almacena las propiedades de un analizador lexico.
 */
public class Lexic {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    public final Automata AFN;
    private final Clasificacion CLASIFICACION_LEXICA;
    private final LinkedList<Token> TABLA_DE_TOKENS;
    private int numIdentificadores;

    /**
     * Clase que almacena las propiedades de un analizador lexico dependiendo de las palabras que acepta el lenguaje.
     *
     * @param automata             Automata finito no determinista.
     * @param clasificacion_lexica Tabla de clasificacion lexica que contenga descripcion de clasificacion, regex y atributo.
     */
    public Lexic(Automata automata, Clasificacion clasificacion_lexica) {

        TABLA_DE_TOKENS = new LinkedList<>();
        AFN = automata;
        CLASIFICACION_LEXICA = clasificacion_lexica;

        numIdentificadores = -1;

    }

    @Override
    public String toString() {

        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ LEXICO ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

        resultado += CLASIFICACION_LEXICA;

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE PALABRAS RESERVADAS ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Clasificacion.Categoria categoria : CLASIFICACION_LEXICA.WORD_RESERVES) {
            resultado += "[" + categoria.REGEX + "][" + categoria.ATTRIBUTE + "]" + "\n";
        }

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE TOKENS COMPLETA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Token token : TABLA_DE_TOKENS) {
            resultado += token + "\n";
        }

        return resultado;
    }


    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS & SETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Metodo que almacena tokens en su respectiva tabla de tokens.
     *
     * @param token Token generado con atributo y lexema.
     */
    public void almacenarToken(Token token) {
        TABLA_DE_TOKENS.add(token);
    }


    /**
     * Funcion que genera un token de los lexemas que son aceptados o rechazados por el AFN.
     *
     * @param lexema     Nombre propio, cadena, palabra.
     * @param numDeLinea Numero de linea de codigo donde se ubica el lexema.
     */
    public Token crearToken(String lexema, int numDeLinea) {

        // ◂ ◂ ◂ ◂ Obtenemos la clasificacion lexica que pertenece a ese lexema ▸ ▸ ▸ ▸ //
        Clasificacion.Categoria lexemaClasificado = CLASIFICACION_LEXICA.clasificarLexema(lexema);

        // ◂ ◂ ◂ ◂ Asignar numero de atributo en cada identificador diferente comenzando desde su atributo base ▸ ▸ ▸ ▸ //
        if (esIdentificador(lexema)) {
            numIdentificadores++;
            return new Token(lexema, lexemaClasificado.NAME, lexemaClasificado.ATTRIBUTE + numIdentificadores, numDeLinea);
        }

        return new Token(lexema, lexemaClasificado.NAME, lexemaClasificado.ATTRIBUTE, numDeLinea);
    }


    /**
     * Metodo que devuelve la tabla de tokens del analizador lexico
     *
     * @return Tabla de tokens actual.
     */
    public LinkedList<Token> getTABLA_DE_TOKENS() {
        return TABLA_DE_TOKENS;
    }

    /**
     * Funcion que devuelve el nivel jerarquico o precedencia de operadores.
     *
     * @param token Nombre propio, caracter o cadena de texto.
     * @return Precedencia de operaciones.
     * @implNote Asignacion, comparacion y booleanos = 0
     */
    public int getJerarquia(Token token) {
        return CLASIFICACION_LEXICA.OPERATORS.precedencia(token.LEXEMA);
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VALIDACIONES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que verifica si el caracter ingresado forma parte del alfabeto de caracteres simples del lenguaje.
     *
     * @param caracter Caracter a evaluar.
     * @return ¿Forma parte del alfabeto de caracteres simples del lenguaje?.
     */
    public boolean esCaracterEspecial(char caracter) {
        return CLASIFICACION_LEXICA.esOperador(String.valueOf(caracter)) || CLASIFICACION_LEXICA.esCaracterEspecial(String.valueOf(caracter));
    }

    /**
     * Funcion que verifica si la cadena de caracteres especiales forma parte del alfabeto de caracteres compuestos del lenguaje.
     *
     * @param caracterEspecialCompuesto Lexema con caracteres especiales compuestos.
     * @return ¿Forma parte del alfabeto de caracteres especiales del lenguaje?
     */
    public boolean esCaracterEspecial(String caracterEspecialCompuesto) {
        return CLASIFICACION_LEXICA.esOperador(caracterEspecialCompuesto) || CLASIFICACION_LEXICA.esCaracterEspecial(caracterEspecialCompuesto);
    }

    /**
     * Funcion que evalua si el caracter ingresado es un caracter blanco (Espacio, saltos de linea, retorno de carro, tabuladores...).
     *
     * @param caracter Caracter a revisar.
     * @return ¿Es un caracter blanco?
     */
    public boolean esBlanco(char caracter) {
        return caracter == ' ' || caracter == '\n' || caracter == '\t' || caracter == '\r';
    }

    /**
     * Funcion que evalua si el lexema pertenece a la clasificacion lexica de los identificadores.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es un identificador?
     */
    public boolean esIdentificador(String lexema) {
        return CLASIFICACION_LEXICA.esIdentificador(lexema);
    }

    /**
     * Funcion que evalua si el lexema pertenece a la clasificacion lexica de las palabras reservadas.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es una palabra reservada?
     */
    public boolean esPalabraReservada(String lexema) {
        return CLASIFICACION_LEXICA.esPalabraReservada(lexema);
    }

    /**
     * Funcion que evalua si el lexema pertenece a una constante numerica o una cadena.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es una constante?
     */
    public boolean esConstante(String lexema) {
        return CLASIFICACION_LEXICA.esNumeroEntero(lexema) || CLASIFICACION_LEXICA.esNumeroFlotante(lexema) || CLASIFICACION_LEXICA.esCadenaTexto(lexema);
    }

    /**
     * Funcion que evalua si el lexema pertenece a un caracter de apertura operacional.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es un signo de apertura?
     */
    public boolean esCaracterApertura(String lexema) {
        return CLASIFICACION_LEXICA.OPERATORS.esSignoApertura(lexema);
    }

    /**
     * Funcion que evalua si el lexema pertenece a un caracter de cerradura operacional.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es un signo de cierre?
     */
    public boolean esCaracterCierre(String lexema) {
        return CLASIFICACION_LEXICA.OPERATORS.esSignoCierre(lexema);
    }

    /**
     * Funcion que evalua si el lexema pertenece a un operador.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es un operador?
     */
    public boolean esOperador(String lexema) {
        return CLASIFICACION_LEXICA.OPERATORS.esSignoAritmetico(lexema)
                || CLASIFICACION_LEXICA.OPERATORS.esSignoAsignacion(lexema)
                || CLASIFICACION_LEXICA.OPERATORS.esSignoComparacion(lexema)
                || CLASIFICACION_LEXICA.OPERATORS.esSignoLogico(lexema);
    }

}

