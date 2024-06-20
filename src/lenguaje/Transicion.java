package lenguaje;

public class Transicion {

    //▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼ VARIABLES ▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼▲▼//

    public final String estadoActual;
    public final String entrada;
    public final String[] estadosSiguientes;

    /**
     * Clase que almacena la transicion de un estado a otros de un automata finito no determinista generado en el archivo JSON.
     *
     * @param estadoActual      Estado de origen que se va transicionar.
     * @param entrada           Simbolo de entrada para transicionar a los siguientes automatas.
     * @param estadosSiguientes Estados destino despues de ingresar dicho simbolo.
     */
    public Transicion(String estadoActual,
                      String entrada,
                      String[] estadosSiguientes) {

        this.estadoActual = estadoActual;
        this.entrada = entrada;
        this.estadosSiguientes = estadosSiguientes;

    }
}
