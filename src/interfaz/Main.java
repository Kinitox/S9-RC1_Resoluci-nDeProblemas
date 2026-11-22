package interfaz;

import java.util.Scanner;
import modelo.Producto;
import modelo.ProductoNoPerecedero;
import modelo.ProductoPerecedero;
import negocio.GestionInventario;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionInventario inventario = new GestionInventario();
        int opcion = 0;

        do {
            System.out.println("\n--- SISTEMA DE INVENTARIO: PELUQUERÍA GLAMOUR & ESTILO ---");
            System.out.println("1. Registrar Insumo Químico / Perecedero (Tintes, Shampoos, etc.)");
            System.out.println("2. Registrar Herramienta / Equipo (Secadores, Tijeras, etc.)");
            System.out.println("3. Ver Inventario Completo y Valor Total");
            System.out.println("4. Buscar Producto por Código");
            System.out.println("5. Actualizar Stock (entrada / salida)");
            System.out.println("6. Eliminar Producto");
            System.out.println("7. Reporte de Stock Bajo");
            System.out.println("8. Reporte de Productos Vencidos / Por Vencer");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Ingresa un número del 1 al 9.");
                continue;
            }

            switch (opcion) {
                case 1:
                case 2:
                    System.out.print("Ingrese el Código (ej. TIN-01 o SEC-01): ");
                    String codigo = scanner.nextLine();

                    System.out.print("Ingrese Nombre del Producto: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ingrese Precio Unitario: ");
                    String precioStr = scanner.nextLine();

                    System.out.print("Ingrese Cantidad en Stock: ");
                    String cantidadStr = scanner.nextLine();

                    if (precioStr.trim().isEmpty() || cantidadStr.trim().isEmpty()) {
                        System.out.println("ERROR: El precio y la cantidad son campos obligatorios.");
                        break;
                    }

                    try {
                        double precio = Double.parseDouble(precioStr);
                        int cantidad = Integer.parseInt(cantidadStr);

                        if (opcion == 1) {
                            System.out.print("Ingrese la fecha de vencimiento (DD/MM/AAAA): ");
                            String fecha = scanner.nextLine();
                            if (fecha.trim().isEmpty()) {
                                System.out.println("ERROR: La fecha de vencimiento es obligatoria para productos perecederos.");
                                break;
                            }

                            if (!ProductoPerecedero.esFechaValida(fecha)) {
                                System.out.println("ERROR: Fecha inválida. Use el formato DD/MM/AAAA con una fecha real (ej. 15/08/2027).");
                                break;
                            }
                            ProductoPerecedero pp = new ProductoPerecedero(codigo, nombre, precio, cantidad, fecha.trim());
                            System.out.println(inventario.registrarProducto(pp));
                        } else {
                            System.out.print("Ingrese Categoría (ej. Eléctricos, Corte, Peinado): ");
                            String categoria = scanner.nextLine();
                            if (categoria.trim().isEmpty()) {
                                System.out.println("ERROR: La categoría es obligatoria para herramientas.");
                                break;
                            }
                            ProductoNoPerecedero pnp = new ProductoNoPerecedero(codigo, nombre, precio, cantidad, categoria);
                            System.out.println(inventario.registrarProducto(pnp));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: El precio y la cantidad deben ser valores numéricos válidos.");
                    }
                    break;

                case 3:
                    inventario.mostrarInventario();
                    break;

                case 4:
                    System.out.print("Ingrese el código del producto a buscar: ");
                    String codBuscar = scanner.nextLine();
                    Producto encontrado = inventario.buscarProducto(codBuscar);
                    if (encontrado != null) {
                        System.out.println("Resultado: " + encontrado.obtenerDetalle());
                    } else {
                        System.out.println("No se encontró ningún producto con el código: " + codBuscar);
                    }
                    break;


                case 5:
                    System.out.print("Ingrese el código del producto: ");
                    String codStock = scanner.nextLine();
                    System.out.print("Ingrese la cantidad a sumar (ej. 5) o restar (ej. -2): ");
                    String cambioStr = scanner.nextLine();

                    if (cambioStr.trim().isEmpty()) {
                        System.out.println("ERROR: La cantidad es obligatoria.");
                        break;
                    }
                    try {
                        int cambio = Integer.parseInt(cambioStr.trim());
                        System.out.println(inventario.actualizarStock(codStock, cambio));
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: La cantidad debe ser un número entero válido.");
                    }
                    break;

                case 6:
                    System.out.print("Ingrese el código del producto a eliminar: ");
                    String codEliminar = scanner.nextLine();
                    System.out.println(inventario.eliminarProducto(codEliminar));
                    break;

                case 7:
                    inventario.mostrarStockBajo();
                    break;

                case 8:
                    inventario.mostrarVencimientos();
                    break;

                case 9:
                    System.out.println("Cerrando sistema de inventario. ¡SEE U!");
                    break;

                default:
                    System.out.println("ERROR: Opción no reconocida.");
            }
        } while (opcion != 9);

        scanner.close();
    }
}

