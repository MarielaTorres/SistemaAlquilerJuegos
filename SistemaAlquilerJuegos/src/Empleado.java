import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Empleado {
    private String nombre;
    private int idEmpleado;

    public Empleado(String nombre, int idEmpleado) {
        this.nombre = nombre;
        this.idEmpleado = idEmpleado;
    }
    public String getNombre() {
        return nombre;
    }

    public void atenderMesa(Mesa mesa, Scanner scanner) {
        System.out.println("Empleado " + nombre + " atendiendo la mesa " + mesa.getNumeroMesa());
        System.out.println("Seleccione productos para agregar al carrito (0 para finalizar):");

        List<Producto> menu = this.cargarProductosDesdeArchivo("C:\\Users\\MarielaTorres\\Desktop\\SistemaAlquilerJuegosFINALLLLLL\\SistemaAlquilerJuegos\\Productos.txt");

        // Separar productos por categoría
        List<Cafeteria> cafeteria = new ArrayList<>();
        List<Juego> juegos = new ArrayList<>();
        List<Pasteleria> pasteleria = new ArrayList<>();

        for (Producto p : menu) {
            if (p instanceof Cafeteria) {
                cafeteria.add((Cafeteria) p);
            } else if (p instanceof Juego) {
                juegos.add((Juego) p);
            } else if (p instanceof Pasteleria) {
                pasteleria.add((Pasteleria) p);
            }
        }

        // Imprimir menú formateado
        System.out.println("\nMenú:");
        System.out.printf("%-25s %-35s %-35s %n", "Cafetería ☕", "            Juegos 🎲", "        Pastelería 🧁");
        int maxItems = Math.max(cafeteria.size(), Math.max(juegos.size(), pasteleria.size()));

        for (int i = 0; i < maxItems; i++) {
            String cafe = i < cafeteria.size() ? String.format("%-3d %-20s $%-6.2f", cafeteria.get(i).getId(), cafeteria.get(i).getNombre(), cafeteria.get(i).getPrecio()) : "";
            String juego = i < juegos.size() ? String.format("| %-3d %-20s $%-6.2f", juegos.get(i).getId(), juegos.get(i).getNombre(), juegos.get(i).getPrecio()) : "";
            String postre = i < pasteleria.size() ? String.format("| %-3d %-20s $%-6.2f", pasteleria.get(i).getId(), pasteleria.get(i).getNombre(), pasteleria.get(i).getPrecio()) : "";
            System.out.println(cafe + juego + postre);
        }

        while (true) {
            System.out.print("Seleccione un producto por su número (0 para finalizar): ");
            int opcion = scanner.nextInt();
            if (opcion == 0) break;

            for (Producto p : menu) {
                if (p.getId() == opcion) {
                    if (p instanceof Cafeteria) {
                        System.out.print("¿Quiere su bebida caliente o fría? (1: Caliente, 0: Fría): ");
                        boolean temperatura = scanner.nextInt() == 1;
                        ((Cafeteria) p).setTemperatura(temperatura);
                    } else if (p instanceof Juego) {
                        Juego juegoSeleccionado = (Juego) p;
                        if (!juegoSeleccionado.isDisponible()) {
                            System.out.println("El juego " + p.getNombre() + " no está disponible en este momento.");
                            continue;
                        }
                        juegoSeleccionado.setDisponible(false);
                        juegoSeleccionado.actualizarDisponibilidadJuego();
                    }
                    mesa.agregarAlCarrito(p);
                    break;
                }
            }
        }

        mesa.getCarrito().mostrarCarrito();
        mesa.procesarPago(nombre);
    }

    public List<Producto> cargarProductosDesdeArchivo(String nombreArchivo) {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                double precio = Double.parseDouble(datos[2]);
                boolean disponible = datos.length > 3 && datos[3].equals("T");

                if (id >= 1 && id <= 10) {
                    productos.add(new Cafeteria(id, nombre, precio, true));
                } else if (id >= 11 && id <= 20) {
                    productos.add(new Juego(id, nombre, precio, disponible));
                } else if (id >= 21 && id <= 30) {
                    productos.add(new Pasteleria(id, nombre, precio));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de productos: " + e.getMessage());
        }
        return productos;
    }
}



