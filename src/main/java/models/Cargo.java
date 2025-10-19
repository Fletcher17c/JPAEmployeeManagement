package models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cargos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "salario_base", nullable = false)
    private Double salarioBase;

    @Column(name = "nivel", length = 50)
    private String nivel;

    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Empleado> empleados = new ArrayList<>();

    // Constructor personalizado sin ID
    public Cargo(String nombre, String descripcion, Double salarioBase, String nivel) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.salarioBase = salarioBase;
        this.nivel = nivel;
    }

    // Solo métodos de negocio personalizados
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        empleado.setCargo(this);
    }

    public void removerEmpleado(Empleado empleado) {
        empleados.remove(empleado);
        empleado.setCargo(null);
    }
}