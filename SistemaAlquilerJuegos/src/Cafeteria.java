public class Cafeteria extends Producto {
    private boolean temperatura;

    public Cafeteria(int id, String nombre, double precio, boolean temperatura) {
        super(id, nombre, precio);
        this.temperatura = temperatura;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Cafetería: " + getNombre() + " | Precio: $" + getPrecio() + " | Temperatura: " + (temperatura ? "Caliente" : "Frío"));
    }

    public boolean isTemperatura() {
        return temperatura;
    }

    public void setTemperatura(boolean temperatura) {
        this.temperatura = temperatura;
    }
}

