package lenguaje;

import java.util.Objects;

/**
 * Clase que contiene las propiedades de una produccion gramatical.
 */
public class ProduccionGramatical {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Numero de produccion gramatical.
     */
    public final int id;
    /**
     * Simbolo no terminal
     */
    public final String N;
    /**
     * Produccion que contiene simbolos terminales y/o no terminales ó simbolo vacio.
     */
    public final String[] NuT;

    /**
     * Clase que contiene las propiedades de una produccion gramatical.
     *
     * @param id  Numero de produccion gramatica.
     * @param n   Simbolo no terminal que deriva a la produccion.
     * @param nuT Produccion que contiene simbolos terminales y/o no terminales ó simbolo vacio.
     */
    public ProduccionGramatical(int id, String n, String[] nuT) {
        this.id = id;
        N = n;
        NuT = nuT;
    }

    @Override
    public String toString() {
        String produccion = id + ".- " + N + " ->";

        for (String nut : NuT) {
            if (nut.isEmpty()){
                produccion += " ε"; //Solo representativo para los simbolos vacios.
            }else {
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
        ProduccionGramatical that = (ProduccionGramatical) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
