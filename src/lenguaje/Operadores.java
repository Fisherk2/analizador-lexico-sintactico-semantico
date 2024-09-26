package lenguaje;

/**
 * Clase que almacena los simbolos representativos de los operadores aritmeticos, de asignacion, comparacion y logicas
 * de una sentencia que pertenezcan al lenguaje.
 */
public class Operadores {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    // ◂ ◂ ◂ ◂ Aritmetica ▸ ▸ ▸ ▸ //
    public final int ADDITION;
    public final int SUBTRACTION;
    public final int MULTIPLICATION;
    public final int DIVISION;
    public final int EXPONENTIATION;
    public final int MODULO;
    public final int INTEGER_DIVISION;
    private final int[] SIGNOS_ARITMETICOS;

    // ◂ ◂ ◂ ◂ Asignacion ▸ ▸ ▸ ▸ //
    public final int ASIGN;
    public final int ADD_ASIGN;
    public final int SUB_ASIGN;
    public final int MULT_ASIGN;
    public final int DIV_ASIGN;
    public final int MOD_ASIGN;
    public final int INT_DIV_ASIGN;
    private final int[] SIGNOS_ASIGNACION;

    // ◂ ◂ ◂ ◂ Comparacion ▸ ▸ ▸ ▸ //
    public final int EQUAL_TO;
    public final int LESS_THAN;
    public final int GREATER_THAN;
    public final int LESS_EQUAL_TO;
    public final int GREATER_EQUAL_TO;
    public final int NOT_EQUAL_TO;
    private final int[] SIGNOS_COMPARACION;

    // ◂ ◂ ◂ ◂ Logica ▸ ▸ ▸ ▸ //
    public final int AND;
    public final int OR;
    public final int NOT;
    private final int[] SIGNOS_LOGICOS;

    /**
     * Clase que almacena los simbolos representativos de los operadores aritmeticos, de asignacion, comparacion y logicas
     * de una sentencia que pertenezcan al lenguaje, basados en la columna de atributos de la tabla de clasificacion lexica.
     *
     * @param id_suma            Simbolo aritmetico que representa la suma en el lenguaje.
     * @param id_resta           Simbolo aritmetico que representa la resta en el lenguaje.
     * @param id_multiplicacion  Simbolo aritmetico que representa la multiplicacion en el lenguaje.
     * @param id_division        Simbolo aritmetico que representa la division en el lenguaje.
     * @param id_exponenciacion  Simbolo aritmetico que representa la exponenciacion en el lenguaje.
     * @param id_modulo          Simbolo aritmetico que representa el modulo en el lenguaje.
     * @param id_division_entera Simbolo aritmetico que representa la division entera en el lenguaje.
     * @param id_asignacion      Simbolo que representa la asignacion de memoria en el lenguaje.
     * @param id_suma_asigna     Simbolo que representa en el lenguaje la suma de un valor antes de asignarse en memoria a si mismo.
     * @param id_resta_asigna    Simbolo que representa en el lenguaje la resta de un valor antes de asignarse en memoria a si mismo.
     * @param id_mult_asigna     Simbolo que representa en el lenguaje la multiplicacion de un valor antes de asignarse en memoria a si mismo.
     * @param id_div_asigna      Simbolo que representa en el lenguaje la division de un valor antes de asignarse en memoria a si mismo.
     * @param id_mod_asigna      Simbolo que representa en el lenguaje el modulo de un valor antes de asignarse en memoria a si mismo.
     * @param id_div_int_asigna  Simbolo que representa en el lenguaje la division entera de un valor antes de asignarse en memoria a si mismo.
     * @param id_igual_que       Simbolo que representa en el lenguaje la igualdad entre dos valores.
     * @param id_menor_que       Simbolo que representa en el lenguaje el menor de un valor.
     * @param id_mayor_que       Simbolo que representa en el lenguaje el mayor de un valor.
     * @param id_menor_igual_que Simbolo que representa en el lenguaje el menor o igual de un valor.
     * @param id_mayor_igual_que Simbolo que representa en el lenguaje el mayor o igual de un valor.
     * @param id_diferente_que   Simbolo que representa en el lenguaje si es diferente a un valor.
     * @param id_and             Simbolo que representa en el lenguaje la compuerta logica AND.
     * @param id_or              Simbolo que representa en el lenguaje la compuerta logica OR.
     * @param id_not             Simbolo que representa en el lenguaje la compuerta logica NOT.
     * @implNote Si en tu lenguaje no existe algun simbolo operacional, debes asignarlo como CERO = 0.
     */
    public Operadores(int id_suma,
                      int id_resta,
                      int id_multiplicacion,
                      int id_division,
                      int id_exponenciacion,
                      int id_modulo,
                      int id_division_entera,
                      int id_asignacion,
                      int id_suma_asigna,
                      int id_resta_asigna,
                      int id_mult_asigna,
                      int id_div_asigna,
                      int id_mod_asigna,
                      int id_div_int_asigna,
                      int id_igual_que,
                      int id_menor_que,
                      int id_mayor_que,
                      int id_menor_igual_que,
                      int id_mayor_igual_que,
                      int id_diferente_que,
                      int id_and,
                      int id_or,
                      int id_not
    ) {
        this.ADDITION = id_suma;
        this.SUBTRACTION = id_resta;
        this.MULTIPLICATION = id_multiplicacion;
        this.DIVISION = id_division;
        this.EXPONENTIATION = id_exponenciacion;
        this.MODULO = id_modulo;
        this.INTEGER_DIVISION = id_division_entera;
        this.ASIGN = id_asignacion;
        this.ADD_ASIGN = id_suma_asigna;
        this.SUB_ASIGN = id_resta_asigna;
        this.MULT_ASIGN = id_mult_asigna;
        this.DIV_ASIGN = id_div_asigna;
        this.MOD_ASIGN = id_mod_asigna;
        this.INT_DIV_ASIGN = id_div_int_asigna;
        this.EQUAL_TO = id_igual_que;
        this.LESS_THAN = id_menor_que;
        this.GREATER_THAN = id_mayor_que;
        this.LESS_EQUAL_TO = id_menor_igual_que;
        this.GREATER_EQUAL_TO = id_mayor_igual_que;
        this.NOT_EQUAL_TO = id_diferente_que;
        this.AND = id_and;
        this.OR = id_or;
        this.NOT = id_not;

        SIGNOS_ARITMETICOS = new int[]{ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, EXPONENTIATION, MODULO, INTEGER_DIVISION};
        SIGNOS_ASIGNACION = new int[]{ASIGN, ADD_ASIGN, SUB_ASIGN, MULT_ASIGN, DIV_ASIGN, MOD_ASIGN, INT_DIV_ASIGN};
        SIGNOS_COMPARACION = new int[]{EQUAL_TO, LESS_THAN, GREATER_THAN, LESS_EQUAL_TO, GREATER_EQUAL_TO, NOT_EQUAL_TO};
        SIGNOS_LOGICOS = new int[]{AND, OR, NOT};

    }

    @Override
    public String toString() {
        return "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ OPERADORES ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n"
                + "\nAritmeticos: [" + ADDITION + "][" + SUBTRACTION + "][" + MULTIPLICATION + "][" + DIVISION + "][" + EXPONENTIATION + "][" + MODULO + "][" + INTEGER_DIVISION + "]"
                + "\nAsignacion: [" + ASIGN + "][" + ADD_ASIGN + "][" + SUB_ASIGN + "][" + MULT_ASIGN + "][" + DIV_ASIGN + "][" + MOD_ASIGN + "][" + INT_DIV_ASIGN + "]"
                + "\nComparacion: [" + EQUAL_TO + "][" + LESS_THAN + "][" + GREATER_THAN + "][" + LESS_EQUAL_TO + "][" + GREATER_EQUAL_TO + "][" + NOT_EQUAL_TO + "]"
                + "\nLogica:[" + AND + "][" + OR + "][" + NOT + "]"
                ;
    }

    /**
     * Funcion que verifica si el simbolo ingresado es un signo aritmetico.
     *
     * @param atributo Atributo que pertenece de la tabla de clasificacion lexica.
     * @return ¿Es un signo aritmetico?
     */
    public boolean esSignoAritmetico(int atributo) {

        for (int signo : SIGNOS_ARITMETICOS) {
            if (signo == atributo) {
                return true;
            }
        }

        return false;
    }

    /**
     * Funcion que verifica si el simbolo ingresado es un signo de asignacion de memoria.
     *
     * @param atributo Atributo que pertenece de la tabla de clasificacion lexica.
     * @return ¿Es un signo de asignacion?
     */
    public boolean esSignoAsignacion(int atributo) {

        for (int signo : SIGNOS_ASIGNACION) {
            if (signo == atributo) {
                return true;
            }
        }

        return false;
    }

    /**
     * Funcion que verifica si el simbolo ingresado es un signo de comparacion entre dos variables o constantes.
     *
     * @param atributo Atributo que pertenece de la tabla de clasificacion lexica.
     * @return ¿Es un signo de comparacion?
     */
    public boolean esSignoComparacion(int atributo) {

        for (int signo : SIGNOS_COMPARACION) {
            if (signo == atributo) {
                return true;
            }
        }

        return false;
    }

    /**
     * Funcion que verifica si el simbolo ingresado es un signo logico o booleano.
     *
     * @param atributo Atributo que pertenece de la tabla de clasificacion lexica.
     * @return ¿Es un signo booleano o logico?
     */
    public boolean esSignoLogico(int atributo) {

        for (int signo : SIGNOS_LOGICOS) {
            if (signo == atributo) {
                return true;
            }
        }

        return false;
    }

}
