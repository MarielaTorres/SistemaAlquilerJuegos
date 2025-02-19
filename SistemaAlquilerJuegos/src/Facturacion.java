import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Facturacion {

    // Método estático para generar el ticket
    public static void generarTicket(Mesa mesa, String nombreEmpleado) {
        try (FileWriter writer = new FileWriter("ticket_mesa_" + mesa.getNumeroMesa() + ".txt")) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            writer.write("Factura - Mesa " + mesa.getNumeroMesa() + "\n");
            writer.write("Atendido por: " + nombreEmpleado + "\n");
            writer.write("Fecha y hora: " + formattedDateTime + "\n");

            for (Producto p : mesa.getCarrito().getProductos()) {
                writer.write(p.getNombre() + " - Precio: $" + p.getPrecio() + "\n");
            }
            writer.write("Total: $" + mesa.getCarrito().calcularTotal() + "\n");

            System.out.println("Ticket generado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al generar el ticket: " + e.getMessage());
        }
    }
}



