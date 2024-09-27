package lenguaje;

/**
 * Clase que almacena los IDs de la gramatica que establecen las instrucciones o sentencias del programa.
 */
public class Notaciones {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    /**
     * IDs de la gramatica donde se establecen las producciones que inician instrucciones o sentencias.
     */
    public final int[] SENTENCES;

    /**
     * Clase que almacena los IDs de la gramatica que establecen las instrucciones o sentencias del programa.
     *
     * @param ids_instrucciones   IDs de la gramatica donde se establece las producciones que inician instrucciones o sentencias.
     */
    public Notaciones(int[] ids_instrucciones) {
        this.SENTENCES = ids_instrucciones;
    }

    @Override
    public String toString() {

        String resultado = "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  NOTACIONES DE LA GRAMATICA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        resultado += "\nSentencias: ";
        for (int id : SENTENCES) {
            resultado += "[" + id + "]";
        }

        return resultado;
    }
}
