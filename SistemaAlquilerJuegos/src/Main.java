import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nombre para iniciar sesión: ");
        String nombreEmpleado = scanner.nextLine();
        Empleado empleado = new Empleado(nombreEmpleado, 1);
        List<Mesa> mesas = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            mesas.add(new Mesa(i));
        }

        System.out.println("-------------☕☕☕Bienvenido al sistema de gestión de la cafetería, 🤵" + nombreEmpleado + "☕☕☕-------------");

        while (true) {
            System.out.println("\nOpciones disponibles:");
            System.out.println("1. Atender una mesa  😀");
            System.out.println("2. Salir del sistema  ❌");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero(scanner);

            if (opcion == 2) {
                System.out.println("Cerrando el sistema...");
                break;
            } else if (opcion == 1) {
                System.out.println("Mesas disponibles:");
                for (Mesa mesa : mesas) {
                    System.out.println("Mesa " + mesa.getNumeroMesa());
                }

                System.out.print("Ingrese el número de la mesa a atender: ");
                int numMesa = leerEntero(scanner);

                Mesa mesaSeleccionada = null;
                for (Mesa mesa : mesas) {
                    if (mesa.getNumeroMesa() == numMesa) {
                        mesaSeleccionada = mesa;
                        break;
                    }
                }

                if (mesaSeleccionada != null) {
                    empleado.atenderMesa(mesaSeleccionada, scanner);
                    //Facturacion.generarTicket(mesaSeleccionada, nombreEmpleado);
                } else {
                    System.out.println("Número de mesa inválido.");
                }
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static int leerEntero(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Error: ha ingresado un texto. Solo se admiten opciones numéricas.");
                scanner.next();
                System.out.print("Intente nuevamente: ");
            }
        }
    }
}

