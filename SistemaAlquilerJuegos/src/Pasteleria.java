public class Pasteleria extends Producto {
    public Pasteleria(int id,String nombre, double precio) {
        super(id,nombre, precio);
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Pastelería: " + getNombre() + " | Precio: $" + getPrecio());
    }
}
