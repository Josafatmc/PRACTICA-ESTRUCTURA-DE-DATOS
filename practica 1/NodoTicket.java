
public class NodoTicket {

    // Atributos.
    private Ticket ticket;
    private NodoTicket siguiente;

    // Constructor.
    public NodoTicket(Ticket ticket) {
        this.ticket = ticket;
        this.siguiente = null;
    }

    // Getters.
    public Ticket getTicket() {
        return ticket;
    }

    public NodoTicket getSiguiente() {
        return siguiente;
    }

    // Setters.
    public void setSiguiente(NodoTicket siguiente) {
        this.siguiente = siguiente;
    }
}