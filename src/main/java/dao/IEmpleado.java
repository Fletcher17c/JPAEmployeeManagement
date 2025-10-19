// src/main/java/dao/EmpleadoDAO.java
package dao;

import models.Empleado;
import java.util.List;
import java.util.Optional;

public interface IEmpleado {

    /**
     * Guarda o actualiza un empleado
     */
    Empleado guardar(Empleado empleado);

    /**
     * Busca un empleado por su ID
     */
    Optional<Empleado> buscarPorId(Long id);

    /**
     * Busca un empleado por su número de identificación
     */
    Optional<Empleado> buscarPorIdentificacion(String numeroIdentificacion);

    /**
     * Retorna todos los empleados ordenados por apellidos y nombres
     */
    List<Empleado> buscarTodos();

    /**
     * Busca empleados por cargo
     */
    List<Empleado> buscarPorCargo(Long cargoId);

    /**
     * Busca empleados cuyo nombre o apellido contenga el texto
     */
    List<Empleado> buscarPorNombre(String nombre);

    /**
     * Retorna solo empleados activos
     */
    List<Empleado> buscarActivos();

    /**
     * Retorna empleados inactivos
     */
    List<Empleado> buscarInactivos();

    /**
     * Elimina un empleado por su ID
     */
    boolean eliminar(Long id);

    /**
     * Desactiva un empleado (borrado lógico)
     */
    boolean desactivar(Long id);

    /**
     * Reactiva un empleado
     */
    boolean activar(Long id);

    /**
     * Cuenta el total de empleados
     */
    long contar();

    /**
     * Cuenta empleados activos
     */
    long contarActivos();

    /**
     * Cuenta empleados inactivos
     */
    long contarInactivos();
}