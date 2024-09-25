//▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ MAIN ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//


/*
30/04/24

 */

import archivos.Fichero;
import lenguaje.*;
import lexico.Lexic;
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

        Semantic semantico = new Semantic(
                (Operadores) new Fichero("operadores.json").deserializar_json(Operadores.class),
                (Procedencia) new Fichero("procedencia.json").deserializar_json(Procedencia.class)
        );

        Analizador analizador = new Analizador(lexico, sintactico, semantico);

        System.out.println(codigo);
        analizador.analizar_codigo_fuente(codigo);

        System.out.println(lexico);
        System.out.println(sintactico);
        System.out.println(semantico);

    }

}