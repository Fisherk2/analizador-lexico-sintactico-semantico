//▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ MAIN ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//


/*
30/04/24

 */

import archivos.Fichero;
import lenguaje.Automata;
import lenguaje.Gramatica;
import lexico.Lexic;
import lexico.Clasificacion;
import semantico.Semantic;
import sintactico.Syntax;


public class Main {


    public static void main(String[] lya1) {
        new Main().probarAnalizador();
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ PRUEBAS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private void probarAnalizador() {

        String codigo = new Fichero("programa.txt").leer_texto_plano();

        Lexic lexico = new Lexic(
                (Automata) new Fichero("automata.json").deserializar_json(Automata.class),
                (Clasificacion[]) new Fichero("tabla_clasificacion_lexica.json").deserializar_json(Clasificacion[].class)
        );

        Syntax sintactico = new Syntax(
                (Gramatica) new Fichero("gramatica.json").deserializar_json(Gramatica.class)
        );

        Semantic semantico = new Semantic();

        Analizador analizador = new Analizador(lexico, sintactico, semantico);

        System.out.println(codigo);
        analizador.analizar_codigo_fuente(codigo);

        System.out.println(lexico);
        System.out.println(sintactico);
        System.out.println(semantico);

    }

}