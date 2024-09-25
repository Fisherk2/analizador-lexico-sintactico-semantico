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
     * @param CHECK_DATA Tabla de verificacion de datos entre constantes y variables.
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
        public final String[][] INTEGER;
        /**
         * Cruces entre tipos de datos que dan como resultado un tipo de dato flotante.
         */
        public final String[][] FLOAT;
        /**
         * Cruces entre tipos de datos que dan como resultado un tipo de dato cadena de texto.
         */
        public final String[][] STRING;

        /**
         * Clase que almacena la tabla de verificacion de datos de una constante o variable.
         *
         * @param INTEGER Pares de cruces donde el resultado dara un tipo de dato entero.
         * @param FLOAT   Pares de cruces donde el resultado dara un tipo de dato flotante.
         * @param STRING  Pares de cruces donde el resultado dara un tipo de dato cadena de texto.
         */
        public Verificador(String[][] INTEGER, String[][] FLOAT, String[][] STRING) {
            this.INTEGER = INTEGER;
            this.FLOAT = FLOAT;
            this.STRING = STRING;
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
