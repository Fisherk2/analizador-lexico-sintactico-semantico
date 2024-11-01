//▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ MAIN ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//


/*
30/04/24

 */

import archivos.Fichero;
import lenguaje.*;
import lexico.Lexic;
import semantico.Semantic;
import sintactico.Syntax;

import java.util.LinkedList;
import java.util.Stack;


public class Main {


    public static void main(String[] lya1) {
        new Main().probarAnalizador();
        //new Main().rendimiento();
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ PRUEBAS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private void probarAnalizador() {

        String codigo = new Fichero("programaSimple.txt").leer_texto_plano();
        //String codigo = new Fichero("programa.txt").leer_texto_plano();


        Lexic lexico = new Lexic(
                (Automata) new Fichero("automata.json").deserializar_json(Automata.class),
                (Clasificacion) new Fichero("tabla_clasificacion_lexica.json").deserializar_json(Clasificacion.class)
        );

        Syntax sintactico = new Syntax(
                (Gramatica) new Fichero("gramatica.json").deserializar_json(Gramatica.class)
        );

        Semantic semantico = new Semantic(
                (Verificador) new Fichero("verificador.json").deserializar_json(Verificador.class),
                (Notaciones) new Fichero("notaciones.json").deserializar_json(Notaciones.class)
        );

        Analizador analizador = new Analizador(lexico, sintactico, semantico);

        System.out.println(codigo);
        analizador.analizar_codigo_fuente(codigo);
        System.out.println(analizador);

        CodigoIntermedio codigoIntermedio = new CodigoIntermedio(analizador);
        System.out.println(codigoIntermedio);

    }

    private void rendimiento() {
        LinkedList<Integer> lista = new LinkedList<>();
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        // ◂ ◂ ◂ ◂ Generar 50,000 numeros del 1 al 500 ▸ ▸ ▸ ▸ //
        for (int i = 0; i < 50_000; i++) {
            int numeroAleatorio = (int) (Math.random() * 500) + 1;
            lista.add(numeroAleatorio);
        }
        endTime = System.currentTimeMillis() - startTime;
        System.out.println("Tiempo en generar numeros aleatorios: " + endTime +" milisegundos");

        // ◂ ◂ ◂ ◂ Medimos tiempo de ejecucion ▸ ▸ ▸ ▸ //
        startTime = System.currentTimeMillis();
        Stack<Integer> pilaPrueba = pushInvertido1(lista);
        endTime = System.currentTimeMillis() - startTime;

        System.out.println("Tiempo de ejecucion: " + endTime +" milisegundos");
        System.out.println("===============================Pila prueba==================");
        for (int i = 0; i < 10; i++) {
            System.out.println(pilaPrueba.get(i));
        }

        // ◂ ◂ ◂ ◂ Medimos tiempo de ejecucion ▸ ▸ ▸ ▸ //
        startTime = System.currentTimeMillis();
        Stack<Integer> pilaNueva1 = pushInvertido1(lista);
        endTime = System.currentTimeMillis() - startTime;

        System.out.println("Tiempo de ejecucion: " + endTime +" milisegundos");
        System.out.println("===============================Pila 1==================");
        for (int i = 0; i < 10; i++) {
            System.out.println(pilaNueva1.get(i));
        }

        // ◂ ◂ ◂ ◂ Medimos tiempo de ejecucion ▸ ▸ ▸ ▸ //
        startTime = System.currentTimeMillis();
        Stack<Integer> pilaNueva2 = pushInvertido2(lista);
        endTime = System.currentTimeMillis() - startTime;

        System.out.println("Tiempo de ejecucion: " + endTime +" milisegundos");
        System.out.println("===============================Pila 2 ==================");
        for (int i = 0; i < 10; i++) {
            System.out.println(pilaNueva2.get(i));
        }

        // ◂ ◂ ◂ ◂ Medimos tiempo de ejecucion ▸ ▸ ▸ ▸ //
        startTime = System.currentTimeMillis();
        Stack<Integer> pilaNueva3 = pushInvertido3(lista);
        endTime = System.currentTimeMillis() - startTime;

        System.out.println("Tiempo de ejecucion: " + endTime +" milisegundos");
        System.out.println("===============================Pila 3 ==================");
        for (int i = 0; i < 10; i++) {
            System.out.println(pilaNueva3.get(i));
        }

    }

    private Stack<Integer> pushInvertido3(LinkedList<Integer> lista) {
        Stack<Integer> pilaNueva = new Stack<>();

        // ◂ ◂ ◂ ◂ Usando While ▸ ▸ ▸ ▸ //
        int i = lista.size() - 1;
        while (i >= 0) {
            pilaNueva.push(lista.get(i));
            i--;
        }

        return pilaNueva;
    }

    private Stack<Integer> pushInvertido2(LinkedList<Integer> lista) {
        Stack<Integer> pilaNueva = new Stack<>();

        for (int i = lista.size() - 1; i >= 0; i--) {

            // ◂ ◂ ◂ ◂ Variable inecesaria ▸ ▸ ▸ ▸ //
            int numero = lista.get(i);

            pilaNueva.push(numero);
        }

        return pilaNueva;
    }

    private Stack<Integer> pushInvertido1(LinkedList<Integer> lista) {
        Stack<Integer> pilaNueva = new Stack<>();

        for (int i = lista.size() - 1; i >= 0; i--) {
            pilaNueva.push(lista.get(i));
        }

        return pilaNueva;
    }

}