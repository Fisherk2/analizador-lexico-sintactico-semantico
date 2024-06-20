import lexico.Lexic;
import lexico.Token;
import sintactico.Syntax;
import util.PilaDeAliexpress;

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
    //private Stack<String> stackDriver;
    private PilaDeAliexpress stackDriver;
    private LinkedList<String> lineasCodigoFuente;

    private boolean hayError;
    private int numLineaCodigo;
    private int bof;
    private int eof;


    /**
     * Clase que analiza el codigo fuente utilizando un analizador lexico y sintactico.
     *
     * @param analizador_lexer  Analizador lexico.
     * @param analizador_parser Analizador sintactico.
     */
    public Analizador(Lexic analizador_lexer, Syntax analizador_parser) {

        LEXICO = analizador_lexer;
        SINTACTICO = analizador_parser;

    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ PROCESO DE ANALISIS DE CODIGO FUENTE ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Metodo que analiza lexica y sintacticamente codigos fuente.
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

        System.out.println("\n■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣ METODO LLDRIVER PASO A PASO ■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣\n");
        aplicarLLDriver();
        System.out.println("\n■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣ ANALISIS TERMINADO ■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣■▣□▣\n");

    }

    /**
     * Funcion que genera una pila con un archivo de texto plano separado por saltos de linea.
     *
     * @param texto Texto plano.
     * @return Pila de un texto separado por saltos de linea.
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

    private void aplicarLLDriver() {

        // ◂ ◂ ◂ ◂ Iniciamos nueva pila vacia ▸ ▸ ▸ ▸ //
        //stackDriver = new Stack<>();
        stackDriver = new PilaDeAliexpress();

        // ◂ ◂ ◂ ◂ Push(s) -- poner el símbolo inicial en la pila vacía ▸ ▸ ▸ ▸ //
        stackDriver.push(SINTACTICO.getSimboloInicial());
        System.out.println("1) Push (S) en la pila vacia: " + stackDriver.peek());

        // ◂ ◂ ◂ ◂ Asigne a [x] el símbolo en la parte alta de la pila ▸ ▸ ▸ ▸ //
        int x = SINTACTICO.obtenerIndiceNoTerminal(stackDriver.peek());
        System.out.println("2) Asigne a [x] el símbolo en la parte alta de la pila: " + x);

        // ◂ ◂ ◂ ◂ Asigne a [a] el token de entrada ▸ ▸ ▸ ▸ //
        Token entrada_a = pedirToken();
        int a = SINTACTICO.obtenerIndiceTerminal(convertirATerminal(entrada_a));
        System.out.println("3) Asigne a [a] el token de entrada: " + entrada_a + " [a]: " + a);

        System.out.println("☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲");

        // ◂ ◂ ◂ ◂ while not stack empty loop ▸ ▸ ▸ ▸ //
        while (!stackDriver.empty()) {

            // ◂ ◂ ◂ ◂ If x in noterminals Then ▸ ▸ ▸ ▸ //
            System.out.println("4) Si " + stackDriver.peek() + " es no terminal, entonces: ");
            if (SINTACTICO.esNoTerminal(stackDriver.peek())) {

                // ◂ ◂ ◂ ◂ Obtenemos el indice de [x] de la matriz predictiva ▸ ▸ ▸ ▸ //
                x = SINTACTICO.obtenerIndiceNoTerminal(stackDriver.peek());
                System.out.println("5) Obtenemos el indice de la matriz predictiva donde se ubica [x]: " + x);

                // ◂ ◂ ◂ ◂ If Predict[x,a] !=0 ▸ ▸ ▸ ▸ //
                System.out.println("6) Si no es cero, osea Predict[" + x + "," + a + "] = " + SINTACTICO.PREDICT[x][a] + ", entonces: ");
                if (SINTACTICO.PREDICT[x][a] != 0) {

                    // ◂ ◂ ◂ ◂ Un Pop() y un ciclo de Push() de la produccion del id que se encuentra en Predict[x,a] ▸ ▸ ▸ ▸ //

                    System.out.println("7) Hacemos Pop a " + stackDriver.peek());
                    stackDriver.pop();


                    System.out.println("8) Remplazamos [x] por la produccion de Predict[x,a]");
                    System.out.print("Produccion de Predict[" + x + "," + a + "] = ");
                    for (String simboloNut : SINTACTICO.obtenerProduccion(SINTACTICO.PREDICT[x][a])) {
                        System.out.print("[" + simboloNut + "]");
                    }
                    System.out.println();

                    pushInvertido(SINTACTICO.obtenerProduccion(SINTACTICO.PREDICT[x][a]));
                    System.out.println("9) Hacemos ciclo de Push a la produccion de derecha a izquierda");

                } else {

                    // ◂ ◂ ◂ ◂ Procesa error de sintaxis ▸ ▸ ▸ ▸ //
                    hayError = true;
                    System.err.println("HAY ERROR DE SINTAXIS, YA QUE Predict[" + x + "," + a + "] = " + SINTACTICO.PREDICT[x][a] + " NO APUNTA A NINGUN ID DE PRODUCCION DE LA GRAMATICA");
                    return;

                }
            } else {

                // ◂ ◂ ◂ ◂ If x == a Then ▸ ▸ ▸ ▸ //
                System.out.println("10) Si " + entrada_a + " es igual ó es un " + stackDriver.peek());
                if (sonElMismoSimboloTerminal(stackDriver.peek(), convertirATerminal(entrada_a))) {

                    // ◂ ◂ ◂ ◂ Pop() ▸ ▸ ▸ ▸ //
                    System.out.println("11) Hacemos Pop a " + stackDriver.peek());
                    stackDriver.pop();
                    // ◂ ◂ ◂ ◂ a = scanner() ▸ ▸ ▸ ▸ //
                    entrada_a = pedirToken();
                    a = SINTACTICO.obtenerIndiceTerminal(convertirATerminal(entrada_a));
                    System.out.println("12) Pedimos proximo token de entrada: " + entrada_a + " [a]: " + a);

                } else {

                    // ◂ ◂ ◂ ◂ Procesa error de sintaxis ▸ ▸ ▸ ▸ //
                    hayError = true;
                    System.err.println("HAY ERROR DE SINTAXIS, YA QUE " + entrada_a + " NO ES IGUAL ó NO ES UN " + stackDriver.peek());
                    return;
                }
            }

//            System.out.println("☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲ PILA ☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲");
//            for (int i = stackDriver.size() - 1; i >= 0; i--) {
//                System.out.println(stackDriver.get(i));
//            }

            System.out.println(stackDriver);

            System.out.println("☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲ TABLA DE SIMBOLOS ☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲");

            for (Token simbolo : LEXICO.getTABLA_SIMBOLOS()) {
                System.out.println(simbolo);
            }

            System.out.println("☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲");

        }

        // ◂ ◂ ◂ ◂ Validar si la pila esta vacia y el apuntador del token de entrada sea fin de archivo ▸ ▸ ▸ ▸ //
        System.out.println("13) ¿La pila esta vacia y el apuntador " + entrada_a + " es EOF ($)?: ");
        if (!stackDriver.empty() && !entrada_a.LEXEMA.equals("EOF")) {
            hayError = true;
            System.err.println(hayError + " POR LO TANTO, HAY ERROR DE SINTAXIS, YA QUE LA PILA NO ESTA VACIA");
        }

        System.out.print(!hayError);
    }


    /**
     * Metodo que hace push a la pila los elementos de la produccion de izquierda a derecha.
     *
     * @param produccion Produccion que contiene simbolos terminales y no terminales.
     */
    private void pushInvertido(String[] produccion) {

        for (int i = produccion.length - 1; i >= 0; i--) {

            // ◂ ◂ ◂ ◂ Si hay vacios en la produccion, no se agrega a la pila ▸ ▸ ▸ ▸ //
            if (!produccion[i].isEmpty()) {
                stackDriver.push(produccion[i]);

            }

        }
    }

    /**
     * Funcion que transforma el token de entrada en un simbolo gramatical para su posterior comparacion.
     *
     * @param entrada_a Token de entrada.
     * @return Simbolo terminal de la gramatica.
     */
    private String convertirATerminal(Token entrada_a) {

        // ◂ ◂ ◂ ◂ Si pertenecen explicitamente al conjunto de terminales ▸ ▸ ▸ ▸ //
        if (SINTACTICO.esTerminal(entrada_a.LEXEMA)) {
            return entrada_a.LEXEMA;
        }

        // ◂ ◂ ◂ ◂ Enviamos el titulo de clasificacion ▸ ▸ ▸ ▸ //
        return LEXICO.queClasificacionEs(entrada_a.LEXEMA).clasificacion;
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
            return new Token("$", 0);
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
        while (bof < linea.length() && esBlanco(linea.charAt(bof))) {
            bof++;
        }

        // ◂ ◂ ◂ ◂ ¿Es una letra o digito? ▸ ▸ ▸ ▸ //
        if (!esCaracterEspecial(linea.charAt(bof)) && !esBlanco(linea.charAt(bof))) {

            // ◂ ◂ ◂ ◂ Igualamos apuntadores para recorrer la cinta ▸ ▸ ▸ ▸ //
            eof = bof;

            // ◂ ◂ ◂ ◂ Recorrera caracteres hasta terminar la linea, encontrar algun caracter especial o un blanco ▸ ▸ ▸ ▸ //
            while (eof < linea.length() && !esCaracterEspecial(linea.charAt(eof)) && !esBlanco(linea.charAt(eof))) {
                eof++;
            }


        } else {
            // ◂ ◂ ◂ ◂ Si es un caracter especial, se trata igual como lexema de un solo caracter ▸ ▸ ▸ ▸ //
            eof = bof + 1;

        }

        // ◂ ◂ ◂ ◂ Generamos lexema ▸ ▸ ▸ ▸ //
        String subcadena = linea.substring(bof, eof);

        // ◂ ◂ ◂ ◂ Igualamos apuntadores para la proxima peticion. ▸ ▸ ▸ ▸ //
        bof = eof;

        // ◂ ◂ ◂ ◂ Generamos Token y lo almacenamos en la tabla de simbolos ▸ ▸ ▸ ▸ //
        Token token = LEXICO.crearToken(subcadena, numLinea);
        LEXICO.almacenarSimbolo(token);

        // ◂ ◂ ◂ ◂ Damos el token generado con esa subcadena ▸ ▸ ▸ ▸ //
        return token;
    }

    /**
     * Funcion que verifica si el caracter ingresado forma parte del alfabeto de caracteres simples del lenguaje.
     *
     * @param caracter Caracter a evaluar.
     * @return ¿Forma parte del alfabeto de caracteres simples del lenguaje?.
     */
    private boolean esCaracterEspecial(char caracter) {

        // ◂ ◂ ◂ ◂ Los caracteres simples siempre seran la SEGUNDA PRIORIDAD de la tabla de clasificacion lexica ▸ ▸ ▸ ▸ //
        return LEXICO.queClasificacionEs(String.valueOf(caracter)).clasificacion.equals(LEXICO.getPrioridadLexica(1));
    }

    /**
     * Funcion que evalua si el caracter ingresado es un caracter blanco (Espacio, saltos de linea, retorno de carro, tabuladores...).
     *
     * @param caracter Caracter a revisar.
     * @return ¿Es un caracter blanco?
     */
    private boolean esBlanco(char caracter) {
        return caracter == ' ' || caracter == '\n' || caracter == '\t' || caracter == '\r';
    }

}
