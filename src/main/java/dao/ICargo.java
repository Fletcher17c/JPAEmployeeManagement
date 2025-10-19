package dao;

import models.Cargo;
import java.util.List;
import java.util.Optional;

public interface ICargo {
    /**
     * Guarda o actualiza un cargo
     */
    Cargo guardar(Cargo cargo);

    /**
     * Busca un cargo por su ID
     */
    Optional<Cargo> buscarPorId(Long id);

    /**
     * Busca un cargo por su nombre exacto
     */
    Optional<Cargo> buscarPorNombreExacto(String nombre);

    /**
     * Retorna todos los cargos ordenados por nombre
     */
    List<Cargo> buscarTodos();

    /**
     * Busca cargos cuyo nombre contenga el texto proporcionado
     */
    List<Cargo> buscarPorNombre(String nombre);

    /**
     * Elimina un cargo por su ID
     */
    boolean eliminar(Long id);

    /**
     * Verifica si existe un cargo con el nombre proporcionado
     */
    boolean existePorNombre(String nombre);

    /**
     * Cuenta el total de cargos
     */
    long contar();
}
