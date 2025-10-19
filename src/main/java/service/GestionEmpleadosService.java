// src/main/java/service/GestionEmpleadosService.java
package service;

import dao.CargoDAO;
import dao.EmpleadoDAO;
import dao.ICargo;
import dao.IEmpleado;
import models.Cargo;
import models.Empleado;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
public class GestionEmpleadosService {

    private final ICargo cargoDAO;
    private final IEmpleado empleadoDAO;
    private final EntityManager entityManager;

    public GestionEmpleadosService() {
        this.entityManager = JPAUtil.getEntityManager();
        this.cargoDAO = new CargoDAO(entityManager);
        this.empleadoDAO = new EmpleadoDAO(entityManager);
        logDatabaseInfo();
    }

    private void logDatabaseInfo() {
        System.out.println("🔍 Información de conexión:");
        System.out.println("   Base de datos: " + JPAUtil.getActiveDatabase());
        System.out.println("   Detalles: " + JPAUtil.getConnectionDetails());
        System.out.println("   Modo fallback: " + (JPAUtil.isUsingFallback() ? "SÍ" : "NO"));
        System.out.println();
    }

    // ===== MÉTODOS PARA CARGOS =====

    public Cargo crearCargo(String nombre, String descripcion, Double salarioBase, String nivel) {
        if (cargoDAO.existePorNombre(nombre)) {
            throw new IllegalArgumentException("❌ Ya existe un cargo con el nombre: " + nombre);
        }

        Cargo cargo = new Cargo(nombre, descripcion, salarioBase, nivel);
        return cargoDAO.guardar(cargo);
    }

    public Optional<Cargo> obtenerCargoPorId(Long id) {
        return cargoDAO.buscarPorId(id);
    }

    public Optional<Cargo> obtenerCargoPorNombre(String nombre) {
        return cargoDAO.buscarPorNombreExacto(nombre);
    }

    public List<Cargo> obtenerTodosLosCargos() {
        return cargoDAO.buscarTodos();
    }

    public List<Cargo> buscarCargosPorNombre(String nombre) {
        return cargoDAO.buscarPorNombre(nombre);
    }

    public void eliminarCargo(Long id) {
        Cargo cargo = cargoDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado con ID: " + id));

        if (!cargo.getEmpleados().isEmpty()) {
            throw new IllegalStateException(
                    "❌ No se puede eliminar el cargo '" + cargo.getNombre() + "'. Tiene " +
                            cargo.getEmpleados().size() + " empleados asociados.");
        }

        boolean eliminado = cargoDAO.eliminar(id);
        if (!eliminado) {
            throw new RuntimeException("Error al eliminar el cargo");
        }
    }

    public Cargo actualizarCargo(Long id, String nombre, String descripcion, Double salarioBase, String nivel) {
        Cargo cargo = cargoDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado con ID: " + id));

        // Verificar si el nuevo nombre ya existe (excluyendo el actual)
        if (!cargo.getNombre().equals(nombre) && cargoDAO.existePorNombre(nombre)) {
            throw new IllegalArgumentException("❌ Ya existe otro cargo con el nombre: " + nombre);
        }

        cargo.setNombre(nombre);
        cargo.setDescripcion(descripcion);
        cargo.setSalarioBase(salarioBase);
        cargo.setNivel(nivel);

        return cargoDAO.guardar(cargo);
    }

    // ===== MÉTODOS PARA EMPLEADOS =====

    public Empleado crearEmpleado(String numeroIdentificacion, String nombres, String apellidos,
                                  String email, String telefono, LocalDate fechaContratacion,
                                  Double salarioActual, Cargo cargo) {

        if (empleadoDAO.buscarPorIdentificacion(numeroIdentificacion).isPresent()) {
            throw new IllegalArgumentException("❌ Ya existe un empleado con la identificación: " + numeroIdentificacion);
        }

        Empleado empleado = new Empleado(numeroIdentificacion, nombres, apellidos,
                email, telefono, fechaContratacion, salarioActual, cargo);
        return empleadoDAO.guardar(empleado);
    }

    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoDAO.buscarPorId(id);
    }

    public Optional<Empleado> obtenerEmpleadoPorIdentificacion(String identificacion) {
        return empleadoDAO.buscarPorIdentificacion(identificacion);
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoDAO.buscarTodos();
    }

    public List<Empleado> obtenerEmpleadosActivos() {
        return empleadoDAO.buscarActivos();
    }

    public List<Empleado> obtenerEmpleadosInactivos() {
        return empleadoDAO.buscarInactivos();
    }

    public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
        return empleadoDAO.buscarPorNombre(nombre);
    }

    public List<Empleado> obtenerEmpleadosPorCargo(Long cargoId) {
        return empleadoDAO.buscarPorCargo(cargoId);
    }

    public List<Empleado> obtenerEmpleadosPorCargo(Cargo cargo) {
        return empleadoDAO.buscarPorCargo(cargo.getId());
    }

    public void desactivarEmpleado(Long id) {
        boolean desactivado = empleadoDAO.desactivar(id);
        if (!desactivado) {
            throw new IllegalArgumentException("Empleado no encontrado con ID: " + id);
        }
    }

    public void activarEmpleado(Long id) {
        boolean activado = empleadoDAO.activar(id);
        if (!activado) {
            throw new IllegalArgumentException("Empleado no encontrado con ID: " + id);
        }
    }

    public void eliminarEmpleado(Long id) {
        boolean eliminado = empleadoDAO.eliminar(id);
        if (!eliminado) {
            throw new IllegalArgumentException("Empleado no encontrado con ID: " + id);
        }
    }

    public Empleado actualizarEmpleado(Long id, String nombres, String apellidos, String email,
                                       String telefono, Double salarioActual, Cargo cargo) {
        Empleado empleado = empleadoDAO.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));

        empleado.setNombre(nombres);
        empleado.setApellido(apellidos);
        empleado.setEmail(email);
        empleado.setTelefono(telefono);
        empleado.setSalarioActual(salarioActual);
        empleado.setCargo(cargo);

        return empleadoDAO.guardar(empleado);
    }

    // ===== MÉTODOS DE ESTADÍSTICAS =====

    public long contarTotalEmpleados() {
        return empleadoDAO.contar();
    }

    public long contarEmpleadosActivos() {
        return empleadoDAO.contarActivos();
    }

    public long contarEmpleadosInactivos() {
        return empleadoDAO.contarInactivos();
    }

    public long contarTotalCargos() {
        return cargoDAO.contar();
    }

    public String obtenerEstadisticas() {
        return String.format(
                "📊 Estadísticas:\n" +
                        "   • Total empleados: %d\n" +
                        "   • Empleados activos: %d\n" +
                        "   • Empleados inactivos: %d\n" +
                        "   • Total cargos: %d",
                contarTotalEmpleados(), contarEmpleadosActivos(),
                contarEmpleadosInactivos(), contarTotalCargos()
        );
    }

    // ===== INICIALIZACIÓN DE DATOS =====

    public void inicializarDatosEjemplo() {
        if (cargoDAO.contar() == 0) {
            log.info("Inicializando datos de ejemplo...");
            crearDatosIniciales();
        }
    }

    private void crearDatosIniciales() {
        try {
            // Crear cargos iniciales
            Cargo gerente = crearCargo("Gerente General",
                    "Responsable de la dirección general de la empresa", 5000.0, "Ejecutivo");

            Cargo desarrolladorSenior = crearCargo("Desarrollador Senior",
                    "Desarrollo de aplicaciones empresariales complejas", 3500.0, "Senior");

            Cargo desarrolladorJunior = crearCargo("Desarrollador Junior",
                    "Desarrollo de aplicaciones bajo supervisión", 2000.0, "Junior");

            Cargo analista = crearCargo("Analista de Sistemas",
                    "Análisis y diseño de sistemas informáticos", 2800.0, "Semi-Senior");

            // Crear empleados de ejemplo
            crearEmpleado("001-123456-1000A", "Ana", "García López",
                    "ana.garcia@empresa.com", "555-100-200", LocalDate.of(2020, 3, 15),
                    5200.0, gerente);

            crearEmpleado("001-123456-1001B", "Carlos", "Martínez Ruiz",
                    "carlos.martinez@empresa.com", "555-100-201", LocalDate.of(2021, 6, 1),
                    3600.0, desarrolladorSenior);

            crearEmpleado("001-123456-1002C", "María", "Rodríguez Silva",
                    "maria.rodriguez@empresa.com", "555-100-202", LocalDate.of(2022, 1, 10),
                    2100.0, desarrolladorJunior);

            crearEmpleado("001-123456-1003D", "Pedro", "Hernández Castro",
                    "pedro.hernandez@empresa.com", "555-100-203", LocalDate.of(2021, 9, 20),
                    2900.0, analista);

            log.info("✅ Datos de ejemplo creados exitosamente");

        } catch (Exception e) {
            log.error("❌ Error al crear datos iniciales: {}", e.getMessage());
        }
    }

    // ===== GESTIÓN DE TRANSACCIONES =====

    public void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    public void commitTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }

    public void rollbackTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    public void cerrar() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        JPAUtil.close();
    }

    public String getDatabaseStatus() {
        return String.format("Conectado a: %s | Fallback: %s",
                JPAUtil.getActiveDatabase(),
                JPAUtil.isUsingFallback() ? "SÍ" : "NO");
    }
}