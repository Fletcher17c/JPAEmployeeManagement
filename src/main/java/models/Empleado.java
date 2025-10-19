package models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "num_empleado", nullable = false, unique = true, length = 20)
    @EqualsAndHashCode.Include
    private String num_empleado;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @Column(name = "salario_actual")
    private Double salarioActual;

    @Column(name = "activo")
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    @ToString.Exclude
    private Cargo cargo;

    // Constructor personalizado sin ID
    public Empleado(String num_empleado,
                    String nombre,
                    String apellido,
                    String email,
                    String telefono,
                    LocalDate fechaContratacion,
                    Double salarioActual,
                    Cargo cargo)
    {
        this.num_empleado = num_empleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaContratacion = fechaContratacion;
        this.salarioActual = salarioActual;
        this.cargo = cargo;
        this.activo = true;
    }

    // Solo métodos de negocio personalizados
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }
}