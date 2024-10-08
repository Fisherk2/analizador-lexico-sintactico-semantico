package lenguaje;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

/**
 * Clase que almacena la tabla de clasificacion lexica del lenguaje.
 */
public class Clasificacion {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ ATRIBUTOS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Palabras reservadas que pertenezcan al lenguaje.
     */
    public final Categoria[] WORD_RESERVES;
    /**
     * Numeros enteros que pertenezcan al lenguaje.
     */
    public final Categoria INTEGER_NUMBERS;
    /**
     * Numeros flotantes que pertenezcan al lenguaje.
     */
    public final Categoria FLOAT_NUMBERS;
    /**
     * Cadenas de texto que pertenezcan al lenguaje.
     */
    public final Categoria STRINGS;
    /**
     * Identificadores que pertenezcan al lenguaje.
     */
    public final Categoria IDENTIFIERS;
    /**
     * Signos operacionales que pertenezcan al lenguaje.
     */
    public final Operadores OPERATORS;
    /**
     * Caracter de apertura que pertenecen al lenguaje.
     */
    public final Categoria OPENER_SIGN;
    /**
     * Caracter de cerradura que pertenecen al lenguaje.
     */
    public final Categoria CLOSER_SIGN;
    /**
     * Caracteres especiales que pertenezcan al lenguaje.
     */
    public final Categoria[] SPECIAL_CHARS;

    /**
     * Clase que almacena la tabla de clasificacion lexica del lenguaje.
     *
     * @param palabras_reservadas Palabras reservadas que pertenezcan al lenguaje.
     * @param numero_entero       Numeros enteros que pertenezcan al lenguaje.
     * @param numero_flotante     Numeros flotantes que pertenezcan al lenguaje.
     * @param cadena              Cadenas de texto que pertenezcan al lenguaje.
     * @param identificador       Identificadores que pertenezcan al lenguaje.
     * @param signos_operadores   Signos operacionales que pertenezcan al lenguaje.
     * @param signo_apertura      Caracter o signo de apertura que pertenezcan al lenguaje.
     * @param signo_cerradura     Caracter o signo de cerradura que pertenezcan al lenguaje.
     * @param chars_especiales    Caracteres especiales que pertenezcan al lenguaje.
     * @implNote Si alguna clasificacion no pertenece al lenguaje, debes dejarlo VACIO = NULL.
     */
    public Clasificacion(
            Categoria[] palabras_reservadas,
            Categoria numero_entero,
            Categoria numero_flotante,
            Categoria cadena,
            Categoria identificador,
            Operadores signos_operadores,
            Categoria signo_apertura,
            Categoria signo_cerradura,
            Categoria[] chars_especiales
    ) {
        this.WORD_RESERVES = palabras_reservadas;
        this.INTEGER_NUMBERS = numero_entero;
        this.FLOAT_NUMBERS = numero_flotante;
        this.STRINGS = cadena;
        this.IDENTIFIERS = identificador;
        this.OPERATORS = signos_operadores;
        this.SPECIAL_CHARS = chars_especiales;
        this.OPENER_SIGN = signo_apertura;
        this.CLOSER_SIGN = signo_cerradura;
    }

    @Override
    public String toString() {
        String resultado = "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE CLASIFICACION LEXICA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (Categoria categoria : getAllCategorias()) {
            resultado += "\n" + categoria;
        }

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Metodo que devuelve por completo la tabla de clasificacion lexica.
     *
     * @return Lista enlazada irrepetible con todas las clasificaciones lexicas del lenguaje
     */
    private LinkedHashSet<Categoria> getAllCategorias() {
        LinkedHashSet<Categoria> allElements = new LinkedHashSet<>();

        for (Categoria palabra_reservada : WORD_RESERVES) {
            if (palabra_reservada != null) {
                allElements.add(palabra_reservada);
            }
        }

        if (INTEGER_NUMBERS.esValido()) {
            allElements.add(INTEGER_NUMBERS);
        }

        if (FLOAT_NUMBERS.esValido()) {
            allElements.add(FLOAT_NUMBERS);
        }

        if (STRINGS.esValido()) {
            allElements.add(STRINGS);
        }

        if (IDENTIFIERS.esValido()) {
            allElements.add(IDENTIFIERS);
        }

        allElements.addAll(OPERATORS.getAllSignos());

        if (OPENER_SIGN.esValido()) {
            allElements.add(OPENER_SIGN);
        }

        if (CLOSER_SIGN.esValido()) {
            allElements.add(CLOSER_SIGN);
        }

        for (Categoria caracter_especial : SPECIAL_CHARS) {
            if (caracter_especial.esValido()) {
                allElements.add(caracter_especial);
            }
        }

        return allElements;
    }

    /**
     * Funcion que sirve para saber que tipo de clasificacion lexica pertenece al lexema procesado.
     *
     * @param lexema Nombre propio, cadena, palabra que NO ESTE VACIA.
     * @return Clasificacion lexica que pertenece a dicho lexema.
     */
    public Categoria clasificarLexema(String lexema) {
        for (Categoria categoria : getAllCategorias()) {
            try {

                // ◂ ◂ Si alguna de las expresiones regulares que se encuentren en las clasificaciones coincide ▸ ▸ //
                if (lexema.matches(categoria.REGEX)) {
                    return categoria;
                }

            } catch (PatternSyntaxException ex) {
                System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + categoria.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
            }

        }

        return new Categoria("LEXER ERROR", "", -1);
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VALIDACION ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que verifica si el lexema es una palabra reservada.
     *
     * @param lexema Nombre propio, cadena o caracter
     * @return ¿Es palabra reservada?
     */
    public boolean esPalabraReservada(String lexema) {
        for (Categoria palabra_reservada : WORD_RESERVES) {
            try {

                // ◂ ◂ Si alguna de las palabras reservadas que se encuentren en las clasificaciones coincide ▸ ▸ //
                if (lexema.matches(palabra_reservada.REGEX)) {
                    return true;
                }

            } catch (PatternSyntaxException ex) {
                System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + palabra_reservada.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
            } catch (NullPointerException error) {
                return false;
            }
        }
        return false;
    }

    /**
     * Funcion que verifica si el lexema es un identificador.
     *
     * @param lexema Nombre propio, cadena o caracter
     * @return ¿Es un identificador?
     */
    public boolean esIdentificador(String lexema) {
        try {

            if (lexema.matches(IDENTIFIERS.REGEX)) {
                return true;
            }

        } catch (PatternSyntaxException ex) {
            System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + IDENTIFIERS.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
        } catch (NullPointerException error) {
            return false;
        }

        return false;
    }

    /**
     * Funcion que verifica si el lexema es un numero entero.
     *
     * @param lexema Nombre propio, cadena o caracter
     * @return ¿Es un numero entero?
     */
    public boolean esNumeroEntero(String lexema) {
        try {

            if (lexema.matches(INTEGER_NUMBERS.REGEX)) {
                return true;
            }

        } catch (PatternSyntaxException ex) {
            System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + INTEGER_NUMBERS.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
        } catch (NullPointerException error) {
            return false;
        }

        return false;
    }

    /**
     * Funcion que verifica si el lexema es un numero flotante.
     *
     * @param lexema Nombre propio, cadena o caracter
     * @return ¿Es un numero flotante?
     */
    public boolean esNumeroFlotante(String lexema) {
        try {

            if (lexema.matches(FLOAT_NUMBERS.REGEX)) {
                return true;
            }

        } catch (PatternSyntaxException ex) {
            System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + FLOAT_NUMBERS.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
        } catch (NullPointerException error) {
            return false;
        }

        return false;
    }

    /**
     * Funcion que verifica si el lexema es una cadena de texto.
     *
     * @param lexema Nombre propio, cadena o caracter
     * @return ¿Es una cadena de texto?
     */
    public boolean esCadenaTexto(String lexema) {
        try {

            if (lexema.matches(STRINGS.REGEX)) {
                return true;
            }

        } catch (PatternSyntaxException ex) {
            System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + STRINGS.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
        } catch (NullPointerException error) {
            return false;
        }

        return false;
    }

    /**
     * Funcion que verifica si el lexema es un Operador del lenguaje.
     *
     * @param lexema Nombre propio, cadena o caracter
     * @return ¿Es un Operador?
     */
    public boolean esSignoOperacional(String lexema) {
        for (Categoria signo : OPERATORS.getAllSignos()) {
            try {

                // ◂ ◂ Si alguno de los signos operacionales que se encuentren en las clasificaciones coincide ▸ ▸ //
                if (lexema.matches(signo.REGEX)) {
                    return true;
                }

            } catch (PatternSyntaxException ex) {
                System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + signo.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
            } catch (NullPointerException error) {
                return false;
            }
        }
        return false;
    }

    /**
     * Funcion que verifica si el lexema es un caracter especial del lenguaje.
     *
     * @param lexema Nombre propio, cadena o caracter
     * @return ¿Es un caracter especial?
     */
    public boolean esCaracterEspecial(String lexema) {
        for (Categoria caracter_especial : SPECIAL_CHARS) {
            try {

                // ◂ ◂ Si alguna de los caracteres especiales que se encuentran en las clasificaciones coincide ▸ ▸ //
                if (lexema.matches(caracter_especial.REGEX)) {
                    return true;
                }

            } catch (PatternSyntaxException ex) {
                System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + caracter_especial.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
            } catch (NullPointerException error) {
                return false;
            }
        }
        return false;
    }

    /**
     * Funcion que verifica si el simbolo ingresado es un signo de apertura.
     *
     * @param lexema Nombre propio, caracter o cadena de texto.
     * @return ¿Es un signo de apertura?
     */
    public boolean esSignoApertura(String lexema) {
        try {

            if (lexema.matches(OPENER_SIGN.REGEX)) {
                return true;
            }

        } catch (PatternSyntaxException ex) {
            System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + STRINGS.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
        } catch (NullPointerException error) {
            return false;
        }

        return false;
    }

    /**
     * Funcion que verifica si el simbolo ingresado es un signo de cierre.
     *
     * @param lexema Nombre propio, caracter o cadena de texto.
     * @return ¿Es un signo de cierre?
     */
    public boolean esSignoCierre(String lexema) {

        try {

            if (lexema.matches(CLOSER_SIGN.REGEX)) {
                return true;
            }

        } catch (PatternSyntaxException ex) {
            System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + STRINGS.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
        } catch (NullPointerException error) {
            return false;
        }

        return false;
    }

    /**
     * Funcion que verifica si el simbolo ingresado es un signo de asignacion de variables.
     *
     * @param lexema Nombre propio, caracter o cadena de texto.
     * @return ¿Es un signo de asignacion?
     */
    public boolean esSignoAsignacion(String lexema) {
        for (Categoria signo : OPERATORS.getSignosAsignacion()) {
            try {

                // ◂ ◂ Si alguna de los caracteres especiales que se encuentran en las clasificaciones coincide ▸ ▸ //
                if (lexema.matches(signo.REGEX)) {
                    return true;
                }

            } catch (PatternSyntaxException ex) {
                System.err.println("ERROR, NO PUEDE ANALIZAR LA EXPRESION REGULAR: " + signo.REGEX + "\n CORRIJALO PARA CONTINUAR EVALUANDO.");
            } catch (NullPointerException error) {
                return false;
            }
        }
        return false;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ CLASES INTERNAS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Clase que almacena los simbolos representativos de los operadores aritmeticos, de asignacion, comparacion y logicas
     * de una sentencia que pertenezcan al lenguaje.
     */
    public static class Operadores {

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

        // ◂ ◂ ◂ ◂ Aritmetica ▸ ▸ ▸ ▸ //
        public final Categoria ADDITION;
        public final Categoria SUBTRACTION;
        public final Categoria MULTIPLICATION;
        public final Categoria DIVISION;
        public final Categoria EXPONENTIATION;
        public final Categoria MODULO;
        public final Categoria INTEGER_DIVISION;

        // ◂ ◂ ◂ ◂ Asignacion ▸ ▸ ▸ ▸ //
        public final Categoria ASIGN;
        public final Categoria ADD_ASIGN;
        public final Categoria SUB_ASIGN;
        public final Categoria MULT_ASIGN;
        public final Categoria DIV_ASIGN;
        public final Categoria MOD_ASIGN;
        public final Categoria INT_DIV_ASIGN;

        // ◂ ◂ ◂ ◂ Comparacion ▸ ▸ ▸ ▸ //
        public final Categoria EQUAL_TO;
        public final Categoria LESS_THAN;
        public final Categoria GREATER_THAN;
        public final Categoria LESS_EQUAL_TO;
        public final Categoria GREATER_EQUAL_TO;
        public final Categoria NOT_EQUAL_TO;

        // ◂ ◂ ◂ ◂ Logica ▸ ▸ ▸ ▸ //
        public final Categoria AND;
        public final Categoria OR;
        public final Categoria NOT;

        /**
         * Clase que almacena los simbolos representativos de los operadores aritmeticos, de asignacion, comparacion y logicas
         * de una sentencia que pertenezcan al lenguaje.
         *
         * @param suma            Simbolo aritmetico que representa la suma en el lenguaje.
         * @param resta           Simbolo aritmetico que representa la resta en el lenguaje.
         * @param multiplicacion  Simbolo aritmetico que representa la multiplicacion en el lenguaje.
         * @param division        Simbolo aritmetico que representa la division en el lenguaje.
         * @param exponenciacion  Simbolo aritmetico que representa la exponenciacion en el lenguaje.
         * @param modulo          Simbolo aritmetico que representa el modulo en el lenguaje.
         * @param division_entera Simbolo aritmetico que representa la division entera en el lenguaje.
         * @param asignacion      Simbolo que representa la asignacion de memoria en el lenguaje.
         * @param suma_asigna     Simbolo que representa en el lenguaje la suma de un valor antes de asignarse en memoria a si mismo.
         * @param resta_asigna    Simbolo que representa en el lenguaje la resta de un valor antes de asignarse en memoria a si mismo.
         * @param mult_asigna     Simbolo que representa en el lenguaje la multiplicacion de un valor antes de asignarse en memoria a si mismo.
         * @param div_asigna      Simbolo que representa en el lenguaje la division de un valor antes de asignarse en memoria a si mismo.
         * @param mod_asigna      Simbolo que representa en el lenguaje el modulo de un valor antes de asignarse en memoria a si mismo.
         * @param div_int_asigna  Simbolo que representa en el lenguaje la division entera de un valor antes de asignarse en memoria a si mismo.
         * @param igual_que       Simbolo que representa en el lenguaje la igualdad entre dos valores.
         * @param menor_que       Simbolo que representa en el lenguaje el menor de un valor.
         * @param mayor_que       Simbolo que representa en el lenguaje el mayor de un valor.
         * @param menor_igual_que Simbolo que representa en el lenguaje el menor o igual de un valor.
         * @param mayor_igual_que Simbolo que representa en el lenguaje el mayor o igual de un valor.
         * @param diferente_que   Simbolo que representa en el lenguaje si es diferente a un valor.
         * @param and             Simbolo que representa en el lenguaje la compuerta logica AND.
         * @param or              Simbolo que representa en el lenguaje la compuerta logica OR.
         * @param not             Simbolo que representa en el lenguaje la compuerta logica NOT.
         */
        public Operadores(Categoria suma,
                          Categoria resta,
                          Categoria multiplicacion,
                          Categoria division,
                          Categoria exponenciacion,
                          Categoria modulo,
                          Categoria division_entera,
                          Categoria asignacion,
                          Categoria suma_asigna,
                          Categoria resta_asigna,
                          Categoria mult_asigna,
                          Categoria div_asigna,
                          Categoria mod_asigna,
                          Categoria div_int_asigna,
                          Categoria igual_que,
                          Categoria menor_que,
                          Categoria mayor_que,
                          Categoria menor_igual_que,
                          Categoria mayor_igual_que,
                          Categoria diferente_que,
                          Categoria and,
                          Categoria or,
                          Categoria not
        ) {
            this.ADDITION = suma;
            this.SUBTRACTION = resta;
            this.MULTIPLICATION = multiplicacion;
            this.DIVISION = division;
            this.EXPONENTIATION = exponenciacion;
            this.MODULO = modulo;
            this.INTEGER_DIVISION = division_entera;
            this.ASIGN = asignacion;
            this.ADD_ASIGN = suma_asigna;
            this.SUB_ASIGN = resta_asigna;
            this.MULT_ASIGN = mult_asigna;
            this.DIV_ASIGN = div_asigna;
            this.MOD_ASIGN = mod_asigna;
            this.INT_DIV_ASIGN = div_int_asigna;
            this.EQUAL_TO = igual_que;
            this.LESS_THAN = menor_que;
            this.GREATER_THAN = mayor_que;
            this.LESS_EQUAL_TO = menor_igual_que;
            this.GREATER_EQUAL_TO = mayor_igual_que;
            this.NOT_EQUAL_TO = diferente_que;
            this.AND = and;
            this.OR = or;
            this.NOT = not;
        }

        @Override
        public String toString() {
            String resultado = "";

            for (Categoria signo : getAllSignos()) {
                resultado += "\n" + signo;
            }

            return resultado;
        }

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

        /**
         * Metodo que agrupa todos los signos aritmeticos del lenguaje.
         *
         * @return Todos los signos aritmeticos del lenguaje.
         */
        private Categoria[] getSignosAritmeticos() {
            return new Categoria[]{ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, EXPONENTIATION, MODULO, INTEGER_DIVISION};
        }

        /**
         * Metodo que agrupa todos los signos de asignacion del lenguaje.
         *
         * @return Todos los signos de asignacion del lenguaje.
         */
        private Categoria[] getSignosAsignacion() {
            return new Categoria[]{ASIGN, ADD_ASIGN, SUB_ASIGN, MULT_ASIGN, DIV_ASIGN, MOD_ASIGN, INT_DIV_ASIGN};
        }

        /**
         * Metodo que agrupa todos los signos de comparacion del lenguaje.
         *
         * @return Todos los signos de comparacion del lenguaje.
         */
        private Categoria[] getSignosComparacion() {
            return new Categoria[]{EQUAL_TO, LESS_THAN, GREATER_THAN, LESS_EQUAL_TO, GREATER_EQUAL_TO, NOT_EQUAL_TO};

        }

        /**
         * Metodo que agrupa todos los signos logicos del lenguaje.
         *
         * @return Todos los signos logicos del lenguaje.
         */
        private Categoria[] getSignosLogicos() {
            return new Categoria[]{AND, OR, NOT};

        }

        /**
         * Metodo que agrupa todos los operadores del lenguaje.
         *
         * @return Todos los operadores del lenguaje (Incluyendo caracter de apertura y cierre).
         */
        private LinkedHashSet<Categoria> getAllSignos() {

            LinkedHashSet<Categoria> allElements = new LinkedHashSet<>();

            for (Categoria signo : getSignosAritmeticos()) {
                if (signo.esValido()) {
                    allElements.add(signo);
                }
            }

            for (Categoria signo : getSignosAsignacion()) {
                if (signo.esValido()) {
                    allElements.add(signo);
                }
            }

            for (Categoria signo : getSignosComparacion()) {
                if (signo.esValido()) {
                    allElements.add(signo);
                }
            }

            for (Categoria signo : getSignosLogicos()) {
                if (signo.esValido()) {
                    allElements.add(signo);
                }
            }

            return allElements;
        }

        /**
         * Funcion que devuelve el rango jerarquico o precedencia de operaciones.
         *
         * @param lexema Nombre propio, caracter o cadena de texto.
         * @return Nivel de jerarquia de operaciones.
         * @implNote Asignacion, comparacion y booleanos = 0
         */
        public int precedenciaAritmetica(String lexema) {

            if (esSignoComparacion(lexema)) {
                return 1;
            }

            if (ADDITION.esValido()) {
                if (lexema.matches(ADDITION.REGEX)) {
                    return 2;
                }
            }

            if (SUBTRACTION.esValido()) {
                if (lexema.matches(SUBTRACTION.REGEX)) {
                    return 2;
                }
            }

            if (MULTIPLICATION.esValido()) {
                if (lexema.matches(MULTIPLICATION.REGEX)) {
                    return 3;
                }
            }
            if (DIVISION.esValido()) {
                if (lexema.matches(DIVISION.REGEX)) {
                    return 3;
                }
            }
            if (MODULO.esValido()) {
                if (lexema.matches(MODULO.REGEX)) {
                    return 3;
                }
            }
            if (INTEGER_DIVISION.esValido()) {
                if (lexema.matches(INTEGER_DIVISION.REGEX)) {
                    return 3;
                }
            }
            if (EXPONENTIATION.esValido()) {
                if (lexema.matches(EXPONENTIATION.REGEX)) {
                    return 4;
                }
            }
            // ◂ ◂ ◂ ◂ Signos de asignacion y logicos▸ ▸ ▸ ▸ //
            return 0;
        }

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VALIDACION ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

        /**
         * Funcion que verifica si el simbolo ingresado es un signo aritmetico.
         *
         * @param lexema Nombre propio, caracter o cadena de texto.
         * @return ¿Es un signo aritmetico?
         */
        public boolean esSignoAritmetico(String lexema) {

            for (Categoria signo : getSignosAritmeticos()) {
                if (signo.esValido()) {
                    if (lexema.matches(signo.REGEX)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * Funcion que verifica si el simbolo ingresado es un signo de asignacion de memoria.
         *
         * @param lexema Nombre propio, caracter o cadena de texto.
         * @return ¿Es un signo de asignacion?
         */
        public boolean esSignoAsignacion(String lexema) {

            for (Categoria signo : getSignosAsignacion()) {
                if (signo.esValido()) {
                    if (lexema.matches(signo.REGEX)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * Funcion que verifica si el simbolo ingresado es un signo de comparacion entre dos variables o constantes.
         *
         * @param lexema Nombre propio, caracter o cadena de texto.
         * @return ¿Es un signo de comparacion?
         */
        public boolean esSignoComparacion(String lexema) {

            for (Categoria signo : getSignosComparacion()) {
                if (signo.esValido()) {
                    if (lexema.matches(signo.REGEX)) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * Funcion que verifica si el simbolo ingresado es un signo logico o booleano.
         *
         * @param lexema Nombre propio, caracter o cadena de texto.
         * @return ¿Es un signo booleano o logico?
         */
        public boolean esSignoLogico(String lexema) {

            for (Categoria signo : getSignosLogicos()) {
                if (signo.esValido()) {
                    if (lexema.matches(signo.REGEX)) {
                        return true;
                    }
                }
            }

            return false;
        }

    } //Operadores

    /**
     * Clase que almacena los atributos de la tabla de clasificacion lexica.
     */
    public static class Categoria {

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ ATRIBUTOS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

        /**
         * Nombre o titulo de la clasificacion lexica.
         */
        public final String NAME;
        /**
         * Expresion regular que pertenece a esa clasificacion lexica.
         */
        public final String REGEX;
        /**
         * Atributo o propiedad que posee la clasificacion lexica.
         */
        public final int ATTRIBUTE;

        /**
         * Clase que almacena los atributos de la tabla de clasificacion lexica
         *
         * @param nombre   Descripcion de que tipo de clasificacion lexica es.
         * @param regex    Expresion regular que representa esa clasificacion.
         * @param atributo Propiedad numerica que pertenece esa clasificacion.
         */
        public Categoria(String nombre, String regex, int atributo) {
            this.NAME = nombre;
            this.REGEX = regex;
            this.ATTRIBUTE = atributo;
        }

        @Override
        public String toString() {
            return "Clasificacion: " + NAME + " --> [" + REGEX + "] [" + ATTRIBUTE + "]";
        }

        /**
         * Funcion que verifica si la clasificacion lexica no sea vacia o incorrecta
         *
         * @return ¿No esta vacia o es incorrecta?
         */
        public boolean esValido() {
            return ATTRIBUTE > 0;
        }

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ EQUALS POR ATRIBUTO ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Categoria that = (Categoria) o;
            return ATTRIBUTE == that.ATTRIBUTE;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(ATTRIBUTE);
        }


    } //Categoria

} //Clasificacion
