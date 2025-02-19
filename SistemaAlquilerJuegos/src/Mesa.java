public class Mesa {
    private int numeroMesa;
    private Carrito carrito;

    public Mesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.carrito = new Carrito();
    }

    public void agregarAlCarrito(Producto producto) {
        carrito.agregarProducto(producto);
    }

    public void procesarPago(String nombreEmpleado) {
        System.out.println("Procesando pago para Mesa " + numeroMesa);
        System.out.println("Total a pagar: $" + carrito.calcularTotal());
        Facturacion.generarTicket(this, nombreEmpleado); // ✅ Llamada estática corregida
    }


    public Carrito getCarrito() {
        return carrito;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }
}
