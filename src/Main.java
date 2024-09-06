//▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ MAIN ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//


/*
30/04/24

 */

import archivos.Fichero;
import lenguaje.Automata;
import lenguaje.Gramatica;
import lexico.Lexic;
import lexico.Clasificacion;
import sintactico.Syntax;


public class Main {


    public static void main(String[] lya1) {
        new Main().probarAnalizadorLexicoSintactico();
        //new Main().probarGramatica();
        //new Main().probarAutomata();
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ PRUEBAS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private void probarAnalizadorLexicoSintactico() {

        String codigo = new Fichero("programa.txt").leer_texto_plano();

        Lexic lexico = new Lexic(
                (Automata) new Fichero("automata.json").deserializar_json(Automata.class),
                (Clasificacion[]) new Fichero("tabla_clasificacion_lexica.json").deserializar_json(Clasificacion[].class)
        );


        Syntax sintactico = new Syntax(
                (Gramatica) new Fichero("gramatica.json").deserializar_json(Gramatica.class)
        );

        Analizador analizador = new Analizador(lexico, sintactico);


        System.out.println(codigo);
        System.out.println(sintactico);
        analizador.analizar_codigo_fuente(codigo);

        System.out.println(lexico);

    }

    private void probarGramatica() {

        Gramatica gramatica = (Gramatica) new Fichero("gramatica.json").deserializar_json(Gramatica.class);

        System.out.println(gramatica);


        System.out.println("-------------------------------------------------------");

    }

    private void probarAutomata() {
        Automata afn = (Automata) new Fichero("automata.json").deserializar_json(Automata.class);

        //ACEPTABLES
        System.out.println("ACEPTABLES");
        System.out.println("El lexema Sino es aceptada?: " + afn.esAceptada("Sino"));
        System.out.println("El lexema Entero es aceptada?: " + afn.esAceptada("Entero"));

        System.out.println("El lexema cuenta123_r es aceptada?: " + afn.esAceptada("cuenta123_r"));
        System.out.println("El lexema fisher33 es aceptada?: " + afn.esAceptada("fisher33"));
        System.out.println("El lexema jossua_1_2_3 es aceptada?: " + afn.esAceptada("jossua_1_2_3"));

        System.out.println("El lexema 3928 es aceptada?: " + afn.esAceptada("3928"));
        System.out.println("El lexema 0 es aceptada?: " + afn.esAceptada("0"));
        System.out.println("El lexema 18.92 es aceptada?: " + afn.esAceptada("18.92"));
        System.out.println("El lexema 0.0 es aceptada?: " + afn.esAceptada("0.0"));

        System.out.println("El lexema == es aceptada?: " + afn.esAceptada("=="));
        System.out.println("El lexema + es aceptada?: " + afn.esAceptada("+"));

        //RECHAZABLES
        System.out.println("\nRECHAZABLES");
        System.out.println("El lexema .99 es aceptada?: " + afn.esAceptada(".99"));
        System.out.println("El lexema Base es aceptada?: " + afn.esAceptada("Base"));
        System.out.println("El lexema Numero es aceptada?: " + afn.esAceptada("Numero"));
        System.out.println("El lexema fisher_1_ es aceptada?: " + afn.esAceptada("fisher_1_"));
        System.out.println("El lexema 0012 es aceptada?: " + afn.esAceptada("0012"));

    }

}