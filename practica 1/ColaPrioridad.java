
public class ColaPrioridad {

   
    private NodoTicket frente;

    // Constructor.
    public ColaPrioridad() {
        frente = null;
    }

    // Indica si la cola de prioridad no contiene tickets.
    public boolean estaVacia() {
        return frente == null;
    }


    public void insertar(Ticket nuevoTicket) {
        NodoTicket nuevoNodo = new NodoTicket(nuevoTicket);

        // Caso 1: la cola esta vacia o el nuevo ticket tiene mas prioridad
        // que el que actualmente esta al frente.
        if (estaVacia() || nuevoTicket.getId() < frente.getTicket().getId()) {
            nuevoNodo.setSiguiente(frente);
            frente = nuevoNodo;
            return;
        }

        // Caso 2: se recorre la cola hasta encontrar el nodo despues del
        // cual debe insertarse el nuevo ticket.
        NodoTicket nodoActual = frente;
        while (nodoActual.getSiguiente() != null
                && nodoActual.getSiguiente().getTicket().getId() < nuevoTicket.getId()) {
            nodoActual = nodoActual.getSiguiente();
        }
        nuevoNodo.setSiguiente(nodoActual.getSiguiente());
        nodoActual.setSiguiente(nuevoNodo);
    }

    // Retorna el ticket que se encuentra al frente de la cola, sin
    // retirarlo de la estructura. Retorna null si la cola esta vacia.
    public Ticket verFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.getTicket();
    }

    // Retira de la cola y retorna el ticket que se encuentra al frente.
    // Retorna null si la cola esta vacia.
    public Ticket resolverFrente() {
        if (estaVacia()) {
            return null;
        }
        Ticket ticketResuelto = frente.getTicket();
        frente = frente.getSiguiente();
        return ticketResuelto;
    }
}