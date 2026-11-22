package modelo;

public class ProductoNoPerecedero extends Producto {
    private String categoria;

    public ProductoNoPerecedero(String codigo, String nombre, double precio, int cantidad, String categoria) {
        super(codigo, nombre, precio, cantidad);
        this.categoria = categoria;
    }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String obtenerDetalle() {
        return super.obtenerDetalle() + " | Tipo: Herramienta/Equipo | Categoría: " + categoria;
    }
}
