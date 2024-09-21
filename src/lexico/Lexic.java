package lexico;

import lenguaje.Automata;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.regex.PatternSyntaxException;

/**
 * Clase que almacena las propiedades de un analizador lexico.
 */
public class Lexic {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    private final Automata AFN;
    private final Clasificacion[] TABLA_CLASIFICACION_LEXICA;
    private final LinkedList<Token> TABLA_DE_TOKENS;
    private final LinkedHashSet<Clasificacion> TABLA_PALABRAS_RESERVADAS;
    private final LinkedHashSet<String> PRIORIDAD_CLASIFICACION;
    private int numIdentificadores;

    /**
     * Clase que almacena las propiedades de un analizador lexico dependiendo de las palabras que acepta el lenguaje.
     *
     * @param automata                   Automata finito no determinista.
     * @param tabla_clasificacion_lexica Tabla de clasificacion lexica que contenga descripcion de clasificacion, regex y atributo.
     * @implNote La tabla de clasificacion lexica DEBE ESTAR ORDENADO DEPENDIENDO DE LA PRIORIDAD DE CLASIFICACION LEXICA,
     * por lo tanto, las PRIMERAS clasificaciones deben ser palabras reservadas, y AL FINAL de la tabla,
     * siempre debe ir los identificadores, el orden de las demas clasificaciones dependera mucho
     * del diseño del analizador lexico.
     */
    public Lexic(Automata automata, Clasificacion[] tabla_clasificacion_lexica) {

        TABLA_PALABRAS_RESERVADAS = new LinkedHashSet<>();
        TABLA_DE_TOKENS = new LinkedList<>();
        PRIORIDAD_CLASIFICACION = new LinkedHashSet<>();
        AFN = automata;
        TABLA_CLASIFICACION_LEXICA = tabla_clasificacion_lexica;

        numIdentificadores = -1;

        // ◂ ◂ ◂ ◂ Almacenamos clasificaciones en orden de prioridad sin duplicados ▸ ▸ ▸ ▸ //
        for (Clasificacion clasificacion : TABLA_CLASIFICACION_LEXICA) {
            PRIORIDAD_CLASIFICACION.add(clasificacion.clasificacion);
        }

        // ◂ ◂ ◂ ◂ Almacenamos las palabras reservadas ▸ ▸ ▸ ▸ //
        for (Clasificacion clasificacion : TABLA_CLASIFICACION_LEXICA) {

            if (esPalabraReservada(clasificacion.regex)) {
                TABLA_PALABRAS_RESERVADAS.add(clasificacion);
            }
        }
    }

    @Override
    public String toString() {

        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ LEXICO ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE CLASIFICACION LEXICA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Clasificacion clasificacion : TABLA_CLASIFICACION_LEXICA) {
            resultado += clasificacion + "\n";
        }

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE PALABRAS RESERVADAS ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Clasificacion clasificacion : TABLA_PALABRAS_RESERVADAS) {
            resultado += "[" + clasificacion.regex + "][" + clasificacion.atributo + "]" + "\n";
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
     * Funcion que sirve para saber que tipo de clasificacion lexica pertenece al lexema procesado.
     *
     * @param lexema Nombre propio, cadena, palabra que NO ESTE VACIA.
     * @return Clasificacion lexica que pertenece a dicho lexema.
     */
    public Clasificacion getClasificacionLexica(String lexema) {
        for (Clasificacion clasificacion : TABLA_CLASIFICACION_LEXICA) {
            try {

                // ◂ ◂ Si alguna de las expresiones regulares que se encuentren en las clasificaciones coincide ▸ ▸ //
                if (lexema.matches(clasificacion.regex)) {
                    return clasificacion;
                }

            } catch (PatternSyntaxException ex) {
                System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + clasificacion.regex + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
            }

        }

        return new Clasificacion("LEXER ERROR", "", -1);
    }

    /**
     * Funcion que genera un token de los lexemas que son aceptados o rechazados por el AFN.
     *
     * @param lexema     Nombre propio, cadena, palabra.
     * @param numDeLinea Numero de linea de codigo donde se ubica el lexema.
     */
    public Token crearToken(String lexema, int numDeLinea) {

        // ◂ ◂ ◂ ◂ Si el lexema no es aceptado, se generara un token de error donde se almacena el numero de linea ▸ ▸ ▸ ▸ //
        if (!AFN.esAceptada(lexema)) {
            return new TokenError(lexema, numDeLinea);
        }

        // ◂ ◂ ◂ ◂ Obtenemos la clasificacion lexica que pertenece a ese lexema ▸ ▸ ▸ ▸ //
        Clasificacion lexemaClasificado = getClasificacionLexica(lexema);

        // ◂ ◂ ◂ ◂ Asignar numero de atributo en cada identificador diferente comenzando desde su atributo base ▸ ▸ ▸ ▸ //
        if (esIdentificador(lexema)) {
            numIdentificadores++;
            return new Token(lexema, lexemaClasificado.clasificacion, lexemaClasificado.atributo + numIdentificadores, numDeLinea);
        }

        return new Token(lexema, lexemaClasificado.clasificacion, lexemaClasificado.atributo, numDeLinea);
    }

    /**
     * Funcion que devuelve el titulo de clasificacion lexica dependiendo de su prioridad de analisis lexico.
     *
     * @param nivel_de_prioridad Nivel de prioridad donde el 0 es el mas importante.
     * @return Titulo de la clasificacion lexica perteneciente a ese nivel de prioridad.
     * @implNote 0 = Palabras reservadas,
     * n-1 ó n < 0 = Identificadores.
     */
    public String getPrioridadLexica(int nivel_de_prioridad) {

        if (nivel_de_prioridad >= PRIORIDAD_CLASIFICACION.size() || nivel_de_prioridad < 0) {
            return new LinkedList<>(PRIORIDAD_CLASIFICACION).getLast();
        }

        return new LinkedList<>(PRIORIDAD_CLASIFICACION).get(nivel_de_prioridad);
    }

    /**
     * Metodo que devuelve la tabla de tokens del analizador lexico
     *
     * @return Tabla de tokens actual.
     */
    public LinkedList<Token> getTABLA_DE_TOKENS() {
        return TABLA_DE_TOKENS;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VALIDACIONES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que verifica si el caracter ingresado forma parte del alfabeto de caracteres simples del lenguaje.
     *
     * @param caracter Caracter a evaluar.
     * @return ¿Forma parte del alfabeto de caracteres simples del lenguaje?.
     * @implNote Incluye otros caracteres que consideres especiales que comiencen desde el atributo 256 hasta el 299.
     */
    public boolean esCaracterEspecial(char caracter) {

        // ◂ ◂ ◂ ◂ Los caracteres simples tendran un rango entre 0 a 255, y los especiales adicionales del 256 al 299 ▸ ▸ ▸ ▸ //
        return getClasificacionLexica(String.valueOf(caracter)).atributo >= 0 && getClasificacionLexica(String.valueOf(caracter)).atributo < 300;
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

        // ◂ ◂ ◂ ◂ Los identificadores SIEMPRE estaran al fondo de la tabla de clasificacion lexica ▸ ▸ ▸ ▸ //
        return getClasificacionLexica(lexema).clasificacion.equals(getPrioridadLexica(-1));
    }

    /**
     * Funcion que evalua si el lexema pertenece a la clasificacion lexica de las palabras reservadas.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es una palabra reservada?
     */
    public boolean esPalabraReservada(String lexema) {

        // ◂ ◂ ◂ ◂ Las palabras reservadas SIEMPRE seran los primeros elementos de la tabla de clasificacion lexica ▸ ▸ ▸ ▸ //
        return getClasificacionLexica(lexema).clasificacion.equals(getPrioridadLexica(0));
    }

    /**
     * Funcion que evalua si el lexema pertenece a una constante numerica.
     *
     * @param lexema Nombre propio del token.
     * @return ¿Es una constante numerica?
     */
    public boolean esConstanteNumerica(String lexema) {

        // ◂ ◂ ◂ ◂ En caso de que el lenguaje acepte coma en vez de punto en cualquier numero decimal ▸ ▸ ▸ ▸ //
        lexema = lexema.replace(",", ".");

        try {

            // ◂ ◂ ◂ ◂ En caso de que se manejen numeros enormes ▸ ▸ ▸ ▸ //
            new BigDecimal(lexema);

            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }
}

