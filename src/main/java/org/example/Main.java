package org.example;

import org.example.dao.*;
import org.example.model.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static ProveedorDAO proveedorDAO = new ProveedorDAO();
    static ProductoDAO productoDAO = new ProductoDAO();
    static ClienteDAO clienteDAO = new ClienteDAO();
    static ClientePromocionDAO clientePromocionDAO = new ClientePromocionDAO();
    static PromocionDAO promocionDAO = new PromocionDAO();
    static ProductoPromocionDAO productoPromocionDAO = new ProductoPromocionDAO();

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Ingresar Proveedor");
            System.out.println("2. Ingresar Producto (con Proveedor)");
            System.out.println("3. Ingresar Cliente");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> ingresarProveedor();
                case 2 -> ingresarProducto();
                case 3 -> ingresarCliente();
                case 4 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opcion invalida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    // ─────────────────────────────────────────────
    // 1. INGRESAR PROVEEDOR
    // ─────────────────────────────────────────────
    static void ingresarProveedor() {
        System.out.println("\n--- Ingresar Proveedor ---");
        Proveedor p = new Proveedor();

        System.out.print("Nombre: ");
        p.setNombre(scanner.nextLine());

        System.out.print("Telefono: ");
        p.setTelefono(scanner.nextLine());

        System.out.print("Correo: ");
        p.setCorreo(scanner.nextLine());

        System.out.print("Ciudad: ");
        p.setCiudad(scanner.nextLine());

        boolean guardado = proveedorDAO.insertar(p);
        if (guardado) {
            System.out.println("Proveedor registrado exitosamente.");
        } else {
            System.out.println("Error al registrar el proveedor.");
        }
    }

    // ─────────────────────────────────────────────
    // 2. INGRESAR PRODUCTO (requiere proveedor)
    // ─────────────────────────────────────────────
    static void ingresarProducto() {
        System.out.println("\n--- Ingresar Producto ---");

        List<Proveedor> proveedores = proveedorDAO.listarTodos();
        if (proveedores == null || proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados. Registre uno primero.");
            return;
        }

        System.out.println("Proveedores disponibles:");
        for (Proveedor prov : proveedores) {
            System.out.println("  ID: " + prov.getIdProveedor() + " | Nombre: " + prov.getNombre());
        }

        System.out.print("Ingrese el ID del proveedor: ");
        int idProveedor = leerEntero();

        Proveedor proveedorSeleccionado = proveedorDAO.buscarPorId(idProveedor);
        if (proveedorSeleccionado == null) {
            System.out.println("El proveedor con ID " + idProveedor + " no existe.");
            return;
        }

        Producto producto = new Producto();
        producto.setIdProveedor(idProveedor);

        System.out.print("Nombre del producto: ");
        producto.setNombre(scanner.nextLine());

        System.out.print("Descripcion: ");
        producto.setDescripcion(scanner.nextLine());

        System.out.print("Precio: ");
        producto.setPrecio(leerDouble());

        System.out.print("Stock: ");
        producto.setStock(leerEntero());

        boolean guardado = productoDAO.insertar(producto);
        if (!guardado) {
            System.out.println("Error al registrar el producto.");
            return;
        }
        System.out.println("Producto registrado con proveedor: " + proveedorSeleccionado.getNombre());

        // ── Promocion del proveedor para este producto ──
        System.out.print("\n¿Este producto viene con alguna promocion del proveedor? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            registrarPromocionProducto(producto);
        }
    }

    // ─────────────────────────────────────────────
    // REGISTRAR PROMOCION DE UN PRODUCTO
    // ─────────────────────────────────────────────
    static void registrarPromocionProducto(Producto producto) {
        System.out.println("\n--- Tipo de Promocion del Producto ---");
        System.out.println("1. Descuento en precio  (ej: 10% de descuento)");
        System.out.println("2. Unidades adicionales (ej: lleva 3 paga 2)");
        System.out.println("3. Ambas                (descuento + unidades extra)");
        System.out.print("Seleccione el tipo: ");
        int tipo = leerEntero();

        if (tipo < 1 || tipo > 3) {
            System.out.println("Opcion invalida. No se registro ninguna promocion.");
            return;
        }

        Promocion promocion = new Promocion();

        System.out.print("Nombre de la promocion (ej: 'Promo Octubre'): ");
        String nombreBase = scanner.nextLine();

        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        try {
            promocion.setFechaInicio(java.sql.Date.valueOf(scanner.nextLine()));
        } catch (IllegalArgumentException e) {
            promocion.setFechaInicio(new java.sql.Date(System.currentTimeMillis()));
            System.out.println("Formato invalido, se usa la fecha de hoy.");
        }

        System.out.print("Fecha de fin    (YYYY-MM-DD): ");
        try {
            promocion.setFechaFin(java.sql.Date.valueOf(scanner.nextLine()));
        } catch (IllegalArgumentException e) {
            System.out.println("Fecha de fin omitida o formato invalido.");
        }

        double descuentoPct = 0;
        String detalle = "";

        if (tipo == 1) {
            System.out.print("Porcentaje de descuento (ej: 15 para 15%): ");
            descuentoPct = leerDouble();
            detalle = "Descuento: " + (int) descuentoPct + "%";
            System.out.printf(">> %.0f%% de descuento en \"%s\".%n", descuentoPct, producto.getNombre());

        } else if (tipo == 2) {
            System.out.print("¿Cuantas unidades LLEVA el cliente? (ej: 3 en 3x2): ");
            int lleva = leerEntero();
            System.out.print("¿Cuantas unidades PAGA el cliente?  (ej: 2 en 3x2): ");
            int paga = leerEntero();
            descuentoPct = 0;
            detalle = lleva + "x" + paga;
            System.out.printf(">> Promocion %dx%d en \"%s\".%n", lleva, paga, producto.getNombre());

        } else {
            System.out.print("Porcentaje de descuento (ej: 10 para 10%): ");
            descuentoPct = leerDouble();
            System.out.print("¿Cuantas unidades LLEVA el cliente?: ");
            int lleva = leerEntero();
            System.out.print("¿Cuantas unidades PAGA el cliente?: ");
            int paga = leerEntero();
            detalle = "Descuento: " + (int) descuentoPct + "% + " + lleva + "x" + paga;
            System.out.printf(">> Promocion combinada: %.0f%% + %dx%d en \"%s\".%n",
                    descuentoPct, lleva, paga, producto.getNombre());
        }

        promocion.setNombre(nombreBase + " | " + detalle);
        promocion.setDescuentoPct(descuentoPct);

        boolean promoGuardada = promocionDAO.insertar(promocion);
        if (!promoGuardada) {
            System.out.println("Error al guardar la promocion.");
            return;
        }

        // *ADVERTENCIA*: Asegúrate de que promocionDAO.insertar() actualice el ID
        // del objeto 'promocion' tras el INSERT en la base de datos.
        ProductoPromocion pp = new ProductoPromocion();
        pp.setIdProducto(producto.getIdProducto());
        pp.setIdPromocion(promocion.getIdPromocion());

        boolean vinculado = productoPromocionDAO.insertar(pp);
        if (vinculado) {
            System.out.println("Promocion [" + promocion.getNombre() + "] vinculada al producto.");
        } else {
            System.out.println("Advertencia: promocion guardada pero no vinculada al producto.");
        }
    }

    // ─────────────────────────────────────────────
    // 3. INGRESAR CLIENTE (con descuento por volumen)
    // ─────────────────────────────────────────────
    static void ingresarCliente() {
        System.out.println("\n--- Ingresar Cliente ---");
        Cliente cliente = new Cliente();

        System.out.print("Nombre: ");
        cliente.setNombre(scanner.nextLine());

        System.out.print("Correo: ");
        cliente.setCorreo(scanner.nextLine());

        System.out.print("Telefono: ");
        cliente.setTelefono(scanner.nextLine());

        boolean guardado = clienteDAO.insertar(cliente);
        if (!guardado) {
            System.out.println("Error al registrar el cliente.");
            return;
        }
        System.out.println("Cliente registrado exitosamente.");

        // OJO: Si tu clienteDAO no devuelve el ID autogenerado, búscalo aquí:
        // cliente = clienteDAO.buscarPorCorreo(cliente.getCorreo());

        System.out.print("\n¿Desea registrar productos para este cliente? (s/n): ");
        if (!scanner.nextLine().equalsIgnoreCase("s")) return;

        List<Producto> productos = productoDAO.listarTodos();
        if (productos == null || productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }

        System.out.println("\nProductos disponibles:");
        for (Producto prod : productos) {
            System.out.printf("  ID: %d | Nombre: %-20s | Precio: $%.2f%n",
                    prod.getIdProducto(), prod.getNombre(), prod.getPrecio());
        }

        int cantidadProductos = 0;
        double totalSinDescuento = 0;
        String continuar = "s";

        while (continuar.equalsIgnoreCase("s")) {
            System.out.print("ID del producto a agregar: ");
            int idProducto = leerEntero();

            Producto prod = productoDAO.buscarPorId(idProducto);
            if (prod == null) {
                System.out.println("Producto no encontrado.");
            } else {
                cantidadProductos++;
                totalSinDescuento += prod.getPrecio();
                System.out.printf("Agregado: \"%s\" | Total: %d producto(s) / $%.2f%n",
                        prod.getNombre(), cantidadProductos, totalSinDescuento);
            }

            System.out.print("¿Agregar otro producto? (s/n): ");
            continuar = scanner.nextLine();
        }

        double descuento = calcularDescuento(cantidadProductos);
        double totalConDescuento = totalSinDescuento * (1 - descuento);

        System.out.println("\n--- Resumen de Compra ---");
        System.out.println("Productos comprados : " + cantidadProductos);
        System.out.printf("Total sin descuento : $%.2f%n", totalSinDescuento);
        if (descuento > 0) {
            System.out.printf("Descuento aplicado  : %.0f%% (por volumen)%n", descuento * 100);
            System.out.printf("Total con descuento : $%.2f%n", totalConDescuento);
            aplicarPromocionCliente(cliente, descuento);
        } else {
            System.out.println("Sin descuento (necesita mas de 3 productos).");
        }
    }

    // ─────────────────────────────────────────────
    // CALCULO DE DESCUENTO POR VOLUMEN
    // ─────────────────────────────────────────────
    static double calcularDescuento(int cantidad) {
        if (cantidad > 6) return 0.20; // 20%
        if (cantidad > 3) return 0.15; // 15%
        return 0.0;
    }

    // ─────────────────────────────────────────────
    // APLICAR PROMOCION AL CLIENTE
    // ─────────────────────────────────────────────
    static void aplicarPromocionCliente(Cliente cliente, double descuento) {
        Promocion promocion = promocionDAO.buscarPorDescuento(descuento * 100);
        if (promocion == null) {
            System.out.println("Aviso: no se encontro una promocion del " +
                    (int)(descuento * 100) + "% en la BD. Registrela primero.");
            return;
        }

        ClientePromocion cp = new ClientePromocion();
        cp.setIdCliente(cliente.getIdCliente()); // Asegúrate de que este ID no sea 0
        cp.setIdPromocion(promocion.getIdPromocion());
        cp.setFechaAdquirida(new java.sql.Date(System.currentTimeMillis()));

        boolean ok = clientePromocionDAO.insertar(cp);
        if (ok) {
            System.out.println("Promocion del " + (int)(descuento * 100) + "% asignada al cliente.");
        } else {
            System.out.println("No se pudo registrar la promocion del cliente.");
        }
    }

    // ─────────────────────────────────────────────
    // MÉTODOS AUXILIARES PARA LECTURA SEGURA
    // ─────────────────────────────────────────────
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Dato invalido. Ingrese un numero entero: ");
            }
        }
    }

    private static double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Dato invalido. Ingrese un valor numerico (ej: 12.50): ");
            }
        }
    }
}