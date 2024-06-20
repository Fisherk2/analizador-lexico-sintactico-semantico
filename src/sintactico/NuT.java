package sintactico;


import java.util.Objects;

/**
 * Clase que almacena el par clave del id de produccion y el simbolo terminal o no terminal de dicha produccion.
 */
public class NuT {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Numero de produccion de la gramatica.
     */
    public final int ID;
    /**
     * Simbolo donde pertenece a esa produccion.
     */
    public final String SIMBOLO;

    /**
     * Clase que almacena el par clave del id de produccion y el simbolo terminal o no terminal de dicha produccion.
     *
     * @param id                     Identificador de la produccion que pertenece al terminal o no terminal.
     * @param terminal_o_no_terminal Simbolo Terminal, no terminal o vacio ε que pertenece al identificador de la produccion.
     */
    public NuT(int id, String terminal_o_no_terminal) {
        this.ID = id;
        this.SIMBOLO = terminal_o_no_terminal;
    }

    @Override
    public String toString() {
        if(SIMBOLO.isEmpty()){
            return "ε{"+ID+"}"; //Solo es representativo de los simbolos vacios
        }

        return SIMBOLO+"{"+ID+"}";
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ EQUALS POR SIMBOLO ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NuT nuT = (NuT) o;
        return Objects.equals(SIMBOLO, nuT.SIMBOLO);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(SIMBOLO);
    }
}

