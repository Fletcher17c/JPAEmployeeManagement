package dao;

import models.Cargo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CargoDAO implements ICargo {

    private final EntityManager entityManager;

    public CargoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Cargo guardar(Cargo cargo) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            if (cargo.getId() == null) {
                entityManager.persist(cargo);
                log.info("Cargo guardado: {}", cargo.getNombre());
            } else {
                cargo = entityManager.merge(cargo);
                log.info("Cargo actualizado: {}", cargo.getNombre());
            }

            transaction.commit();
            return cargo;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error al guardar cargo: {}", e.getMessage());
            throw new RuntimeException("Error al guardar cargo", e);
        }
    }

    @Override
    public Optional<Cargo> buscarPorId(Long id) {
        try {
            Cargo cargo = entityManager.find(Cargo.class, id);
            return Optional.ofNullable(cargo);
        } catch (Exception e) {
            log.error("Error al buscar cargo por ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Cargo> buscarPorNombreExacto(String nombre) {
        try {
            TypedQuery<Cargo> query = entityManager.createQuery(
                    "SELECT c FROM Cargo c WHERE c.nombre = :nombre", Cargo.class);
            query.setParameter("nombre", nombre);

            List<Cargo> resultados = query.getResultList();
            return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.getFirst());

        } catch (Exception e) {
            log.error("Error al buscar cargo por nombre '{}': {}", nombre, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Cargo> buscarTodos() {
        try {
            TypedQuery<Cargo> query = entityManager.createQuery(
                    "SELECT c FROM Cargo c ORDER BY c.nombre", Cargo.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar todos los cargos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener cargos", e);
        }
    }

    @Override
    public List<Cargo> buscarPorNombre(String nombre) {
        try {
            TypedQuery<Cargo> query = entityManager.createQuery(
                    "SELECT c FROM Cargo c WHERE c.nombre LIKE :nombre ORDER BY c.nombre", Cargo.class);
            query.setParameter("nombre", "%" + nombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error al buscar cargos por nombre '{}': {}", nombre, e.getMessage());
            throw new RuntimeException("Error al buscar cargos", e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Cargo cargo = entityManager.find(Cargo.class, id);
            if (cargo != null) {
                if (!cargo.getEmpleados().isEmpty()) {
                    throw new IllegalStateException(
                            "No se puede eliminar el cargo. Tiene " + cargo.getEmpleados().size() + " empleados asociados.");
                }

                entityManager.remove(cargo);
                log.info("Cargo eliminado: {}", cargo.getNombre());
                transaction.commit();
                return true;
            }

            transaction.commit();
            return false;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error al eliminar cargo ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar cargo", e);
        }
    }

    @Override
    public boolean existePorNombre(String nombre) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Cargo c WHERE c.nombre = :nombre", Long.class);
            query.setParameter("nombre", nombre);
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            log.error("Error al verificar existencia de cargo '{}': {}", nombre, e.getMessage());
            return false;
        }
    }

    @Override
    public long contar() {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Cargo c", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error al contar cargos: {}", e.getMessage());
            return 0;
        }
    }
}
