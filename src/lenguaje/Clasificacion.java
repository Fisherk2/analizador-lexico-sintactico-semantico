package lenguaje;

import java.util.Objects;

/**
 * Clase que almacena los atributos de la tabla de clasificacion lexica.
 */
public class Clasificacion {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ ATRIBUTOS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Nombre o titulo de la clasificacion lexica.
     */
    public final String NAME;
    /**
     * Expresion regular que pertenece a esa clasificacion lexica.
     */
    public final String REGEX;
    /**
     * Atributo o propiedad que posee la clasificacion lexica.
     */
    public final int ATTRIBUTE;

    /**
     * Clase que almacena los atributos de la tabla de clasificacion lexica
     *
     * @param nombre   Descripcion de que tipo de clasificacion lexica es.
     * @param regex    Expresion regular que representa esa clasificacion.
     * @param atributo Propiedad numerica que pertenece esa clasificacion.
     */
    public Clasificacion(String nombre, String regex, int atributo) {
        this.NAME = nombre;
        this.REGEX = regex;
        this.ATTRIBUTE = atributo;
    }

    @Override
    public String toString() {
        return "Clasificacion: " + NAME + " --> [" + REGEX + "] [" + ATTRIBUTE + "]";
    }

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ EQUALS POR ATRIBUTO ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clasificacion that = (Clasificacion) o;
        return ATTRIBUTE == that.ATTRIBUTE;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ATTRIBUTE);
    }
}
