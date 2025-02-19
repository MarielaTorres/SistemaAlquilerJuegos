import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Juego extends Producto {
    private boolean disponible;

    public Juego(int id, String nombre, double precio, boolean disponible) {
        super(id, nombre, precio);
        this.disponible = disponible;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Juego: " + getNombre() + " - Precio: $" + getPrecio() + " - Disponible: " + (disponible ? "Sí" : "No"));
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void actualizarDisponibilidadJuego() {  // Hacer el metodo público y tomar valores internos
        File archivo = new File("C:\\Users\\MarielaTorres\\Desktop\\SistemaAlquilerJuegosFINALLLLLL\\SistemaAlquilerJuegos\\Productos.txt");
        List<String> lineasActualizadas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (Integer.parseInt(datos[0]) == this.getId()) {  // Usar this.getId()
                    datos[3] = this.disponible ? "T" : "F";  // Usar this.disponible
                }
                lineasActualizadas.add(String.join(";", datos));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de productos: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineasActualizadas) {
                bw.write(linea + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo de productos: " + e.getMessage());
        }
    }
}



