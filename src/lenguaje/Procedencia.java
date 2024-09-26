package lenguaje;

/**
 * Clase que almacena las configuraciones de verificacion de datos, sentencias y produccion inicial de la gramatica.
 */
public class Procedencia {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
    /**
     * IDs de la gramatica donde se establecen las producciones que inician instrucciones o sentencias.
     */
    public final int[] SENTENCES;
    /**
     * ID de la gramatica donde se establece el inicio del programa.
     */
    public final int INIT;

    /**
     * Tabla de verificacion de datos entre constantes y variables.
     */
    public final Verificador CHECK_DATA;

    /**
     * Clase que almacena las tablas de verificacion, sentencias y produccion inicial gramatical del lenguaje.
     *
     * @param SENTENCES  IDs de la gramatica donde se establece las producciones que inician instrucciones o sentencias.
     * @param INIT       ID de la gramatica donde se establece la produccion que inicia el programa.
     * @param CHECK_DATA Tabla de verificacion de datos entre constantes y variables basado en los atributos de la tabla de clasificaion lexica.
     */
    public Procedencia(int[] SENTENCES, int INIT, Verificador CHECK_DATA) {
        this.SENTENCES = SENTENCES;
        this.INIT = INIT;
        this.CHECK_DATA = CHECK_DATA;
    }

    @Override
    public String toString() {

        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ PROCEDENCIA DE DATOS ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

        resultado += "\nInicio: [" + INIT + "]";

        resultado += "\nSentencias: ";
        for (int id : SENTENCES) {
            resultado += "[" + id + "]";
        }

        resultado += "\n" + CHECK_DATA;

        return resultado;
    }

//▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ CLASES INTERNAS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Clase que almacena la tabla de verificacion de datos de una constante o variable.
     */
    public static class Verificador {

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//
        /**
         * Cruces entre tipos de datos que dan como resultado un tipo de dato entero.
         */
        public final int[][] INTEGER;
        /**
         * Cruces entre tipos de datos que dan como resultado un tipo de dato flotante.
         */
        public final int[][] FLOAT;
        /**
         * Cruces entre tipos de datos que dan como resultado un tipo de dato cadena de texto.
         */
        public final int[][] STRING;

        /**
         * Clase que almacena la tabla de verificacion de datos de una constante o variable,
         * en base a los atributos de la tabla de clasificacion lexica del lenguaje.
         *
         * @param ids_integer Pares de cruces donde el resultado dara un tipo de dato entero.
         * @param ids_float   Pares de cruces donde el resultado dara un tipo de dato flotante.
         * @param ids_string  Pares de cruces donde el resultado dara un tipo de dato cadena de texto.
         * @implNote Si en tu lenguaje no hay operaciones con algun tipo de dato, simplemente dejalo con un CERO = [[0]]
         */
        public Verificador(int[][] ids_integer, int[][] ids_float, int[][] ids_string) {
            this.INTEGER = ids_integer;
            this.FLOAT = ids_float;
            this.STRING = ids_string;
        }

        @Override
        public String toString() {
            String resultado = "\n◂ ◂ ◂ ◂ TABLA DE VERIFICACION DE DATOS ▸ ▸ ▸ ▸ \n";

            resultado += "INTEGER:\n";

            for (int i = 0; i < INTEGER.length; i++) {
                for (int j = 0; j < INTEGER[i].length; j++) {
                    resultado += "[" + INTEGER[i][j] + "]";
                }
                resultado += "\n";
            }

            resultado += "FLOAT:\n";

            for (int i = 0; i < FLOAT.length; i++) {
                for (int j = 0; j < FLOAT[i].length; j++) {
                    resultado += "[" + FLOAT[i][j] + "]";
                }
                resultado += "\n";
            }

            resultado += "STRING:\n";

            for (int i = 0; i < STRING.length; i++) {
                for (int j = 0; j < STRING[i].length; j++) {
                    resultado += "[" + STRING[i][j] + "]";
                }
                resultado += "\n";
            }

            return resultado;
        }
    }
}
