package dao;

import models.Empleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
public class EmpleadoDAO implements IEmpleado {

    private final EntityManager entityManager;

    public EmpleadoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            if (empleado.getId() == null) {
                entityManager.persist(empleado);
                log.info("Empleado guardado: {} {}", empleado.getNombre(), empleado.getApellido());
            } else {
                empleado = entityManager.merge(empleado);
                log.info("Empleado actualizado: {} {}", empleado.getNombre(), empleado.getApellido());
            }

            transaction.commit();
            return empleado;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error al guardar empleado: {}", e.getMessage());
            throw new RuntimeException("Error al guardar empleado", e);
        }
    }

    @Override
    public Optional<Empleado> buscarPorId(Long id) {
        try {
            Empleado empleado = entityManager.find(Empleado.class, id);
            return Optional.ofNullable(empleado);
        } catch (Exception e) {
            log.error("Error al buscar empleado por ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Empleado> buscarPorIdentificacion(String numeroIdentificacion) {
        try {
            TypedQuery<Empleado> query = entityManager.createQuery(
                    "SELECT e FROM Empleado e WHERE e.num_empleado = :identificacion", Empleado.class);
            query.setParameter("identificacion", numeroIdentificacion);

            List<Empleado> resultados = query.getResultList();
            return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));

        } catch (Exception e) {
            log.error("Error al buscar empleado por identificación '{}': {}", numeroIdentificacion, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Empleado> buscarTodos() {
        try {
            TypedQuery<Empleado> query = entityManager.createQuery(
                    "SELECT e FROM Empleado e ORDER BY e.apellido, e.nombre", Empleado.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos los empleados: {}", e.getMessage());
            throw new RuntimeException("Error al obtener empleados", e);
        }
    }

    @Override
    public List<Empleado> buscarPorCargo(Long cargoId) {
        try {
            TypedQuery<Empleado> query = entityManager.createQuery(
                    "SELECT e FROM Empleado e WHERE e.cargo.id = :cargoId ORDER BY e.apellido, e.nombre", Empleado.class);
            query.setParameter("cargoId", cargoId);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar empleados por cargo ID {}: {}", cargoId, e.getMessage());
            throw new RuntimeException("Error al buscar empleados por cargo", e);
        }
    }

    @Override
    public List<Empleado> buscarPorNombre(String nombre) {
        try {
            TypedQuery<Empleado> query = entityManager.createQuery(
                    "SELECT e FROM Empleado e WHERE e.nombre LIKE :nombre OR e.apellido LIKE :nombre ORDER BY e.apellido, e.nombre", Empleado.class);
            query.setParameter("nombre", "%" + nombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar empleados por nombre '{}': {}", nombre, e.getMessage());
            throw new RuntimeException("Error al buscar empleados", e);
        }
    }

    @Override
    public List<Empleado> buscarActivos() {
        try {
            TypedQuery<Empleado> query = entityManager.createQuery(
                    "SELECT e FROM Empleado e WHERE e.activo = true ORDER BY e.apellido, e.nombre", Empleado.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar empleados activos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener empleados activos", e);
        }
    }

    @Override
    public List<Empleado> buscarInactivos() {
        try {
            TypedQuery<Empleado> query = entityManager.createQuery(
                    "SELECT e FROM Empleado e WHERE e.activo = false ORDER BY e.apellido, e.nombre", Empleado.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar empleados inactivos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener empleados inactivos", e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Empleado empleado = entityManager.find(Empleado.class, id);
            if (empleado != null) {
                entityManager.remove(empleado);
                log.info("Empleado eliminado: {} {}", empleado.getNombre(), empleado.getApellido());
                transaction.commit();
                return true;
            }

            transaction.commit();
            return false;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error al eliminar empleado ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar empleado", e);
        }
    }

    @Override
    public boolean desactivar(Long id) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Empleado empleado = entityManager.find(Empleado.class, id);
            if (empleado != null) {
                empleado.setActivo(false);
                entityManager.merge(empleado);
                log.info("Empleado desactivado: {} {}", empleado.getNombre(), empleado.getApellido());
                transaction.commit();
                return true;
            }

            transaction.commit();
            return false;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error al desactivar empleado ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al desactivar empleado", e);
        }
    }

    @Override
    public boolean activar(Long id) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Empleado empleado = entityManager.find(Empleado.class, id);
            if (empleado != null) {
                empleado.setActivo(true);
                entityManager.merge(empleado);
                log.info("Empleado activado: {} {}", empleado.getNombre(), empleado.getApellido());
                transaction.commit();
                return true;
            }

            transaction.commit();
            return false;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error al activar empleado ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al activar empleado", e);
        }
    }

    @Override
    public long contar() {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(e) FROM Empleado e", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar empleados: {}", e.getMessage());
            return 0;
        }
    }

    @Override
    public long contarActivos() {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(e) FROM Empleado e WHERE e.activo = true", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar empleados activos: {}", e.getMessage());
            return 0;
        }
    }

    @Override
    public long contarInactivos() {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(e) FROM Empleado e WHERE e.activo = false", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar empleados inactivos: {}", e.getMessage());
            return 0;
        }
    }


}