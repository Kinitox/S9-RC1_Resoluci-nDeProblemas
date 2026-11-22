package negocio;

import java.util.ArrayList;
import modelo.Producto;

public class GestionInventario {
    private ArrayList<Producto> listaProductos;

    public GestionInventario() {
        this.listaProductos = new ArrayList<>();
    }

    public String registrarProducto(Producto nuevoProducto) {
        if (nuevoProducto.getCodigo() == null || nuevoProducto.getCodigo().trim().isEmpty()) {
            return "ERROR: El código del producto es obligatorio.";
        }
        if (nuevoProducto.getNombre() == null || nuevoProducto.getNombre().trim().isEmpty()) {
            return "ERROR: El nombre del producto es obligatorio.";
        }
        if (nuevoProducto.getPrecio() <= 0) {
            return "ERROR: El precio debe ser mayor a $0.00.";
        }
        if (nuevoProducto.getCantidad() < 0) {
            return "ERROR: La cantidad en stock no puede ser negativa.";
        }

        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(nuevoProducto.getCodigo())) {
                return "ERROR: Ya existe un producto registrado con el código '" + nuevoProducto.getCodigo() + "'.";
            }
        }

        listaProductos.add(nuevoProducto);
        return "ÉXITO: Producto '" + nuevoProducto.getNombre() + "' registrado correctamente en la peluquería.";
    }


    public void mostrarInventario() {
        if (listaProductos.isEmpty()) {
            System.out.println("\n El inventario de la peluquería está actualmente vacío.");
            return;
        }
        System.out.println("\n INVENTARIO PELUQUERÍA 'GLAMOUR & ESTILO' ");
        for (Producto p : listaProductos) {
            System.out.println(p.obtenerDetalle());
        }

        System.out.println("Valor total invertido en inventario: $" + String.format("%.2f", calcularCapitalTotal()));
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : listaProductos) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    public String actualizarStock(String codigo, int cambio) {
        Producto p = buscarProducto(codigo);
        if (p == null) {
            return "ERROR: No existe ningún producto con el código '" + codigo + "'.";
        }
        int nuevaCantidad = p.getCantidad() + cambio;
        if (nuevaCantidad < 0) {
            return "ERROR: Stock insuficiente. Stock actual: " + p.getCantidad() + " unidades.";
        }
        p.setCantidad(nuevaCantidad);
        return "ÉXITO: El stock de '" + p.getNombre() + "' ahora es de " + nuevaCantidad + " unidades.";
    }

    public String eliminarProducto(String codigo) {
        Producto p = buscarProducto(codigo);
        if (p == null) {
            return "ERROR: No existe ningún producto con el código '" + codigo + "'.";
        }
        listaProductos.remove(p);
        return "ÉXITO: Producto '" + p.getNombre() + "' eliminado del inventario.";
    }
    public void mostrarStockBajo() {
        System.out.println("\n PRODUCTOS CON STOCK BAJO");
        boolean hayAlguno = false;
        for (Producto p : listaProductos) {
            if (p.esStockBajo()) {
                System.out.println(p.obtenerDetalle());
                hayAlguno = true;
            }
        }
        if (!hayAlguno) {
            System.out.println("Ningún producto tiene stock bajo.");
        }
    }
    public void mostrarVencimientos() {
        System.out.println("\n PRODUCTOS VENCIDOS");
        boolean hayVencidos = false;
        for (Producto p : listaProductos) {
            if (p.estaVencido()) {
                System.out.println(p.obtenerDetalle());
                hayVencidos = true;
            }
        }
        if (!hayVencidos) {
            System.out.println("Ningún producto está vencido.");
        }

        System.out.println("\n PRODUCTOS QUE VENCEN EN LOS PRÓXIMOS 30 DÍAS");
        boolean hayPorVencer = false;
        for (Producto p : listaProductos) {
            if (p.venceProximamente(30)) {
                System.out.println(p.obtenerDetalle());
                hayPorVencer = true;
            }
        }
        if (!hayPorVencer) {
            System.out.println("Ningún producto vence en los próximos 30 días.");
        }
    }

    public double calcularCapitalTotal() {
        double total = 0;
        for (Producto p : listaProductos) {
            total += p.calcularValorTotal();
        }
        return total;
    }
}
