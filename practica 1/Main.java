import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
      
        Scanner lector = new Scanner(System.in);

        MenuSistema sistema = new MenuSistema(lector);
        sistema.iniciar();

        lector.close();
    }
}