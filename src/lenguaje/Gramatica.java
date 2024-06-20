package lenguaje;


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
    public final ProduccionGramatical[] P;


    /**
     * Clase que almacena las propiedades de una gramatica para analisis sintactico.
     *
     * @param n Conjunto de simbolos no terminales.
     * @param t Conjunto de simbolos terminales.
     * @param s Simbolo inicial no terminal.
     * @param p Producciones de la gramatica.
     */
    public Gramatica(String[] n, String[] t, String s, ProduccionGramatical[] p) {
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
        for (ProduccionGramatical produccion : P) {
            contador = 0; //Por cada id, se reinicia el contador
            for (ProduccionGramatical otraProduccion : P) {
                if (produccion.id == otraProduccion.id) {
                    contador++;
                }
                if (contador > 1) {
                    System.err.println("IDs de produccion tiene duplicados con [" + produccion.id + "]");
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
        for (ProduccionGramatical produccion : P) {
            for (String simbolo : produccion.NuT) {
                if (simbolo.equals(S)) {
                    System.err.println("Existe el simbolo inicial en el lado derecho de la produccion [" + produccion.N + "]");
                    return false;
                }
            }
        }

        // ◂ ◂ ◂ ◂ Verificar si esta declarado el simbolo inicial no terminal en el lado izquierdo de la produccion ▸ ▸ ▸ ▸ //
        encontrado = false;
        for (ProduccionGramatical produccion : P) {
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
            for (ProduccionGramatical produccion : P) {
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
            for (ProduccionGramatical produccion : P) {
                for (String nut : produccion.NuT) {
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

        for (ProduccionGramatical p : P) {
            resultado += "\n" + p;
        }

        return resultado;
    }

}
