package lenguaje;


import java.util.Objects;

/**
 * Clase que almacena las propiedades de una gramatica para analisis sintactico.
 */
public class Gramatica {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Conjunto de simbolos no terminales
     */
    public final String[] N;
    /**
     * Conjunto de simbolos terminales
     */
    public final String[] T;
    /**
     * Simbolo inicial no terminal.
     */
    public final String S;
    /**
     * Producciones de la gramatica
     */
    public final Produccion[] P;


    /**
     * Clase que almacena las propiedades de una gramatica para analisis sintactico.
     *
     * @param n Conjunto de simbolos no terminales.
     * @param t Conjunto de simbolos terminales.
     * @param s Simbolo inicial no terminal.
     * @param p Producciones de la gramatica.
     */
    public Gramatica(String[] n, String[] t, String s, Produccion[] p) {
        N = n;
        T = t;
        S = s;
        P = p;
    }

    /**
     * Metodo que verifica que la gramatica esta bien configurada para usarse en el analizador sintactico.
     *
     * @return Es o no esta configurado apropiadamente.
     */
    public boolean esValida() {
        int contador;
        boolean encontrado;

        // ◂ ◂ ◂ ◂ Verificar si N y T son un conjunto de simbolos ▸ ▸ ▸ ▸ //
        if (N.length == 0 | T.length == 0) {
            System.err.println("N y T NO estan vacios");
            return false;
        }

        for (String simbolo : N) {
            contador = 0;
            for (String otroSimbolo : N) {
                if (simbolo.equals(otroSimbolo)) {
                    contador++;
                }
                if (contador > 1) {
                    System.err.println("N NO es un conjunto de simbolos, tiene duplicados con [" + simbolo + "]");
                    return false;
                }
            }
        }

        for (String simbolo : T) {
            contador = 0;
            for (String otroSimbolo : T) {
                if (simbolo.equals(otroSimbolo)) {
                    contador++;
                }
                if (contador > 1) {
                    System.err.println("T NO es un conjunto de simbolos, tiene duplicados con [" + simbolo + "]");
                    return false;
                }
            }
        }

        // ◂ ◂ ◂ ◂ Verificar si los ID son irrepetibles ▸ ▸ ▸ ▸ //
        for (Produccion produccion : P) {
            contador = 0; //Por cada id, se reinicia el contador
            for (Produccion otraProduccion : P) {
                if (produccion.ID == otraProduccion.ID) {
                    contador++;
                }
                if (contador > 1) {
                    System.err.println("IDs de produccion tiene duplicados con [" + produccion.ID + "]");
                    return false;
                }
            }
        }

        // ◂ ◂ ◂ ◂ Verificar si existe el simbolo inicial en el conjunto de no terminales ▸ ▸ ▸ ▸ //
        encontrado = false;
        for (String noTerminal : N) {
            if (noTerminal.equals(S)) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.err.println("El simbolo inicial [" + S + "] no esta declarado en el conjunto de no terminales");
            return false;
        }

        // ◂ ◂ ◂ ◂ Verificar si NO existe el simbolo inicial en el lado derecho de la produccion ▸ ▸ ▸ ▸ //
        for (Produccion produccion : P) {
            for (String simbolo : produccion.NUT) {
                if (simbolo.equals(S)) {
                    System.err.println("Existe el simbolo inicial en el lado derecho de la produccion [" + produccion.N + "]");
                    return false;
                }
            }
        }

        // ◂ ◂ ◂ ◂ Verificar si esta declarado el simbolo inicial no terminal en el lado izquierdo de la produccion ▸ ▸ ▸ ▸ //
        encontrado = false;
        for (Produccion produccion : P) {
            if (produccion.N.equals(S)) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.err.println("El simbolo inicial no terminal [" + S + "] no esta declarado en el lado izquierdo de la producion");
            return false;
        }

        // ◂ ◂ ◂ ◂ Verificar si existen todos los no terminales del lado izquierdo de la produccion ▸ ▸ ▸ ▸ //
        encontrado = false;
        for (String noTerminal : N) {
            for (Produccion produccion : P) {
                if (noTerminal.equals(produccion.N)) {
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.err.println("Falta simbolos no terminales del lado izquierdo de la produccion: " + noTerminal);
                return false;
            }
        }

        // ◂ ◂ ◂ ◂ Verificar si existen todos los terminales del lado derecho de la produccion ▸ ▸ ▸ ▸ //
        encontrado = false;
        for (String terminal : T) {
            for (Produccion produccion : P) {
                for (String nut : produccion.NUT) {
                    if (nut.equals(terminal)) {
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    System.err.println("Falta simbolos terminales del lado derecho de la produccion: " + terminal);
                    return false;
                }
            }
        }

        return true;
    }


    @Override
    public String toString() {

        String resultado = "N = ";

        for (String n : N) {
            resultado += "[" + n + "]";
        }

        resultado += "\nT = ";

        for (String t : T) {
            resultado += "[" + t + "]";
        }

        resultado += "\nS = " + S + "\nP = ";

        for (Produccion p : P) {
            resultado += "\n" + p;
        }

        return resultado;
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ CLASES INTERNAS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Clase interna que contiene las propiedades de una produccion gramatical.
     */
    public static class Produccion {

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

        /**
         * Numero de produccion gramatical.
         */
        public final int ID;
        /**
         * Simbolo no terminal
         */
        public final String N;
        /**
         * Produccion que contiene simbolos terminales y/o no terminales ó simbolo vacio.
         */
        public final String[] NUT;

        /**
         * Clase interna que contiene las propiedades de una produccion gramatical.
         *
         * @param id                  Numero de produccion gramatica.
         * @param simbolo_no_terminal Simbolo no terminal que deriva a la produccion.
         * @param simbolos_NuT        Produccion que contiene simbolos terminales y/o no terminales ó simbolo vacio.
         */
        public Produccion(int id, String simbolo_no_terminal, String[] simbolos_NuT) {
            ID = id;
            N = simbolo_no_terminal;
            NUT = simbolos_NuT;
        }

        @Override
        public String toString() {
            String produccion = ID + ".- " + N + " ->";

            for (String nut : NUT) {
                if (nut.isEmpty()) {
                    produccion += " ε"; //Solo representativo para los simbolos vacios.
                } else {
                    produccion += " " + nut;
                }
            }

            return produccion;
        }

        //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ EQUALS PARA ID DE LA PRODUCCION ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Produccion that = (Produccion) o;
            return ID == that.ID;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(ID);
        }
    }
}
