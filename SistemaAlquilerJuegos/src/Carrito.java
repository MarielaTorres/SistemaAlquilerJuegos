import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Producto> productos;

    public Carrito() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }

    public void mostrarCarrito() {
        System.out.println("Contenido del carrito:");
        for (Producto p : productos) {
            p.mostrarDetalles();
        }
        System.out.println("Total: $" + calcularTotal());
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
