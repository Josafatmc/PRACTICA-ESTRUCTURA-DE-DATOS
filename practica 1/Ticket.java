import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Ticket {

    
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Contador estatico que permite generar un id unico e irrepetible para
    // cada ticket que se cree durante la ejecucion del programa.
    private static int cantidad = 0;

    // Atributos.
    private final int id;
    private String descripcion;
    private String nombreCompleto;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaResolucion;

    // Constructor.
    public Ticket(String descripcion, String nombreCompleto) {
        cantidad++;
        this.id = cantidad;
        this.descripcion = descripcion;
        this.nombreCompleto = nombreCompleto;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaResolucion = null;
    }

    // Getters.
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public boolean estaResuelto() {
        return fechaResolucion != null;
    }

    // Marca el ticket como resuelto, estableciendo su fecha de resolucion
    public void resolver() {
        this.fechaResolucion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String textoResolucion = estaResuelto()
                ? fechaResolucion.format(FORMATO_FECHA)
                : "Pendiente";

        return "----------------------------------------\n"
                + "Ticket #" + id + "\n"
                + "Descripcion: " + descripcion + "\n"
                + "Solicitado por: " + nombreCompleto + "\n"
                + "Fecha de creacion: " + fechaCreacion.format(FORMATO_FECHA) + "\n"
                + "Fecha de resolucion: " + textoResolucion + "\n"
                + "----------------------------------------";
    }
}