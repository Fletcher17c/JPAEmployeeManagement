// src/main/java/Main.java
import models.Cargo;
import models.Empleado;
import service.GestionEmpleadosService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static GestionEmpleadosService servicio;

    public static void main(String[] args) {
        System.out.println("🚀 INICIANDO SISTEMA DE GESTIÓN DE EMPLEADOS");
        System.out.println("=============================================\n");

        try {
            servicio = new GestionEmpleadosService();

            // Inicializar con datos de ejemplo
            servicio.inicializarDatosEjemplo();

            // Menú principal
            mostrarMenuPrincipal();

        } catch (Exception e) {
            System.err.println("❌ Error crítico al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (servicio != null) {
                servicio.cerrar();
            }
            scanner.close();
            System.out.println("\n=============================================");
            System.out.println("✅ APLICACIÓN FINALIZADA");
        }
    }

    private static void mostrarMenuPrincipal() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestión de Cargos");
            System.out.println("2. Gestión de Empleados");
            System.out.println("3. Consultas y Reportes");
            System.out.println("4. Estadísticas del Sistema");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        menuGestionCargos();
                        break;
                    case 2:
                        menuGestionEmpleados();
                        break;
                    case 3:
                        menuConsultas();
                        break;
                    case 4:
                        mostrarEstadisticas();
                        break;
                    case 5:
                        salir = true;
                        System.out.println("👋 ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("❌ Opción no válida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private static void menuGestionCargos() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE CARGOS ---");
            System.out.println("1. Listar todos los cargos");
            System.out.println("2. Buscar cargo por nombre");
            System.out.println("3. Crear nuevo cargo");
            System.out.println("4. Actualizar cargo");
            System.out.println("5. Eliminar cargo");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        listarCargos();
                        break;
                    case 2:
                        buscarCargoPorNombre();
                        break;
                    case 3:
                        crearCargo();
                        break;
                    case 4:
                        actualizarCargo();
                        break;
                    case 5:
                        eliminarCargo();
                        break;
                    case 6:
                        volver = true;
                        break;
                    default:
                        System.out.println("❌ Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private static void menuGestionEmpleados() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
            System.out.println("1. Listar todos los empleados");
            System.out.println("2. Buscar empleado por nombre");
            System.out.println("3. Buscar empleado por identificación");
            System.out.println("4. Crear nuevo empleado");
            System.out.println("5. Actualizar empleado");
            System.out.println("6. Desactivar empleado");
            System.out.println("7. Activar empleado");
            System.out.println("8. Eliminar empleado");
            System.out.println("9. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        listarEmpleados();
                        break;
                    case 2:
                        buscarEmpleadoPorNombre();
                        break;
                    case 3:
                        buscarEmpleadoPorIdentificacion();
                        break;
                    case 4:
                        crearEmpleado();
                        break;
                    case 5:
                        actualizarEmpleado();
                        break;
                    case 6:
                        desactivarEmpleado();
                        break;
                    case 7:
                        activarEmpleado();
                        break;
                    case 8:
                        eliminarEmpleado();
                        break;
                    case 9:
                        volver = true;
                        break;
                    default:
                        System.out.println("❌ Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private static void menuConsultas() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- CONSULTAS Y REPORTES ---");
            System.out.println("1. Empleados activos");
            System.out.println("2. Empleados inactivos");
            System.out.println("3. Empleados por cargo");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        listarEmpleadosActivos();
                        break;
                    case 2:
                        listarEmpleadosInactivos();
                        break;
                    case 3:
                        listarEmpleadosPorCargo();
                        break;
                    case 4:
                        volver = true;
                        break;
                    default:
                        System.out.println("❌ Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    // ===== MÉTODOS PARA CARGOS =====

    private static void listarCargos() {
        System.out.println("\n--- LISTADO DE CARGOS ---");
        List<Cargo> cargos = servicio.obtenerTodosLosCargos();

        if (cargos.isEmpty()) {
            System.out.println("No hay cargos registrados.");
        } else {
            cargos.forEach(cargo -> {
                long numEmpleados = servicio.obtenerEmpleadosPorCargo(cargo).size();
                System.out.printf("- ID: %d | %s (Nivel: %s) | Salario base: $%,.2f | Empleados: %d%n",
                        cargo.getId(), cargo.getNombre(), cargo.getNivel(),
                        cargo.getSalarioBase(), numEmpleados);
                System.out.printf("  Descripción: %s%n", cargo.getDescripcion());
            });
        }
    }

    private static void buscarCargoPorNombre() {
        System.out.print("Ingrese el nombre o parte del nombre del cargo: ");
        String nombre = scanner.nextLine();

        List<Cargo> cargos = servicio.buscarCargosPorNombre(nombre);

        if (cargos.isEmpty()) {
            System.out.println("No se encontraron cargos con ese nombre.");
        } else {
            System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
            cargos.forEach(cargo -> {
                System.out.printf("- %s (Nivel: %s) - $%,.2f%n",
                        cargo.getNombre(), cargo.getNivel(), cargo.getSalarioBase());
            });
        }
    }

    private static void crearCargo() {
        System.out.println("\n--- CREAR NUEVO CARGO ---");

        System.out.print("Nombre del cargo: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Salario base: ");
        double salarioBase = Double.parseDouble(scanner.nextLine());

        System.out.print("Nivel (Ejecutivo/Senior/Semi-Senior/Junior): ");
        String nivel = scanner.nextLine();

        try {
            Cargo cargo = servicio.crearCargo(nombre, descripcion, salarioBase, nivel);
            System.out.println("✅ Cargo creado exitosamente: " + cargo.getNombre());
        } catch (Exception e) {
            System.out.println("❌ Error al crear cargo: " + e.getMessage());
        }
    }

    private static void actualizarCargo() {
        System.out.println("\n--- ACTUALIZAR CARGO ---");
        listarCargos();

        System.out.print("Ingrese el ID del cargo a actualizar: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            Cargo cargoExistente = servicio.obtenerCargoPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado"));

            System.out.print("Nuevo nombre [" + cargoExistente.getNombre() + "]: ");
            String nombre = scanner.nextLine();
            if (nombre.trim().isEmpty()) nombre = cargoExistente.getNombre();

            System.out.print("Nueva descripción [" + cargoExistente.getDescripcion() + "]: ");
            String descripcion = scanner.nextLine();
            if (descripcion.trim().isEmpty()) descripcion = cargoExistente.getDescripcion();

            System.out.print("Nuevo salario base [" + cargoExistente.getSalarioBase() + "]: ");
            String salarioStr = scanner.nextLine();
            Double salarioBase = salarioStr.trim().isEmpty() ?
                    cargoExistente.getSalarioBase() : Double.parseDouble(salarioStr);

            System.out.print("Nuevo nivel [" + cargoExistente.getNivel() + "]: ");
            String nivel = scanner.nextLine();
            if (nivel.trim().isEmpty()) nivel = cargoExistente.getNivel();

            Cargo cargoActualizado = servicio.actualizarCargo(id, nombre, descripcion, salarioBase, nivel);
            System.out.println("✅ Cargo actualizado exitosamente: " + cargoActualizado.getNombre());

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar cargo: " + e.getMessage());
        }
    }

    private static void eliminarCargo() {
        System.out.println("\n--- ELIMINAR CARGO ---");
        listarCargos();

        System.out.print("Ingrese el ID del cargo a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            servicio.eliminarCargo(id);
            System.out.println("✅ Cargo eliminado exitosamente");
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar cargo: " + e.getMessage());
        }
    }

    // ===== MÉTODOS PARA EMPLEADOS =====

    private static void listarEmpleados() {
        System.out.println("\n--- LISTADO DE EMPLEADOS ---");
        List<Empleado> empleados = servicio.obtenerTodosLosEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            empleados.forEach(emp -> {
                String estado = emp.getActivo() ? "ACTIVO" : "INACTIVO";
                System.out.printf("- ID: %d | %s %s | %s | Cargo: %s | Salario: $%,.2f | %s%n",
                        emp.getId(), emp.getNombre(), emp.getApellido(),
                        emp.getNum_empleado(), emp.getCargo().getNombre(),
                        emp.getSalarioActual(), estado);
            });
        }
    }

    private static void listarEmpleadosActivos() {
        System.out.println("\n--- EMPLEADOS ACTIVOS ---");
        List<Empleado> empleados = servicio.obtenerEmpleadosActivos();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados activos.");
        } else {
            empleados.forEach(emp -> {
                System.out.printf("- %s %s | %s | %s | $%,.2f%n",
                        emp.getNombre(), emp.getApellido(), emp.getNum_empleado(),
                        emp.getCargo().getNombre(), emp.getSalarioActual());
            });
        }
    }

    private static void listarEmpleadosInactivos() {
        System.out.println("\n--- EMPLEADOS INACTIVOS ---");
        List<Empleado> empleados = servicio.obtenerEmpleadosInactivos();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados inactivos.");
        } else {
            empleados.forEach(emp -> {
                System.out.printf("- %s %s | %s | %s%n",
                        emp.getNombre(), emp.getApellido(), emp.getNum_empleado(),
                        emp.getCargo().getNombre());
            });
        }
    }

    private static void buscarEmpleadoPorNombre() {
        System.out.print("Ingrese el nombre o apellido del empleado: ");
        String nombre = scanner.nextLine();

        List<Empleado> empleados = servicio.buscarEmpleadosPorNombre(nombre);

        if (empleados.isEmpty()) {
            System.out.println("No se encontraron empleados con ese nombre.");
        } else {
            System.out.println("\n--- RESULTADOS DE BÚSQUEDA ---");
            empleados.forEach(emp -> {
                String estado = emp.getActivo() ? "ACTIVO" : "INACTIVO";
                System.out.printf("- %s %s | %s | %s | %s%n",
                        emp.getNombre(), emp.getApellido(), emp.getNum_empleado(),
                        emp.getCargo().getNombre(), estado);
            });
        }
    }

    private static void buscarEmpleadoPorIdentificacion() {
        System.out.print("Ingrese el número de identificación: ");
        String identificacion = scanner.nextLine();

        servicio.obtenerEmpleadoPorIdentificacion(identificacion)
                .ifPresentOrElse(
                        emp -> {
                            String estado = emp.getActivo() ? "ACTIVO" : "INACTIVO";
                            System.out.println("\n--- EMPLEADO ENCONTRADO ---");
                            System.out.printf("Nombre: %s %s%n", emp.getNombre(), emp.getApellido());
                            System.out.printf("Identificación: %s%n", emp.getNum_empleado());
                            System.out.printf("Email: %s%n", emp.getEmail());
                            System.out.printf("Teléfono: %s%n", emp.getTelefono());
                            System.out.printf("Cargo: %s%n", emp.getCargo().getNombre());
                            System.out.printf("Salario: $%,.2f%n", emp.getSalarioActual());
                            System.out.printf("Fecha contratación: %s%n", emp.getFechaContratacion());
                            System.out.printf("Estado: %s%n", estado);
                        },
                        () -> System.out.println("❌ No se encontró empleado con esa identificación.")
                );
    }

    private static void listarEmpleadosPorCargo() {
        System.out.println("\n--- EMPLEADOS POR CARGO ---");
        listarCargos();

        System.out.print("Ingrese el ID del cargo: ");
        Long cargoId = Long.parseLong(scanner.nextLine());

        try {
            List<Empleado> empleados = servicio.obtenerEmpleadosPorCargo(cargoId);

            if (empleados.isEmpty()) {
                System.out.println("No hay empleados en este cargo.");
            } else {
                empleados.forEach(emp -> {
                    String estado = emp.getActivo() ? "ACTIVO" : "INACTIVO";
                    System.out.printf("- %s %s | %s | $%,.2f | %s%n",
                            emp.getNombre(), emp.getApellido(), emp.getNum_empleado(),
                            emp.getSalarioActual(), estado);
                });
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void crearEmpleado() {
        System.out.println("\n--- CREAR NUEVO EMPLEADO ---");

        System.out.print("Número de identificación: ");
        String identificacion = scanner.nextLine();

        System.out.print("Nombres: ");
        String nombres = scanner.nextLine();

        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Fecha de contratación (YYYY-MM-DD): ");
        LocalDate fechaContratacion = LocalDate.parse(scanner.nextLine());

        System.out.print("Salario: ");
        Double salario = Double.parseDouble(scanner.nextLine());

        // Mostrar cargos disponibles
        listarCargos();
        System.out.print("ID del cargo: ");
        Long cargoId = Long.parseLong(scanner.nextLine());

        try {
            Cargo cargo = servicio.obtenerCargoPorId(cargoId)
                    .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado"));

            Empleado empleado = servicio.crearEmpleado(identificacion, nombres, apellidos,
                    email, telefono, fechaContratacion, salario, cargo);

            System.out.println("✅ Empleado creado exitosamente: " + empleado.getNombreCompleto());

        } catch (Exception e) {
            System.out.println("❌ Error al crear empleado: " + e.getMessage());
        }
    }

    private static void actualizarEmpleado() {
        System.out.println("\n--- ACTUALIZAR EMPLEADO ---");
        listarEmpleados();

        System.out.print("Ingrese el ID del empleado a actualizar: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            Empleado empleadoExistente = servicio.obtenerEmpleadoPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

            System.out.print("Nuevos nombres [" + empleadoExistente.getNombre() + "]: ");
            String nombres = scanner.nextLine();
            if (nombres.trim().isEmpty()) nombres = empleadoExistente.getNombre();

            System.out.print("Nuevos apellidos [" + empleadoExistente.getApellido() + "]: ");
            String apellidos = scanner.nextLine();
            if (apellidos.trim().isEmpty()) apellidos = empleadoExistente.getApellido();

            System.out.print("Nuevo email [" + empleadoExistente.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (email.trim().isEmpty()) email = empleadoExistente.getEmail();

            System.out.print("Nuevo teléfono [" + empleadoExistente.getTelefono() + "]: ");
            String telefono = scanner.nextLine();
            if (telefono.trim().isEmpty()) telefono = empleadoExistente.getTelefono();

            System.out.print("Nuevo salario [" + empleadoExistente.getSalarioActual() + "]: ");
            String salarioStr = scanner.nextLine();
            Double salario = salarioStr.trim().isEmpty() ?
                    empleadoExistente.getSalarioActual() : Double.parseDouble(salarioStr);

            listarCargos();
            System.out.print("Nuevo ID de cargo [" + empleadoExistente.getCargo().getId() + "]: ");
            String cargoIdStr = scanner.nextLine();
            Long cargoId = cargoIdStr.trim().isEmpty() ?
                    empleadoExistente.getCargo().getId() : Long.parseLong(cargoIdStr);

            Cargo cargo = servicio.obtenerCargoPorId(cargoId)
                    .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado"));

            Empleado empleadoActualizado = servicio.actualizarEmpleado(
                    id, nombres, apellidos, email, telefono, salario, cargo);

            System.out.println("✅ Empleado actualizado exitosamente: " + empleadoActualizado.getNombreCompleto());

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar empleado: " + e.getMessage());
        }
    }

    private static void desactivarEmpleado() {
        System.out.println("\n--- DESACTIVAR EMPLEADO ---");
        listarEmpleados();

        System.out.print("Ingrese el ID del empleado a desactivar: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            servicio.desactivarEmpleado(id);
            System.out.println("✅ Empleado desactivado exitosamente");
        } catch (Exception e) {
            System.out.println("❌ Error al desactivar empleado: " + e.getMessage());
        }
    }

    private static void activarEmpleado() {
        System.out.println("\n--- ACTIVAR EMPLEADO ---");
        servicio.obtenerEmpleadosInactivos().forEach(emp -> {
            System.out.printf("- ID: %d | %s %s | %s%n",
                    emp.getId(), emp.getNombre(), emp.getApellido(), emp.getNum_empleado());
        });

        System.out.print("Ingrese el ID del empleado a activar: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            servicio.activarEmpleado(id);
            System.out.println("✅ Empleado activado exitosamente");
        } catch (Exception e) {
            System.out.println("❌ Error al activar empleado: " + e.getMessage());
        }
    }

    private static void eliminarEmpleado() {
        System.out.println("\n--- ELIMINAR EMPLEADO ---");
        listarEmpleados();

        System.out.print("Ingrese el ID del empleado a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            servicio.eliminarEmpleado(id);
            System.out.println("✅ Empleado eliminado exitosamente");
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar empleado: " + e.getMessage());
        }
    }

    // ===== ESTADÍSTICAS =====

    private static void mostrarEstadisticas() {
        System.out.println("\n" + servicio.obtenerEstadisticas());
        System.out.println("🔧 " + servicio.getDatabaseStatus());
    }
}