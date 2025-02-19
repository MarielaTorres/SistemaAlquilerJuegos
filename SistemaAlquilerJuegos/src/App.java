import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class App {
    private JFrame frame;
    private JTextArea displayTextArea;
    private JButton siguienteButton;
    private JTextField inputField;
    private int estado = 0;
    private Empleado empleado;
    private Mesa mesa;
    private Carrito carrito;
    private List<Producto> menu;

    public App() {
        frame = new JFrame("Cafetería");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());

        displayTextArea = new JTextArea();
        displayTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputField = new JTextField(10);
        siguienteButton = new JButton("Siguiente");

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarEntrada(inputField.getText());
            }
        });

        inputPanel.add(inputField);
        inputPanel.add(siguienteButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        mostrarMensaje("Ingrese su nombre para iniciar sesión:");

        frame.setVisible(true);
    }

    private void mostrarMensaje(String mensaje) {
        displayTextArea.append(mensaje + "\n");
    }

    private void procesarEntrada(String entrada) {
        switch (estado) {
            case 0:
                empleado = new Empleado(entrada, 1);
                mostrarMensaje("-------------☕☕☕Bienvenido al sistema de gestión de la cafetería, 🤵" + entrada + "☕☕☕-------------");
                mostrarMensaje("Opciones disponibles:\n1. Atender una mesa 😀\n2. Salir del sistema ❌\nSeleccione una opción:");
                estado = 1;
                break;
            case 1:
                if (entrada.equals("1")) {
                    mostrarMensaje("Mesas disponibles:\nMesa 1\nMesa 2\nMesa 3\nMesa 4\nMesa 5\nIngrese el número de la mesa a atender:");
                    estado = 2;
                } else if (entrada.equals("2")) {
                    mostrarMensaje("Cerrando el sistema...");
                    System.exit(0);
                } else {
                    mostrarMensaje("Opción inválida. Intente nuevamente:");
                }
                break;
            case 2:
                try {
                    int numMesa = Integer.parseInt(entrada);
                    mesa = new Mesa(numMesa);
                    carrito = new Carrito();
                    menu = empleado.cargarProductosDesdeArchivo("productos.txt");
                    mostrarMensaje("Empleado " + empleado.getNombre() + " atendiendo la mesa " + numMesa);
                    mostrarMensaje("Seleccione productos para agregar al carrito (0 para finalizar):\n" + formatearMenu());
                    estado = 3;
                } catch (NumberFormatException e) {
                    mostrarMensaje("Número de mesa inválido. Intente nuevamente:");
                }
                break;
            case 3:
                try {
                    int idProducto = Integer.parseInt(entrada);
                    if (idProducto == 0) {
                        mostrarMensaje("Contenido del carrito:");
                        for (Producto p : carrito.getProductos()) {
                            mostrarMensaje(p.getNombre() + " - Precio: $" + p.getPrecio());
                        }
                        mostrarMensaje("Total: $" + carrito.calcularTotal());
                        mostrarMensaje("Procesando pago para Mesa " + mesa.getNumeroMesa());
                        Facturacion.generarTicket(mesa, empleado.getNombre());
                        mostrarMensaje("Ticket generado correctamente.");
                        estado = 1;
                        return;
                    }

                    for (Producto p : menu) {
                        if (p.getId() == idProducto) {
                            if (p instanceof Cafeteria) {
                                mostrarMensaje("¿Quiere su bebida caliente o fría? (1: Caliente, 0: Fría):");
                                estado = 4;
                            } else {
                                carrito.agregarProducto(p);
                                mostrarMensaje("Producto agregado: " + p.getNombre());
                                mostrarMensaje("Seleccione otro producto (0 para finalizar):");
                            }
                            return;
                        }
                    }
                    mostrarMensaje("Producto no encontrado. Intente nuevamente:");
                } catch (NumberFormatException e) {
                    mostrarMensaje("Entrada inválida. Ingrese un número de producto o 0 para finalizar:");
                }
                break;
            case 4:
                if (entrada.equals("1") || entrada.equals("0")) {
                    boolean caliente = entrada.equals("1");
                    ((Cafeteria) carrito.getProductos().get(carrito.getProductos().size() - 1)).setTemperatura(caliente);
                    mostrarMensaje("Temperatura establecida. Seleccione otro producto (0 para finalizar):");
                    estado = 3;
                } else {
                    mostrarMensaje("Entrada inválida. Ingrese 1 para caliente o 0 para frío:");
                }
                break;
        }
    }

    private String formatearMenu() {
        StringBuilder sb = new StringBuilder();
        for (Producto p : menu) {
            sb.append(p.getId()).append(" - ").append(p.getNombre()).append(" - $").append(p.getPrecio()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new App();
    }
}
