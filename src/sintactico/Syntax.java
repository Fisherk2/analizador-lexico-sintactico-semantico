package sintactico;

import lenguaje.Gramatica;

import java.util.LinkedList;


/**
 * Clase que almacena las propiedades de un analizador sintactico.
 */
public class Syntax {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private final Gramatica GRAMATICA;
    private final LinkedList<FirstFollow> FIRSTS;
    private final LinkedList<FirstFollow> FOLLOWS;
    public final Integer[][] PREDICT;

    /**
     * Clase que almacena las propiedades de un analizador sintactico dependiendo de la gramatica configurada.
     *
     * @param gramatica Gramatica del lenguaje para el analisis sintactico.
     * @implNote El orden que conforma el conjunto de terminales y no terminales sera el mismo que los indices de la matriz predictiva.
     */
    public Syntax(Gramatica gramatica) {
        GRAMATICA = gramatica;
        FIRSTS = new LinkedList<>();
        FOLLOWS = new LinkedList<>();

        // ◂ ◂ ◂ ◂ Dimensionamos el tamaño de la gramatica dependiendo de los no terminales (Renglones) con los terminales incluyendo el $ (Columnas) ▸ ▸ ▸ ▸ //
        PREDICT = new Integer[GRAMATICA.N.length][GRAMATICA.T.length + 1];


        // ◂ ◂ ◂ ◂ Generacion de matriz ▸ ▸ ▸ ▸ //
        if (GRAMATICA.esValida()) {

            generarFirsts();

            if (hayVaciosEnFirsts()) {
                generarFollows();
            }


            generarPredict();

        } else {
            System.err.println("ERROR, NO SE GENERO UNA MATRIZ PREDICTIVA");
        }

    }

    @Override
    public String toString() {
        String resultado = "\n▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ SINTACTICO ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼ △ ▼\n";

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  GRAMATICA ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        resultado += GRAMATICA;

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  FIRSTS ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (FirstFollow first : FIRSTS) {
            resultado += "First" + first + "\n";
        }

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  FOLLOWS ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (FirstFollow follow : FOLLOWS) {
            resultado += "Follow" + follow + "\n";
        }

        resultado += "\n⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖  PREDICT ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖ ⧗ ⧖\n";

        for (String noTerminales : GRAMATICA.T) {
            resultado += "[" + noTerminales + "]";
        }

        resultado += "[$]\n";

        for (int i = 0; i < PREDICT.length; i++) {
            resultado += GRAMATICA.N[i] + ": ";
            for (int j = 0; j < PREDICT[i].length; j++) {
                resultado += "[" + PREDICT[i][j] + "]";
            }
            resultado += "\n";
        }

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GENERACION DE MATRIZ PREDICTIVA ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    private void generarFirsts() {

        // ◂ ◂ ◂ ◂ Agregamos los primeros de cada produccion, independientemente si son simbolos terminales, no terminales o vacios ▸ ▸ ▸ ▸ //
        for (Gramatica.Produccion produccion : GRAMATICA.P) {
            agregarFirsts(produccion, produccion.NUT[0]);
        }

        /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
        Si alpha = y1 y2 y3 y4 ... yk (Osea, son NO TERMINALES)

        Entonces:
        1) Añada el First(y1) al First(X)
        2) Añada el First(y2) al First(X) si y solo si el First(y1) tiene VACIO.
        .
        .
        .
        k-1) añada el First(yk) al First(X) si y solo si el First(y1) & First(y2)
        . . . & First(yk-1) tienen VACIO.
        k) Por último, añadir VACIO al First(X) si y solo si el First(y1) & First(y2)
        . . . & First(Yk) tienen VACIO.
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
         */

        // ◂ ◂ ◂ ◂ Variable para evitar la excepcion ConcurrentModificationException por modificacion y lectura paralela. ▸ ▸ ▸ ▸ //
        LinkedList<NuT> conjuntoSimbolos; //

        for (FirstFollow first : FIRSTS) {

            // ◂ ◂ ◂ ◂ Mientras contenga simbolos no terminales el conjunto First, seguira agregando simbolos producto de sus derivaciones. ▸ ▸ ▸ ▸ //
            while (contieneNoTerminales(first)) {

                conjuntoSimbolos = new LinkedList<>(first.CONJUNTO_SIMBOLOS);

                for (NuT x : conjuntoSimbolos) {

                    // ◂ ◂ ◂ ◂ Actualizamos el conjunto First(X) ▸ ▸ ▸ ▸ //
                    if (esNoTerminal(x.SIMBOLO)) {

                        // ◂ ◂ ◂ ◂ Eliminamos ese simbolo NO TERMINAL del conjunto original ▸ ▸ ▸ ▸ //
                        first.CONJUNTO_SIMBOLOS.remove(x);

                        // ◂ ◂ ◂ ◂ Agregamos su derivacion al final del conjunto original para que siga evaluando el First(X). ▸ ▸ ▸ ▸ //
                        for (NuT simboloDerivado : extraerFirst(x)) {

                            // ◂ ◂ ◂ ◂ Cuando se deriva el no terminal, agregamos los simbolos derivados pero asignando la id de la produccion actual ▸ ▸ ▸ ▸ //
                            first.agregarSimbolo(new NuT(x.ID, simboloDerivado.SIMBOLO));
                        }
                    }
                }
            }
        }
    }

    private void generarFollows() {
        /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
          A -> αBβ para alguna α y β, donde:
        * α Es el conjunto de simbolos NuT ANTES DE B
        * B No terminal que se busca en cada produccion para encontrar su Follow(B)
        * β Es el conjunto de simbolos Nut DESPUES DE B
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
         */

                 /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
        1.- Añadir el $ en el Follow(S), donde S es el símbolo sentencial de
        la gramática y $ es el símbolo que marca el fin de la entrada.
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
                */
        for (Gramatica.Produccion produccionA : GRAMATICA.P) {
            if (produccionA.N.equals(GRAMATICA.S)) {
                agregarFollows(produccionA, GRAMATICA.S, "$");
                break;
            }
        }

        String B;
        String firstBeta;

        for (Gramatica.Produccion A : GRAMATICA.P) {
            for (int i = 0; i < A.NUT.length; i++) {
                B = A.NUT[i];
                if (esNoTerminal(A.NUT[i])) {
                    if (i + 1 <= A.NUT.length - 1) {

                                       /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
        2.- Añadir el First(β) que sean un simbolo terminal.
        Si es un no terminal, añadimos el First(y) de ese no terminal.
        Si ese First(y) contiene vacios, no añadir ese vacio, en cambio, sustituyelo por Follow(A).
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
                                    */

                        firstBeta = A.NUT[i + 1];

                        // ◂ ◂ ◂ ◂ Añadir el First(β) que sean un simbolo terminal. ▸ ▸ ▸ ▸ //
                        if (!esNoTerminal(firstBeta)) {

                            agregarFollows(A, B, firstBeta);

                        } else {

                            // ◂ ◂ ◂ ◂ Si es un no terminal, añadimos el First(y) de ese no terminal. ▸ ▸ ▸ ▸ //
                            for (NuT simboloFirst : extraerFirst(new NuT(A.ID, firstBeta))) {
                                if (!simboloFirst.SIMBOLO.isEmpty()) {

                                    agregarFollows(A, B, simboloFirst.SIMBOLO);

                                } else {

                                    // ◂ ◂ ◂ ◂ Si ese First(y) contiene vacios, no añadir ese vacio, en cambio, sustituyelo por Follow(A). ▸ ▸ ▸ ▸ //
                                    for (NuT simboloFollow : extraerFollow(new NuT(A.ID, A.N))) {

                                        agregarFollows(A, B, simboloFollow.SIMBOLO);

                                    }
                                }
                            }
                        }

                    } else {

                         /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
        3.- Añadir el Follow(A) si A -> αB, osea, no existe β en la produccion
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
                                    */

                        for (NuT simboloFollow : extraerFollow(new NuT(A.ID, A.N))) {

                            agregarFollows(A, B, simboloFollow.SIMBOLO);

                        }
                    }
                }
            }
        }


                /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
        4 (Opcional).- Si dentro los conjuntos de follow, aun existen no terminales entonces:

        A = y1 y2 y3 y4 ... yk (Osea, son NO TERMINALES)

        Por lo tanto:
        1) Añada el Follow(y1) al Follow(A)
        2) Añada el Follow(y2) al Follow(A) si y solo si el Follow(y1) esta VACIO.
        .
        .
        .
        k-1) añada el Follow(yk) al Follow(A) si y solo si el Follow(y1) & Follow(y2)
        . . . & Follow(yk-1) esta VACIO.
        k) Por último, NO AÑADIR VACIO al Follow(A) si y solo si el Follow(y1) & Follow(y2)
        . . . & Follow(Yk) tienen VACIO.
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
         */

        // ◂ ◂ ◂ ◂ Variable para evitar la excepcion ConcurrentModificationException por modificacion y lectura paralela. ▸ ▸ ▸ ▸ //
        LinkedList<NuT> conjuntoSimbolos; //

        for (FirstFollow follow : FOLLOWS) {

            // ◂ ◂ ◂ ◂ Mientras contenga simbolos no terminales el conjunto Follow, seguira agregando simbolos producto de sus derivaciones. ▸ ▸ ▸ ▸ //
            while (contieneNoTerminales(follow)) {

                conjuntoSimbolos = new LinkedList<>(follow.CONJUNTO_SIMBOLOS);

                for (NuT A : conjuntoSimbolos) {

                    // ◂ ◂ ◂ ◂ Actualizamos el conjunto Follow(A) ▸ ▸ ▸ ▸ //
                    if (esNoTerminal(A.SIMBOLO)) {

                        // ◂ ◂ ◂ ◂ Eliminamos ese simbolo NO TERMINAL del conjunto original ▸ ▸ ▸ ▸ //
                        follow.CONJUNTO_SIMBOLOS.remove(A);

                        // ◂ ◂ ◂ ◂ Agregamos su derivacion al final del conjunto original para que siga evaluando el Follow(A). ▸ ▸ ▸ ▸ //
                        for (NuT simboloDerivado : extraerFollow(A)) {

                            // ◂ ◂ ◂ ◂ Cuando se deriva el no terminal, agregamos los simbolos derivados pero asignando la id de la produccion actual ▸ ▸ ▸ ▸ //
                            follow.agregarSimbolo(new NuT(A.ID, simboloDerivado.SIMBOLO));
                        }
                    }
                }
            }
        }
    }

    private void generarPredict() {
        /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
        Para cada produccion A -> alpha de la gramatica G
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
         */

        // ◂ ◂ ◂ ◂ Para cada simbolo terminal a en los First(alpha) ▸ ▸ ▸ ▸ //
        for (FirstFollow first : FIRSTS) {
            for (NuT terminal_a : first.CONJUNTO_SIMBOLOS) {

                // ◂ ◂ ◂ ◂ Añadir A -> alpha en M[A,a] ▸ ▸ ▸ ▸ //
                if (!terminal_a.SIMBOLO.isEmpty()) {

                    agregarMatriz(first.NO_TERMINAL, terminal_a.SIMBOLO, terminal_a.ID);

                } else {
                    // ◂ ◂ ◂ ◂ Si existe vacio dentro dentro de los First(alpha) ▸ ▸ ▸ ▸ //

                    // ◂ ◂ ◂ ◂ Para cada terminal b (b <> $) en el conjunto de Follow(A) ▸ ▸ ▸ ▸ //
                    for (NuT terminal_b : extraerFollow(new NuT(terminal_a.ID, first.NO_TERMINAL))) {

                        if (!terminal_b.SIMBOLO.equals("$")) {

                            // ◂ ◂ ◂ ◂ Añadir A -> alpha en M[A,b] ▸ ▸ ▸ ▸ //
                            agregarMatriz(first.NO_TERMINAL, terminal_b.SIMBOLO, terminal_a.ID);
                        } else {

                            // ◂ ◂ ◂ ◂ Si VACIO esta en los First(alpha) y el simbolo $ esta en los Follow(A) ▸ ▸ ▸ ▸ //

                            // ◂ ◂ ◂ ◂ Añadir A -> alpha en M[A,$] ▸ ▸ ▸ ▸ //
                            agregarMatriz(first.NO_TERMINAL, terminal_a.SIMBOLO, terminal_a.ID);
                        }
                    }
                }
            }
        }

        /*
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
        Para cada casilla que quedo vacia en la matriz
        ◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻◼◻
         */

        // ◂ ◂ ◂ ◂ Añadir M[A,0] donde las casillas esten vacias ▸ ▸ ▸ ▸ //
        for (int i = 0; i < PREDICT.length; i++) {
            for (int j = 0; j < PREDICT[i].length; j++) {
                if (PREDICT[i][j] == null) {
                    PREDICT[i][j] = 0;
                }
            }
        }
    }

    /**
     * Metodo que agrega First o simbolos del conjunto de ese First: A -> αBβ
     *
     * @param A           Produccion que se le va extraer su First.
     * @param first_Alpha El First de la produccion.
     */
    private void agregarFirsts(Gramatica.Produccion A, String first_Alpha) {

        if (hayFirstFollowDuplicado(FIRSTS, A.N)) {

            // ◂ ◂ ◂ ◂ Editamos el Follow de ese no terminal y agregamos nuevo simbolo NuT ▸ ▸ ▸ ▸ //
            for (FirstFollow first : FIRSTS) {
                if (first.NO_TERMINAL.equals(A.N)) {
                    first.agregarSimbolo(new NuT(A.ID, first_Alpha));
                    break;
                }
            }

        } else {

            // ◂ ◂ ◂ ◂ Creamos un nuevo conjunto First ▸ ▸ ▸ ▸ //
            FIRSTS.add(new FirstFollow(A.N, first_Alpha, A.ID));
        }
    }

    /**
     * Metodo que agrega Follows o simbolos del conjunto de ese Follow:  A -> αBβ
     *
     * @param A          Produccion que pertenece al Follow a extraer.
     * @param B          Simbolo no terminal que pertenece al Follow.
     * @param first_Beta El primero de beta: First(β).
     */
    private void agregarFollows(Gramatica.Produccion A, String B, String first_Beta) {

        if (hayFirstFollowDuplicado(FOLLOWS, B)) {

            // ◂ ◂ ◂ ◂ Editamos el Follow de ese no terminal y agregamos nuevo simbolo NuT ▸ ▸ ▸ ▸ //
            for (FirstFollow follow : FOLLOWS) {
                if (follow.NO_TERMINAL.equals(B)) {
                    follow.agregarSimbolo(new NuT(A.ID, first_Beta));
                    break;
                }
            }

        } else {

            // ◂ ◂ ◂ ◂ Creamos un nuevo conjunto Follow ▸ ▸ ▸ ▸ //
            FOLLOWS.add(new FirstFollow(B, first_Beta, A.ID));

        }

    }

    /**
     * Metodo que asigna un valor numerico a la matriz predictiva dependiendo del ID de produccion del simbolo: M[A,a]
     *
     * @param noTerminal_A     Simbolo no terminal A.
     * @param terminal_a       Simbolo terminal a.
     * @param id_de_produccion Identificador o numero de produccion que pertenece al simbolo terminal a.
     */
    private void agregarMatriz(String noTerminal_A, String terminal_a, int id_de_produccion) {

        int A = -1;
        int a = -1;

        // ◂ ◂ ◂ ◂ Buscar el no terminal A ▸ ▸ ▸ ▸ //
        for (int i = 0; i < GRAMATICA.N.length; i++) {
            if (GRAMATICA.N[i].equals(noTerminal_A)) {
                A = i;
                break;
            }
        }

        if (A < 0) {
            return;
        }

        // ◂ ◂ ◂ ◂ Buscar el terminal a ▸ ▸ ▸ ▸ //
        for (int i = 0; i < GRAMATICA.T.length; i++) {
            if (GRAMATICA.T[i].equals(terminal_a)) {
                a = i;
                break;
            }
        }

        // ◂ ◂ ◂ ◂ Si es simbolo $ de los Follows(A) ▸ ▸ ▸ ▸ //
        if (terminal_a.equals("$")) {

            // ◂ ◂ ◂ ◂ Añadir en M[A,$] (Se encuentra en la ultima columna) ▸ ▸ ▸ ▸ //
            PREDICT[A][PREDICT[A].length - 1] = id_de_produccion;

        } else {
            // ◂ ◂ ◂ ◂ Añadir en M[A,a] ▸ ▸ ▸ ▸ //
            PREDICT[A][a] = id_de_produccion;
        }
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ GETTERS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que devuelve el conjunto de First del simbolo no terminal.
     *
     * @param simbolo_no_terminal Simbolo Terminal o NO TERMINAL DE PREFERENCIA que pertenece a ese First.
     * @return Conjunto del First del simbolo no terminal, o solo una lista con un simbolo terminal.
     */
    private LinkedList<NuT> extraerFirst(NuT simbolo_no_terminal) {


        for (FirstFollow first : FIRSTS) {
            if (first.NO_TERMINAL.equals(simbolo_no_terminal.SIMBOLO)) {

                // ◂ ◂ ◂ ◂ Agregamos el conjunto del First(X) ▸ ▸ ▸ ▸ //
                return first.CONJUNTO_SIMBOLOS;
            }
        }

        LinkedList<NuT> simboloTerminal = new LinkedList<>();

        // ◂ ◂ ◂ ◂ Si se trata de derivar un Terminal, simplemente se devuelve asi mismo. ▸ ▸ ▸ ▸ //
        simboloTerminal.add(simbolo_no_terminal);

        return simboloTerminal;
    }

    /**
     * Funcion que extrae el conjunto de simbolos que pertenece al follow.
     *
     * @param simbolo_terminal Simbolo no terminal o TERMINAL DE PREFERENCIA que pertenece a ese follow.
     * @return Conjunto de simbolos perteneciente al follow, o solo una lista con un simbolo no terminal.
     */
    private LinkedList<NuT> extraerFollow(NuT simbolo_terminal) {

        // ◂ ◂ ◂ ◂ Agregamos los simbolos de ese Follow de ese no terminal ▸ ▸ ▸ ▸ //
        for (FirstFollow follow : FOLLOWS) {

            if (follow.NO_TERMINAL.equals(simbolo_terminal.SIMBOLO)) {
                return follow.CONJUNTO_SIMBOLOS;
            }

        }

        LinkedList<NuT> simboloNoTerminal = new LinkedList<>();

        // ◂ ◂ ◂ ◂ Si no encuentra ningun follow, devolvera el no terminal para que se derive mas tarde ▸ ▸ ▸ ▸ //
        simboloNoTerminal.add(simbolo_terminal);

        return simboloNoTerminal;
    }

    /**
     * Metodo que obtiene el simbolo inicial de la gramatica para analisis sintactico.
     *
     * @return Simbolo inicial (S).
     */
    public String getSimboloInicial() {
        return GRAMATICA.S;
    }

    /**
     * Funcion que devuelve el indice donde se ubica el simbolo no terminal en la matriz predictiva.
     *
     * @param simbolo_no_terminal Simbolo no terminal de la gramatica.
     * @return Indice donde se ubica en la matriz predictiva.
     * @implNote Si no se encuentra el simbolo no terminal en la gramatica, devolvera la primer posicion 0.
     */
    public int obtenerIndiceNoTerminal(String simbolo_no_terminal) {
        for (int i = 0; i < PREDICT.length; i++) {
            if (GRAMATICA.N[i].equals(simbolo_no_terminal)) {
                return i;
            }
        }

        return 0;
    }

    /**
     * Funcion que devuelve el indice donde se ubica el simbolo terminal en la matriz predictiva.
     *
     * @param simbolo_terminal Simbolo terminal de la gramatica.
     * @return Indice donde se ubica en la matriz predictiva.
     * @implNote Si no se encuentra el simbolo terminal en la gramatica, devolvera la ultima posicion n-1.
     */
    public int obtenerIndiceTerminal(String simbolo_terminal) {
        for (int j = 0; j < PREDICT[0].length; j++) {

            // ◂ ◂ ◂ ◂ El simbolo terminal $ que esta al final de la matriz no existe en el conjunto de terminales de la gramatica ▸ ▸ ▸ ▸ //
            if (j == PREDICT[0].length - 1) {
                if (simbolo_terminal.equals("$")) {
                    return j;
                }

            } else {
                if (GRAMATICA.T[j].equalsIgnoreCase(simbolo_terminal)) {
                    return j;
                }
            }
        }

        return PREDICT[0].length - 1;

    }

    /**
     * Funcion que regresa la produccion NuT dependiendo del identificador de produccion.
     *
     * @param id_produccion Numero de produccion de la gramatica.
     * @return Produccion NuT.
     */
    public String[] obtenerProduccion(Integer id_produccion) {

        for (Gramatica.Produccion produccion : GRAMATICA.P) {
            if (produccion.ID == id_produccion) {
                return produccion.NUT;
            }
        }

        return new String[]{""};
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VALIDACIONES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Funcion que verifica si hay vacios en los conjuntos de First.
     *
     * @return Contiene o no vacios en los conjuntos first.
     */
    private boolean hayVaciosEnFirsts() {

        for (FirstFollow first : FIRSTS) {
            for (NuT simbolo : first.CONJUNTO_SIMBOLOS) {

                // ◂ ◂ ◂ ◂ Si existen vacios en los conjuntos de first ▸ ▸ ▸ ▸ //
                if (simbolo.SIMBOLO.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Funcion que evalua si el conjunto del First(X) contiene o no terminales.
     *
     * @param firsts_o_follow Conjunto del First(X).
     * @return Tiene o no tiene terminales ese conjunto.
     */
    private boolean contieneNoTerminales(FirstFollow firsts_o_follow) {

        if (firsts_o_follow.CONJUNTO_SIMBOLOS.isEmpty()) {
            return false;
        }

        // ◂ ◂ ◂ ◂ Si contiene NO TERMINALES en el conjunto del First o Follow ▸ ▸ ▸ ▸ //
        for (NuT nut : firsts_o_follow.CONJUNTO_SIMBOLOS) {
            if (esNoTerminal(nut.SIMBOLO)) {
                return true;
            }
        }


        return false;
    }


    /**
     * Funcion que verifica si en el conjunto de First o Follows no halla duplicado de no terminales.
     *
     * @param conjunto_firsts_o_follows Conjunto de Firsts o Follows.
     * @param produccion                Produccion de la gramatica.
     * @return ¿Ya existe ese no terminal en el conjunto de First o Follow?.
     */
    private boolean hayFirstFollowDuplicado
    (LinkedList<FirstFollow> conjunto_firsts_o_follows, String produccion) {

        if (conjunto_firsts_o_follows.isEmpty()) {
            return false;
        }

        // ◂ ◂ ◂ ◂ Si existe en el conjunto de First o Follow ▸ ▸ ▸ ▸ //
        for (FirstFollow firstFollow : conjunto_firsts_o_follows) {
            if (firstFollow.NO_TERMINAL.equals(produccion)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Funcion que verifica que el simbolo ingresado es terminal.
     *
     * @param simbolo_NuT Simbolo terminal o no terminal.
     * @return Es o no simbolo terminal.
     */
    public boolean esNoTerminal(String simbolo_NuT) {

        for (String elemento : GRAMATICA.N) {
            if (elemento.equals(simbolo_NuT)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Funcion que verifica que el simbolo ingresado es terminal.
     *
     * @param simbolo_NuT Simbolo terminal o no terminal.
     * @return Es o no simbolo terminal.
     */
    public boolean esTerminal(String simbolo_NuT) {

        for (String elemento : GRAMATICA.T) {
            if (elemento.equals(simbolo_NuT)) {
                return true;
            }
        }

        return false;
    }
}
