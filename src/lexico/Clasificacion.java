package lexico;

/**
 * Clase que almacena los atributos de la tabla de clasificacion lexica.
 */
public class Clasificacion {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ ATRIBUTOS ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    /**
     * Nombre o titulo de la clasificacion lexica.
     */
    public final String clasificacion;
    /**
     * Expresion regular que pertenece a esa clasificacion lexica.
     */
    public final String regex;
    /**
     * Atributo o propiedad que posee la clasificacion lexica.
     */
    public final int atributo;

    /**
     * Clase que almacena los atributos de la tabla de clasificacion lexica
     *
     * @param clasificacion Descripcion de que tipo de clasificacion lexica es.
     * @param regex         Expresion regular que representa esa clasificacion.
     * @param atributo      Propiedad numerica que pertenece esa clasificacion.
     */
    public Clasificacion(String clasificacion, String regex, int atributo) {
        this.clasificacion = clasificacion;
        this.regex = regex;
        this.atributo = atributo;
    }

    @Override
    public String toString() {
        return "Clasificacion: " + clasificacion + " --> [" + regex + "] [" + atributo + "]";
    }
}
