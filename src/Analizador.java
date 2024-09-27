import lexico.Lexic;
import lexico.Token;
import lexico.TokenError;
import semantico.Semantic;
import semantico.Sentencia;
import sintactico.Syntax;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Clase que analiza el codigo fuente usando un analizador lexico - sintactico.
 */
public class Analizador {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private final Lexic LEXICO;
    private final Syntax SINTACTICO;
    private final Semantic SEMANTICO;

    private Stack<String> stackDriver;
    private LinkedList<String> lineasCodigoFuente;
    private boolean hayError;
    private int numLineaCodigo;
    private int bof;
    private int eof;

    /**
     * Clase que analiza el codigo fuente utilizando un analizador lexico, sintactico y semantico.
     *
     * @param analizador_lexer    Analizador lexico.
     * @param analizador_parser   Analizador sintactico.
     * @param analizador_semantic Analizador semantico.
     */
    public Analizador(Lexic analizador_lexer, Syntax analizador_parser, Semantic analizador_semantic) {

        LEXICO = analizador_lexer;
        SINTACTICO = analizador_parser;
        SEMANTICO = analizador_semantic;

    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ PROCESO DE ANALISIS DE CODIGO FUENTE ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Metodo que analiza lexica, sintacticamente y semanticamente codigos fuente.
     *
     * @param codigo Texto plano donde se almacena el codigo fuente.
     */
    public void analizar_codigo_fuente(String codigo) {

        // ◂ ◂ ◂ ◂ Iniciamos contadores de estados de lectura ▸ ▸ ▸ ▸ //
        hayError = false;
        numLineaCodigo = 1;
        bof = 0;
        eof = 0;

        lineasCodigoFuente = separarPorLineas(codigo);
        firstLLDRIVER();
        System.out.println("\n■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣ ANALISIS LEXICO-SINTACTICO TERMINADO ■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣\n");
        secondLLDRIVER();
    }

    /**
     * Funcion que genera una pila con un archivo de texto plano separado por saltos de linea.
     *
     * @param texto Texto plano.
     * @return Lista enlazada de un texto separado por saltos de linea.
     */
    private LinkedList<String> separarPorLineas(String texto) {

        // ◂ ◂ ◂ ◂ Escaneamos el texto ▸ ▸ ▸ ▸ //
        Scanner escaner = new Scanner(texto);
        LinkedList<String> lineas = new LinkedList<>();

        // ◂ ◂ ◂ ◂ Seguira almacenando sin espacios al principio y final hasta que llegue al final del archivo ▸ ▸ ▸ ▸ //
        while (escaner.hasNextLine()) {
            lineas.add(escaner.nextLine());
        }

        // ◂ ◂ ◂ ◂ Cerramos buffer de lectura ▸ ▸ ▸ ▸ //
        escaner.close();
        return lineas;
    }

    /**
     * Metodo que analiza lexica y sintacticamente un codigo fuente dependiendo del lenguaje,
     * genera la tabla de tokens y la primer tabla de simbolos.
     */
    private void firstLLDRIVER() {

        // ◂ ◂ ◂ ◂ Iniciamos nueva pila vacia ▸ ▸ ▸ ▸ //
        stackDriver = new Stack<>();

        // ◂ ◂ ◂ ◂ Push(s) -- poner el símbolo inicial en la pila vacía ▸ ▸ ▸ ▸ //
        stackDriver.push(SINTACTICO.getSimboloInicial());

        // ◂ ◂ ◂ ◂ Asigne a [x] el símbolo en la parte alta de la pila ▸ ▸ ▸ ▸ //
        int x = SINTACTICO.obtenerIndiceNoTerminal(stackDriver.peek());

        // ◂ ◂ ◂ ◂ Asigne a [a] el token de entrada ▸ ▸ ▸ ▸ //
        Token entrada_a = pedirToken();
        int a = SINTACTICO.obtenerIndiceTerminal(convertirATerminal(entrada_a));

        // ◂ ◂ ◂ ◂ while not stack empty loop ▸ ▸ ▸ ▸ //
        while (!stackDriver.empty()) {

            // ◂ ◂ ◂ ◂ If x in noterminals Then ▸ ▸ ▸ ▸ //
            if (SINTACTICO.esNoTerminal(stackDriver.peek())) {

                // ◂ ◂ ◂ ◂ Obtenemos el indice de [x] de la matriz predictiva ▸ ▸ ▸ ▸ //
                x = SINTACTICO.obtenerIndiceNoTerminal(stackDriver.peek());

                // ◂ ◂ ◂ ◂ If Predict[x,a] !=0 ▸ ▸ ▸ ▸ //
                if (SINTACTICO.PREDICT[x][a] != 0) {

                    // ◂ ◂ ◂ ◂ Un Pop() y un ciclo de Push() de la produccion del id que se encuentra en Predict[x,a] ▸ ▸ ▸ ▸ //
                    stackDriver.pop();
                    pushInvertido(stackDriver, SINTACTICO.obtenerProduccion(SINTACTICO.PREDICT[x][a]));

                } else {

                    // ◂ ◂ ◂ ◂ TODO: Procesa error de sintaxis ▸ ▸ ▸ ▸ //
                    hayError = true;
                    System.err.println("HAY ERROR DE SINTAXIS: PREDICT[x,a] = 0");
                    return;

                }
            } else {

                // ◂ ◂ ◂ ◂ If x == a Then ▸ ▸ ▸ ▸ //
                if (sonElMismoSimboloTerminal(stackDriver.peek(), convertirATerminal(entrada_a))) {

                    // ◂ ◂ ◂ ◂ Pop() ▸ ▸ ▸ ▸ //
                    stackDriver.pop();
                    // ◂ ◂ ◂ ◂ a = scanner() ▸ ▸ ▸ ▸ //
                    entrada_a = pedirToken();
                    a = SINTACTICO.obtenerIndiceTerminal(convertirATerminal(entrada_a));

                } else {

                    // ◂ ◂ ◂ ◂ TODO: Procesa error de sintaxis ▸ ▸ ▸ ▸ //
                    hayError = true;
                    System.err.println("HAY ERROR DE SINTAXIS: x != a");
                    return;
                }
            }
        }

        // ◂ ◂ ◂ ◂ Validar si la pila esta vacia y el apuntador del token de entrada sea fin de archivo ▸ ▸ ▸ ▸ //
        if (!stackDriver.empty() && !entrada_a.LEXEMA.equals("EOF")) {
            // ◂ ◂ ◂ ◂ TODO: Procesa error de sintaxis ▸ ▸ ▸ ▸ //
            hayError = true;
            System.err.println("HAY ERROR DE SINTAXIS: La pila no esta vacia y el apuntador no es fin de archivo");
        }

    }

    /**
     * Metodo que genera tabla de notaciones a partir de la tabla de tokens y actualiza la tabla de simbolos.
     */
    private void secondLLDRIVER() {

        // ◂ ◂ ◂ ◂ Iniciamos nueva pila vacia, el indice de la tabla de tokens y las sentencias que se vayan generado ▸ ▸ ▸ ▸ //
        stackDriver.empty();
        int indiceToken = 0;
        String[] produccion;
        LinkedList<Token> notacion_infija = new LinkedList<>();
        Stack<String> stackSentencia = new Stack<>();
        Sentencia sentencia;

        // ◂ ◂ ◂ ◂ Push(s) -- poner el símbolo inicial en la pila vacía ▸ ▸ ▸ ▸ //
        stackDriver.push(SINTACTICO.getSimboloInicial());

        // ◂ ◂ ◂ ◂ Asignar a [x] el símbolo en la parte alta de la pila ▸ ▸ ▸ ▸ //
        int x = SINTACTICO.obtenerIndiceNoTerminal(stackDriver.peek());

        // ◂ ◂ ◂ ◂ Asigne a [a] el token de entrada ▸ ▸ ▸ ▸ //
        Token entrada_a = LEXICO.getTABLA_DE_TOKENS().get(indiceToken++);
        int a = SINTACTICO.obtenerIndiceTerminal(convertirATerminal(entrada_a));

        // ◂ ◂ ◂ ◂ while not stack empty loop ▸ ▸ ▸ ▸ //
        while (!stackDriver.empty()) {

            // ◂ ◂ ◂ ◂ If x in noterminals Then ▸ ▸ ▸ ▸ //
            if (SINTACTICO.esNoTerminal(stackDriver.peek())) {

                // ◂ ◂ ◂ ◂ Obtenemos el indice de [x] de la matriz predictiva ▸ ▸ ▸ ▸ //
                x = SINTACTICO.obtenerIndiceNoTerminal(stackDriver.peek());
                produccion = SINTACTICO.obtenerProduccion(SINTACTICO.PREDICT[x][a]);

                // ◂ ◂ ◂ ◂ Si Predict[x,a] == conjunto de indices que establecen las sentencias gramaticales ▸ ▸ ▸ ▸ //
                if (SEMANTICO.esSentencia(SINTACTICO.PREDICT[x][a]) && stackSentencia.empty()) {

                    // ◂ ◂ ◂ ◂ Capturamos un ciclo de push() de la produccion del id que pertenece a las sentencias ▸ ▸ ▸ ▸ //
                    stackSentencia.clear();
                    pushInvertido(stackSentencia, produccion);

                } else {

                    // ◂ ◂ ◂ ◂ Seguimos derivando producciones, hasta que vaciemos la produccion de la sentencia ▸ ▸ ▸ ▸ //
                    if (!stackSentencia.empty()) {
                        stackSentencia.pop();
                        pushInvertido(stackSentencia, produccion);

                        // ◂ ◂ ◂ ◂ Cuando terminos de recorrer toda la produccion, almacenamos la notacion, siempre y cuando tenga contenido ▸ ▸ ▸ ▸ //
                    } else if (!notacion_infija.isEmpty()) {

                        sentencia = new Sentencia(
                                notacion_infija.toArray(new Token[0]),
                                convertirPrefija(notacion_infija),
                                convertirPosfija(notacion_infija));

                        // ◂ ◂ ◂ ◂ Verificar tipo de datos en notaciones ▸ ▸ ▸ ▸ //
                        if (verificarDatos(sentencia)) {
                            SEMANTICO.almacenarNotacion(sentencia);
                        } else {
                            // ◂ ◂ ◂ ◂ TODO: Procesa error semantico ▸ ▸ ▸ ▸ //
                            hayError = true;
                            System.err.println("HAY ERROR SEMANTICO en la sentencia: " + sentencia);
                            break;
                        }

                        // ◂ ◂ ◂ ◂ Volvemos a iniciar de nuevo ▸ ▸ ▸ ▸ //
                        notacion_infija.clear();
                    }
                }

                // ◂ ◂ ◂ ◂ Un Pop() y un ciclo de Push() de la produccion del id que se encuentra en Predict[x,a] ▸ ▸ ▸ ▸ //
                stackDriver.pop();
                pushInvertido(stackDriver, produccion);

            } else {

                // ◂ ◂ ◂ ◂ Pop() siempre y cuando la produccion de la sentencias tenga contenido ▸ ▸ ▸ ▸ //
                if (!stackSentencia.empty()) {
                    notacion_infija.add(entrada_a);
                    stackSentencia.pop();
                }
                stackDriver.pop();

                // ◂ ◂ ◂ ◂ a = scanner() ▸ ▸ ▸ ▸ //
                if (indiceToken < LEXICO.getTABLA_DE_TOKENS().size()) {

                    entrada_a = LEXICO.getTABLA_DE_TOKENS().get(indiceToken++);
                    a = SINTACTICO.obtenerIndiceTerminal(convertirATerminal(entrada_a));

                } else {
                    // ◂ ◂ ◂ ◂ Fin de la tabla de tokens ▸ ▸ ▸ ▸ //
                    break;
                }
            }
        }

    }

    /**
     * Metodo que hace push a la pila los elementos de la produccion de izquierda a derecha.
     *
     * @param produccion Produccion que contiene simbolos terminales y no terminales.
     */
    private void pushInvertido(Stack<String> pila, String[] produccion) {

        for (int i = produccion.length - 1; i >= 0; i--) {

            // ◂ ◂ ◂ ◂ Si hay vacios en la produccion, no se agrega a la pila ▸ ▸ ▸ ▸ //
            if (!produccion[i].isEmpty()) {
                pila.push(produccion[i]);

            }

        }
    }

    /**
     * Funcion que transforma el token de entrada en un simbolo gramatical para su posterior comparacion.
     *
     * @param entrada_a Token de entrada.
     * @return Simbolo terminal de la gramatica.
     * @implNote Solo se usa en caso de que quieras convertir el token en una constante o identificador.
     */
    private String convertirATerminal(Token entrada_a) {

        // ◂ ◂ ◂ ◂ Si pertenecen explicitamente al conjunto de terminales ▸ ▸ ▸ ▸ //
        if (SINTACTICO.esTerminal(entrada_a.LEXEMA)) {
            return entrada_a.LEXEMA;
        }

        // ◂ ◂ ◂ ◂ Entonces es una constante o identificador ▸ ▸ ▸ ▸ //
        return entrada_a.CLASIFICACION_LEXICA;
    }

    /**
     * Funcion que compara si el token de entrada es el mismo que un simbolo terminal de la pila.
     *
     * @param peek_x_terminal Simbolo terminal de la gramatica.
     * @param entrada_a       Token de entrada.
     * @return ¿Son lo mismo?
     */
    private boolean sonElMismoSimboloTerminal(String peek_x_terminal, String entrada_a) {
        return peek_x_terminal.equalsIgnoreCase(entrada_a);
    }


    /**
     * Funcion que devuelve el token del codigo fuente, y que cada vez que se vuelve a llamar, devuelva el siguiente token del codigo fuente.
     *
     * @return Token del codigo fuente.
     */
    private Token pedirToken() {

        // ◂ ◂ ◂ ◂ Cuando se intente pedir otro token y el codigo fuente ya se acabo, devolvera fin de archivo ▸ ▸ ▸ ▸ //
        if (numLineaCodigo >= lineasCodigoFuente.size()) {
            return new Token("$");
        }

        // ◂ ◂ ◂ ◂ Recorrer la siguiente linea de texto en caso de que halla una linea vacia o se halla terminado de leer dicha linea ▸ ▸ ▸ ▸ //
        while (lineasCodigoFuente.get(numLineaCodigo - 1).isEmpty() || bof >= lineasCodigoFuente.get(numLineaCodigo - 1).length()) {
            numLineaCodigo++;
            eof = 0;
            bof = 0;
        }

        return extraerToken(lineasCodigoFuente.get(numLineaCodigo - 1), numLineaCodigo);
    }

    /**
     * Funcion que recorre la linea con dos apuntadores [BOF y EOF] que va identificando tokens y los extrae.
     *
     * @param linea    Linea de codigo.
     * @param numLinea Numero donde esta ubicado la linea de codigo.
     * @return Token encontrado en la linea de codigo.
     * @implNote Devolvera null en caso de terminar la linea de codigo.
     */
    private Token extraerToken(String linea, int numLinea) {

        // ◂ ◂ ◂ ◂ Recorremos caracteres blancos hasta encontrar uno que no lo sea ▸ ▸ ▸ ▸ //
        while (bof < linea.length() && LEXICO.esBlanco(linea.charAt(bof))) {
            bof++;
        }

        // ◂ ◂ ◂ ◂ ¿Es una letra o digito? ▸ ▸ ▸ ▸ //
        if (!LEXICO.esCaracterEspecial(linea.charAt(bof)) && !LEXICO.esBlanco(linea.charAt(bof))) {

            // ◂ ◂ ◂ ◂ Igualamos apuntadores para recorrer la cinta ▸ ▸ ▸ ▸ //
            eof = bof;

            // ◂ ◂ ◂ ◂ Recorrera caracteres hasta terminar la linea, encontrar algun caracter especial o un blanco ▸ ▸ ▸ ▸ //
            while (eof < linea.length() && !LEXICO.esCaracterEspecial(linea.charAt(eof)) && !LEXICO.esBlanco(linea.charAt(eof))) {
                eof++;
            }


        } else {
            // ◂ ◂ ◂ ◂ Si es un caracter especial, intentamos detectar una secuencia de caracteres especiales ▸ ▸ ▸ ▸ //
            eof = bof;

            // ◂ ◂ ◂ ◂ Continuamos mientras los caracteres sean especiales ▸ ▸ ▸ ▸ //
            while (eof < linea.length() && LEXICO.esCaracterEspecial(linea.charAt(eof))) {
                eof++;
            }

            // ◂ ◂ ◂ ◂ Si no es un caracter especial compuesto válido, generamos el lexema a un solo caracter ▸ ▸ ▸ ▸ //
            if (!LEXICO.esCaracterEspecial(linea.substring(bof, eof))) {
                eof = bof + 1;
            }

        }

        // ◂ ◂ ◂ ◂ Generamos lexema ▸ ▸ ▸ ▸ //
        String subcadena = linea.substring(bof, eof);

        // ◂ ◂ ◂ ◂ Igualamos apuntadores para la proxima peticion. ▸ ▸ ▸ ▸ //
        bof = eof;

        // ◂ ◂ ◂ ◂ Generamos Token y lo almacenamos en la tabla de simbolos y tokens ▸ ▸ ▸ ▸ //
        Token token;

        if (LEXICO.AFN.esAceptada(subcadena)) {
            token = LEXICO.crearToken(subcadena, numLinea);
        } else {
            // ◂ ◂ ◂ ◂ TODO: Procesa error lexico ▸ ▸ ▸ ▸ //
            token = new TokenError(subcadena, numLinea);
            hayError = true;
            System.err.println("HAY ERROR LEXICO: " + token);
        }

        almacenarTablas(token);

        // ◂ ◂ ◂ ◂ Damos el token generado con esa subcadena ▸ ▸ ▸ ▸ //
        return token;
    }

    /**
     * Funcion que almacena el token generado en las tablas de tokens y de simbolos.
     *
     * @param token Token generado.
     */
    private void almacenarTablas(Token token) {

        // ◂ ◂ ◂ ◂ ¿Es identificador o constante? se almacena ▸ ▸ ▸ ▸ //
        if (LEXICO.esIdentificador(token.LEXEMA)) {
            SEMANTICO.almacenarSimbolo(token);
        }

        if (LEXICO.esConstante(token.LEXEMA)) {
            SEMANTICO.almacenarSimbolo(token, token.LEXEMA);
        }

        // ◂ ◂ ◂ ◂ Si ya se encuentra en la tabla de simbolos, almacenamos el token con el atributo de su tabla de simbolos ▸ ▸ ▸ ▸ //
        if (SEMANTICO.contieneTablaSimbolos(token.LEXEMA)) {
            LEXICO.almacenarToken(new Token(token.LEXEMA, token.CLASIFICACION_LEXICA, SEMANTICO.getSimbolo(token).getATRIBUTO(), token.LINEA_DE_CODIGO));
        } else {
            LEXICO.almacenarToken(token);
        }
    }

    /**
     * Funcion que convierte cualquier expresion infija a prefija.
     *
     * @param expresion_infija Sentencia o instruccion en forma infija.
     */
    private Token[] convertirPrefija(LinkedList<Token> expresion_infija) {
        //TODO: (PENDIENTE) GENERAR ALGORITMO DE CONVERSION PREFIJA
        return null;
    }

    /**
     * Funcion que convierte cualquier expresion infija a posfija.
     *
     * @param expresion_infija Sentencia o instruccion en forma infija.
     */
    private Token[] convertirPosfija(LinkedList<Token> expresion_infija) {
        Stack<Token> pila = new Stack<>();
        LinkedList<Token> expresionPosfija = new LinkedList<>();

        for (Token token : expresion_infija) {

            // ◂ ◂ ◂ ◂ ¿Es identificador o constante? se almacena ▸ ▸ ▸ ▸ //
            if (LEXICO.esConstante(token.LEXEMA) || LEXICO.esIdentificador(token.LEXEMA)) {
                expresionPosfija.add(token);

            } else if (LEXICO.esOperador(token.LEXEMA)) {

                // ◂ ◂ ◂ ◂ Mientras haya un operador en la pila con mayor o igual precedencia ▸ ▸ ▸ ▸ //
                while (!pila.isEmpty() && LEXICO.getJerarquia(pila.peek()) >= LEXICO.getJerarquia(token)) {
                    expresionPosfija.add(pila.pop());
                }

                // ◂ ◂ ◂ ◂ Agregar el operador a la pila ▸ ▸ ▸ ▸ //
                pila.push(token);

                // ◂ ◂ ◂ ◂ Si es un paréntesis de apertura, agregar a la pila ▸ ▸ ▸ ▸ //
            } else if (LEXICO.esCaracterApertura(token.LEXEMA)) {
                pila.push(token);

                // ◂ ◂ ◂ ◂ Si es un paréntesis de cierre, sacar operadores hasta el paréntesis de apertura ▸ ▸ ▸ ▸ //
            } else if (LEXICO.esCaracterCierre(token.LEXEMA)) {
                while (!pila.isEmpty() && !LEXICO.esCaracterApertura(pila.peek().LEXEMA)) {
                    expresionPosfija.add(pila.pop());
                }

                // ◂ ◂ ◂ ◂ Eliminar el paréntesis de apertura ▸ ▸ ▸ ▸ //
                if (!pila.isEmpty() && LEXICO.esCaracterApertura(pila.peek().LEXEMA)) {
                    pila.pop();
                }

                // ◂ ◂ ◂ ◂ Agregar palabra reservada como una instruccion al final de los operadores  ▸ ▸ ▸ ▸ //
            } else if (LEXICO.esPalabraReservada(token.LEXEMA)) {
                pila.push(token);
            }
        }

        // ◂ ◂ ◂ ◂ Sacar cualquier operador restante en la pila ▸ ▸ ▸ ▸ //
        while (!pila.isEmpty()) {
            expresionPosfija.add(pila.pop());
        }

        return expresionPosfija.toArray(new Token[0]);
    }

    private boolean verificarDatos(Sentencia sentencia){
        // ◂ ◂ ◂ ◂ TODO: Verificar tipo de datos en notaciones que involucren expresiones de asignacion ▸ ▸ ▸ ▸ //




        return true;
    }

}
