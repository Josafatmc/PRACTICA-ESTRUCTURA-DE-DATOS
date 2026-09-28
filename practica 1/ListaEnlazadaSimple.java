
public class ListaEnlazadaSimple {

    // Unico atributo: referencia al primer nodo de la lista.
    private NodoTicket primero;

    // Constructor.
    public ListaEnlazadaSimple() {
        primero = null;
    }

    // Indica si la lista no contiene tickets resueltos.
    public boolean estaVacia() {
        return primero == null;
    }

    // Inserta un nuevo ticket resuelto al inicio de la lista.
    public void insertarTicket(Ticket ticket) {
        NodoTicket nuevoNodo = new NodoTicket(ticket);
        nuevoNodo.setSiguiente(primero);
        primero = nuevoNodo;
    }

    // Busca un ticket en la lista a partir de su id.
    // Retorna el ticket si lo encuentra, o null si no esta en la lista
    
    public Ticket buscarTicket(int idBuscado) {
        NodoTicket nodoActual = primero;
        while (nodoActual != null && nodoActual.getTicket().getId() != idBuscado) {
            nodoActual = nodoActual.getSiguiente();
        }
        if (nodoActual == null) {
            return null;
        }
        return nodoActual.getTicket();
    }
}