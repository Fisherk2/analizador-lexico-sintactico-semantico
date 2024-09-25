package lenguaje;

/**
 * Clase que almacena los simbolos representativos de los operadores aritmeticos, de asignacion, comparacion y logicas
 * de una sentencia que pertenezcan al lenguaje.
 */
public class Operadores {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    // ◂ ◂ ◂ ◂ Aritmetica ▸ ▸ ▸ ▸ //
    public final String ADDITION;
    public final String SUBTRACTION;
    public final String MULTIPLICATION;
    public final String DIVISION;
    public final String EXPONENTIATION;
    public final String MODULO;
    public final String INTEGER_DIVISION;

    // ◂ ◂ ◂ ◂ Asignacion ▸ ▸ ▸ ▸ //
    public final String ASIGN;
    public final String ADD_ASIGN;
    public final String SUB_ASIGN;
    public final String MULT_ASIGN;
    public final String DIV_ASIGN;
    public final String MOD_ASIGN;
    public final String INT_DIV_ASIGN;

    // ◂ ◂ ◂ ◂ Comparacion ▸ ▸ ▸ ▸ //
    public final String EQUAL_TO;
    public final String LESS_THAN;
    public final String GREATER_THAN;
    public final String LESS_EQUAL_TO;
    public final String GREATER_EQUAL_TO;
    public final String NOT_EQUAL_TO;

    // ◂ ◂ ◂ ◂ Logica ▸ ▸ ▸ ▸ //
    public final String AND;
    public final String OR;
    public final String NOT;

    /**
     * Clase que almacena los simbolos representativos de los operadores aritmeticos, de asignacion, comparacion y logicas
     * de una sentencia que pertenezcan al lenguaje.
     *
     * @param suma            Simbolo aritmetico que representa la suma en el lenguaje.
     * @param resta           Simbolo aritmetico que representa la resta en el lenguaje.
     * @param multiplicacion  Simbolo aritmetico que representa la multiplicacion en el lenguaje.
     * @param division        Simbolo aritmetico que representa la division en el lenguaje.
     * @param exponenciacion  Simbolo aritmetico que representa la exponenciacion en el lenguaje.
     * @param modulo          Simbolo aritmetico que representa el modulo en el lenguaje.
     * @param division_entera Simbolo aritmetico que representa la division entera en el lenguaje.
     * @param asignacion      Simbolo que representa la asignacion de memoria en el lenguaje.
     * @param suma_asigna     Simbolo que representa en el lenguaje la suma de un valor antes de asignarse en memoria a si mismo.
     * @param resta_asigna    Simbolo que representa en el lenguaje la resta de un valor antes de asignarse en memoria a si mismo.
     * @param mult_asigna     Simbolo que representa en el lenguaje la multiplicacion de un valor antes de asignarse en memoria a si mismo.
     * @param div_asigna      Simbolo que representa en el lenguaje la division de un valor antes de asignarse en memoria a si mismo.
     * @param mod_asigna      Simbolo que representa en el lenguaje el modulo de un valor antes de asignarse en memoria a si mismo.
     * @param div_int_asigna  Simbolo que representa en el lenguaje la division entera de un valor antes de asignarse en memoria a si mismo.
     * @param igual_que       Simbolo que representa en el lenguaje la igualdad entre dos valores.
     * @param menor_que       Simbolo que representa en el lenguaje el menor de un valor.
     * @param mayor_que       Simbolo que representa en el lenguaje el mayor de un valor.
     * @param menor_igual_que Simbolo que representa en el lenguaje el menor o igual de un valor.
     * @param mayor_igual_que Simbolo que representa en el lenguaje el mayor o igual de un valor.
     * @param diferente_que   Simbolo que representa en el lenguaje si es diferente a un valor.
     * @param and             Simbolo que representa en el lenguaje la compuerta logica AND.
     * @param or              Simbolo que representa en el lenguaje la compuerta logica OR.
     * @param not             Simbolo que representa en el lenguaje la compuerta logica NOT.
     */
    public Operadores(String suma,
                      String resta,
                      String multiplicacion,
                      String division,
                      String exponenciacion,
                      String modulo,
                      String division_entera,
                      String asignacion,
                      String suma_asigna,
                      String resta_asigna,
                      String mult_asigna,
                      String div_asigna,
                      String mod_asigna,
                      String div_int_asigna,
                      String igual_que,
                      String menor_que,
                      String mayor_que,
                      String menor_igual_que,
                      String mayor_igual_que,
                      String diferente_que,
                      String and,
                      String or,
                      String not
    ) {
        this.ADDITION = suma;
        this.SUBTRACTION = resta;
        this.MULTIPLICATION = multiplicacion;
        this.DIVISION = division;
        this.EXPONENTIATION = exponenciacion;
        this.MODULO = modulo;
        this.INTEGER_DIVISION = division_entera;
        this.ASIGN = asignacion;
        this.ADD_ASIGN = suma_asigna;
        this.SUB_ASIGN = resta_asigna;
        this.MULT_ASIGN = mult_asigna;
        this.DIV_ASIGN = div_asigna;
        this.MOD_ASIGN = mod_asigna;
        this.INT_DIV_ASIGN = div_int_asigna;
        this.EQUAL_TO = igual_que;
        this.LESS_THAN = menor_que;
        this.GREATER_THAN = mayor_que;
        this.LESS_EQUAL_TO = menor_igual_que;
        this.GREATER_EQUAL_TO = mayor_igual_que;
        this.NOT_EQUAL_TO = diferente_que;
        this.AND = and;
        this.OR = or;
        this.NOT = not;
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
}
