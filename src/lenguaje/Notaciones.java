package lenguaje;

import java.util.LinkedHashSet;

/**
 * Clase que almacena los IDs de la gramatica que establecen las instrucciones o sentencias del programa y los clasifica en tipos.
 */
public class Notaciones {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    /**
     * IDs de la gramatica donde se establecen las producciones que declaran variable o constantes.
     */
    public final int[] DECLARATIONS;
    /**
     * IDs de la gramatica donde se establecen las producciones donde se incluyen operaciones aritméticas, llamadas a funciones o manipulación de variables.
     */
    public final int[] EXPRESSIONS_ASIGNMENTS;
    /**
     * IDs de la gramatica donde se establecen las producciones donde se recibe o muestra informacion del usuario en la consola o archivo.
     */
    public final int[] INPUTS_OUPUTS;
    /**
     * IDs de la gramatica donde se establecen las producciones donde se ejecutan bloques de código según un valor booleano.
     */
    public final int[] CONTROL_FLOW;
    /**
     * IDs de la gramatica donde se establecen las producciones donde se ejecutan bloques en repetición controlada por un contador o valor booleano.
     */
    public final int[] LOOPS;
    /**
     * IDs de la gramatica donde se establecen las producciones que declaran metodos o funciones.
     */
    public final int[] FUNCTIONS;
    /**
     * IDs de la gramatica donde se establecen las producciones donde termina la ejecución de métodos y opcionalmente devuelve un valor.
     */
    public final int[] RETURNS;
    /**
     * IDs de la gramatica donde se establecen las producciones que declaran clases u objetos del programa.
     */
    public final int[] CLASSES;
    /**
     * IDs de la gramatica donde se establecen las producciones que controla el acceso a métodos o bloques en entornos multihilo.
     */
    public final int[] THREADS;
    /**
     * IDs de la gramatica donde se establecen las producciones que manejan bloques de codigo donde puede ocurrir una excepcion.
     */
    public final int[] HANDLER_EXCEPTION;

    /**
     * Clase que almacena los IDs de la gramatica que establecen las instrucciones o sentencias del programa y los clasifica en tipos.
     *
     * @param declaraciones            IDs de la gramatica donde se establecen las producciones que declaran variable o constantes.
     * @param expresiones_asignaciones IDs de la gramatica donde se establecen las producciones donde se recibe o muestra informacion del usuario en la consola o archivo.
     * @param inputs_ouputs            IDs de la gramatica donde se establecen las producciones donde se recibe o muestra informacion del usuario en la consola o archivo.
     * @param flujo_control            IDs de la gramatica donde se establecen las producciones donde se ejecutan bloques de código según un valor booleano.
     * @param bucles                   IDs de la gramatica donde se establecen las producciones donde se ejecutan bloques en repetición controlada por un contador o valor booleano.
     * @param funciones                IDs de la gramatica donde se establecen las producciones que declaran metodos o funciones.
     * @param retornos                 IDs de la gramatica donde se establecen las producciones donde termina la ejecución de métodos y opcionalmente devuelve un valor.
     * @param clases                   IDs de la gramatica donde se establecen las producciones que controla el acceso a métodos o bloques en entornos multihilo.
     * @param hilos                    IDs de la gramatica donde se establecen las producciones que controla el acceso a métodos o bloques en entornos multihilo.
     * @param manejo_excepciones       IDs de la gramatica donde se establecen las producciones que manejan bloques de codigo donde puede ocurrir una excepcion.
     * @implNote Si tu programa no utiliza algun tipo de sentencia, solo debes asignarlo como CERO = [0].
     */
    public Notaciones(int[] declaraciones, int[] expresiones_asignaciones, int[] inputs_ouputs, int[] flujo_control, int[] bucles, int[] funciones, int[] retornos, int[] clases, int[] hilos, int[] manejo_excepciones) {
        this.DECLARATIONS = declaraciones;
        this.EXPRESSIONS_ASIGNMENTS = expresiones_asignaciones;
        this.INPUTS_OUPUTS = inputs_ouputs;
        this.CONTROL_FLOW = flujo_control;
        this.LOOPS = bucles;
        this.FUNCTIONS = funciones;
        this.RETURNS = retornos;
        this.CLASSES = clases;
        this.THREADS = hilos;
        this.HANDLER_EXCEPTION = manejo_excepciones;
    }

    @Override
    public String toString() {

        String resultado = "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  NOTACIONES DE LA GRAMATICA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        resultado += "\nNOTACIONES: ";
        for (int id : getAllTypeStatements()) {
            resultado += "[" + id + "]";
        }

        resultado += "\n";

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que acumula todos los tipos de sentencias establecidos en la gramatica.
     *
     * @return Todos los tipos de sentencias establecidos por la gramatica.
     */
    public LinkedHashSet<Integer> getAllTypeStatements() {
        LinkedHashSet<Integer> allStatements = new LinkedHashSet<>();

        for (int statement : DECLARATIONS) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : EXPRESSIONS_ASIGNMENTS) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : INPUTS_OUPUTS) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : CONTROL_FLOW) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : LOOPS) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : FUNCTIONS) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : RETURNS) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : CLASSES) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : THREADS) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        for (int statement : HANDLER_EXCEPTION) {
            if (statement != 0) {
                allStatements.add(statement);
            }
        }

        return allStatements;
    }
}