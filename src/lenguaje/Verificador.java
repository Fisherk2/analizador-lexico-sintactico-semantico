package lenguaje;

/**
 * Clase que almacena la tabla de verificacion de datos de una constante o variable.
 */
public class Verificador {

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
        String resultado = "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  TABLA DE VERIFICACION DE DATOS ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

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
