package modelo;

import java.time.LocalDate;


public class ProductoPerecedero extends Producto {
    private String fechaVencimiento; // formato DD/MM/AAAA

    public ProductoPerecedero(String codigo, String nombre, double precio, int cantidad, String fechaVencimiento) {
        super(codigo, nombre, precio, cantidad);
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public static LocalDate convertirFecha(String texto) {
        try {
            String[] partes = texto.trim().split("/");
            if (partes.length != 3) {
                return null;
            }
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);
            return LocalDate.of(anio, mes, dia);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean esFechaValida(String texto) {
        return convertirFecha(texto) != null;
    }

    public boolean estaVencido() {
        LocalDate fecha = convertirFecha(fechaVencimiento);
        if (fecha == null) {
            return false;
        }
        return fecha.isBefore(LocalDate.now());
    }


    @Override
    public boolean venceProximamente(int dias) {
        LocalDate fecha = convertirFecha(fechaVencimiento);
        if (fecha == null) {
            return false;
        }
        LocalDate hoy = LocalDate.now();
        return !fecha.isBefore(hoy) && !fecha.isAfter(hoy.plusDays(dias));
    }

    @Override
    public String obtenerDetalle() {
        String estado = "";
        if (estaVencido()) {
            estado = " [¡VENCIDO!]";
        } else if (venceProximamente(30)) {
            estado = " [Por vencer]";
        }
        return super.obtenerDetalle() + " | Tipo: Químico/Perecedero | Vence: " + fechaVencimiento + estado;
    }
}
