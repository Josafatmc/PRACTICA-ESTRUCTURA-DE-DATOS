import java.util.Scanner;

public class MenuSistema {

    // Constantes utilizadas para evitar numeros magicos en las validaciones.
    private static final int OPCION_SALIR_PRINCIPAL = 3;
    private static final int OPCION_SALIR_SUBMENU = 3;

    // Atributos.
    private final Scanner lector;
    private final ColaPrioridad ticketsPendientes;
    private final ListaEnlazadaSimple ticketsResueltos;

    // Constructor.
    public MenuSistema(Scanner lector) {
        this.lector = lector;
        this.ticketsPendientes = new ColaPrioridad();
        this.ticketsResueltos = new ListaEnlazadaSimple();
    }

    // Metodo principal del menu. Muestra las opciones generales del sistema
    // y dirige al usuario hacia el submenu correspondiente, hasta que se
    // elija la opcion de salir.
    public void iniciar() {
        System.out.println("========================================");
        System.out.println(" SISTEMA DE GESTION DE TICKETS");
        System.out.println("========================================");

        int opcion;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ingresar como Usuario");
            System.out.println("2. Ingresar como Administrador");
            System.out.println("3. Salir del programa");
            System.out.print("Seleccione una opcion: ");

            opcion = leerOpcion(1, OPCION_SALIR_PRINCIPAL);

            switch (opcion) {
                case 1:
                    menuUsuario();
                    break;
                case 2:
                    menuAdministrador();
                    break;
                case OPCION_SALIR_PRINCIPAL:
                    System.out.println("\nGracias por utilizar el sistema. Hasta pronto.");
                    break;
                default:
                    // No deberia alcanzarse gracias a la validacion de leerOpcion().
                    break;
            }
        } while (opcion != OPCION_SALIR_PRINCIPAL);
    }

    // ---------------------------------------------------------------
    // MENU DE USUARIO
    // ---------------------------------------------------------------
    private void menuUsuario() {
        int opcion;
        do {
            System.out.println("\n--- MENU DE USUARIO ---");
            System.out.println("1. Crear un ticket");
            System.out.println("2. Buscar un ticket resuelto");
            System.out.println("3. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            opcion = leerOpcion(1, OPCION_SALIR_SUBMENU);

            switch (opcion) {
                case 1:
                    crearTicket();
                    break;
                case 2:
                    buscarTicketResuelto();
                    break;
                case OPCION_SALIR_SUBMENU:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    break;
            }
        } while (opcion != OPCION_SALIR_SUBMENU);
    }

    private void crearTicket() {
        System.out.println("\n--- CREACION DE TICKET ---");

        System.out.print("Ingrese su nombre completo: ");
        String nombreCompleto = lector.nextLine().trim();
        while (nombreCompleto.isEmpty()) {
            System.out.print("El nombre no puede estar vacio. Ingrese su nombre completo: ");
            nombreCompleto = lector.nextLine().trim();
        }

        System.out.print("Describa el problema o solicitud: ");
        String descripcion = lector.nextLine().trim();
        while (descripcion.isEmpty()) {
            System.out.print("La descripcion no puede estar vacia. Describa el problema o solicitud: ");
            descripcion = lector.nextLine().trim();
        }

        Ticket nuevoTicket = new Ticket(descripcion, nombreCompleto);
        ticketsPendientes.insertar(nuevoTicket);

        System.out.println("\nEl ticket se creo exitosamente con el numero #" + nuevoTicket.getId() + ".");
        System.out.println("Puede utilizar este numero para consultar el estado de su ticket mas adelante.");
    }

    // Solicita un id y busca el ticket correspondiente en la lista de
    // tickets resueltos. Si no lo encuentra, indica que el ticket esta
    // pendiente (o que el id no existe).
    private void buscarTicketResuelto() {
        System.out.println("\n--- BUSQUEDA DE TICKET ---");
        int idBuscado = leerEntero("Ingrese el numero de ticket a consultar: ");

        Ticket ticketEncontrado = ticketsResueltos.buscarTicket(idBuscado);

        if (ticketEncontrado != null) {
            System.out.println("\nEl ticket fue encontrado y esta resuelto:");
            System.out.println(ticketEncontrado);
        } else {
            System.out.println("\nEl ticket #" + idBuscado
                    + " todavia esta pendiente de resolucion (o el numero no existe).");
        }
    }

    // ---------------------------------------------------------------
    // MENU DE ADMINISTRADOR
    // ---------------------------------------------------------------
    private void menuAdministrador() {
        int opcion;
        do {
            System.out.println("\n--- MENU DE ADMINISTRADOR ---");
            System.out.println("1. Ver el ticket al frente de la cola");
            System.out.println("2. Resolver el ticket al frente de la cola");
            System.out.println("3. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            opcion = leerOpcion(1, OPCION_SALIR_SUBMENU);

            switch (opcion) {
                case 1:
                    verFrenteCola();
                    break;
                case 2:
                    resolverTicket();
                    break;
                case OPCION_SALIR_SUBMENU:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    break;
            }
        } while (opcion != OPCION_SALIR_SUBMENU);
    }

    // Muestra el ticket que actualmente se encuentra al frente de la cola
    // de prioridad, sin retirarlo de la estructura.
    private void verFrenteCola() {
        System.out.println("\n--- TICKET AL FRENTE DE LA COLA ---");

        if (ticketsPendientes.estaVacia()) {
            System.out.println("No hay tickets pendientes en este momento.");
            return;
        }

        System.out.println(ticketsPendientes.verFrente());
    }

    // Resuelve el ticket que se encuentra al frente de la cola: establece
    // su fecha de resolucion, lo retira de la cola de prioridad y lo
    // inserta en la lista enlazada simple de tickets resueltos.
    private void resolverTicket() {
        System.out.println("\n--- RESOLUCION DE TICKET ---");

        if (ticketsPendientes.estaVacia()) {
            System.out.println("No hay tickets pendientes por resolver.");
            return;
        }

        Ticket ticketResuelto = ticketsPendientes.resolverFrente();
        ticketResuelto.resolver();
        ticketsResueltos.insertarTicket(ticketResuelto);

        System.out.println("El ticket #" + ticketResuelto.getId() + " fue resuelto exitosamente.");
        System.out.println(ticketResuelto);
    }

    // ---------------------------------------------------------------
    // METODOS AUXILIARES DE VALIDACION DE ENTRADA
    // ---------------------------------------------------------------

    // Lee una opcion de menu, validando que sea un numero entero dentro del
    // rango permitido. Si la entrada no es valida, vuelve a solicitarla sin
    // detener la ejecucion del programa.
    private int leerOpcion(int minimo, int maximo) {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                opcion = Integer.parseInt(lector.nextLine().trim());
                if (opcion >= minimo && opcion <= maximo) {
                    entradaValida = true;
                } else {
                    System.out.print("Opcion fuera de rango. Ingrese un numero entre "
                            + minimo + " y " + maximo + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada invalida. Por favor ingrese un numero: ");
            }
        }
        return opcion;
    }

    
    private int leerEntero(String mensaje) {
        int valor = -1;
        boolean entradaValida = false;

        System.out.print(mensaje);
        while (!entradaValida) {
            try {
                valor = Integer.parseInt(lector.nextLine().trim());
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.print("Entrada invalida. Ingrese unicamente numeros: ");
            }
        }
        return valor;
    }
}