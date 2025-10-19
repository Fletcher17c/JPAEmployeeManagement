Una librería de Java desarrollada con Maven que implementa un sistema completo de gestión de empleados y cargos utilizando Jakarta EE JPA para la persistencia de datos. El sistema está diseñado para ser ejecutable desde aplicaciones Java estándar (Java SE), sin depender de servidores de aplicaciones o entornos web.


## Arquitectura del Proyecto:

### `/src/main/java/`
- **`models/`** - Entidades JPA (Cargo, Empleado)
- **`dao/`** - Interfaces e implementaciones de Data Access Object
- **`service/`** - Lógica de negocio y servicios
- **`util/`** - Utilidades (JPAUtil, etc.)

### `/src/main/resources/`
- **`META-INF/persistence.xml`** - Configuración de persistencia JPA

### `/src/test/`
- Pruebas unitarias y de integración

### Archivos Raíz
- **`pom.xml`** - Configuración de Maven y dependencias
## Tech:

###  Tecnologías Utilizadas

    Java 21 - Lenguaje de programación

    Maven - Gestión de dependencias y build

    Jakarta EE JPA - API de persistencia

    Hibernate 6.4.4 - Implementación de JPA (downgraded for SQLite Compatibility)

    PostgreSQL - Base de datos principal

    SQLite - Base de datos fallback en caso de ausencia de la principal

    Lombok - Reducción de código repetitivo

    SLF4J - Logging en la aplicacion

### Requisitos:
    Java 21 o superior

    Maven 3.6+

    (Opcional) PostgreSQL si se desea usar como BD principal

## Instalación

'''
git clone <repository-url>
cd EmployeeManagement
mvn clean compile
mvn exec:java -Dexec.mainClass="Main"
'''

## Funcionalidades:
### Gestión de Cargos

    Crear, leer, actualizar y eliminar cargo
    Validación de nombres único
    Prevención de eliminación con empleados asociado
    Búsqueda por nombre

### Gestión de Empleados

    CRUD completo de empleados
    Números de identificación únicos
    Borrado lógico (activar/desactivar)
    Búsqueda por nombre, identificación y cargo
    Validación de integridad referencial

### Consultas y Reportes

    Listado de empleados activos/inactivos
    Empleados por cargo
    Estadísticas del sistema
    Búsquedas avanzadas

## Sistema de Fallback de Base de Datos

### Implemente un mecanismo inteligente de conexión:

    Intenta conectar a PostgreSQL primero

    Si falla, usa SQLite automáticamente

    Transparente para el usuario - misma funcionalidad

