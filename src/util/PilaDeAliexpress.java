package util;

public class PilaDeAliexpress {

    // Nodo de la pila
    private class Nodo {
        String dato;
        Nodo siguiente;

        Nodo(String dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        @Override
        public String toString() {
            return dato;
        }
    }

    private Nodo cima;

    // inicializar la pila vacía
    public PilaDeAliexpress() {

        this.cima = null;
    }

    // vaciar la pila
    public void vaciar() {
        this.cima = null;
    }

    // agregar un elemento a la pila
    public void push(String dato) {
        Nodo nuevoNodo = new Nodo(dato);
        nuevoNodo.siguiente = cima;
        cima = nuevoNodo;
    }

    // remover y retornar el elemento en la cima de la pila
    public void pop() {
        if (empty()) {
            return;
        }
        String dato = cima.dato;
        cima = cima.siguiente;
    }

    //  retornar el elemento en la cima de la pila sin removerlo
    public String peek() {
        if (empty()) {
            return "";
        }
        return cima.dato;
    }

    // Método para verificar si la pila está vacía
    public boolean empty() {
        return cima == null;
    }

    @Override
    public String toString() {
        String resultado = "☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲ PILA DE ALIEXPRESS ☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲☱☲☴☲";

        Nodo actual = cima;
        while (actual != null) {

            resultado += "\n"+actual;
            actual = actual.siguiente;
        }
        return resultado;
    }
}

  
