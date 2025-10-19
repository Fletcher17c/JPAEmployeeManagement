// src/main/java/util/JPAUtil.java
package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JPAUtil {

    private static final String POSTGRES_UNIT = "postgres-unit";
    private static final String SQLITE_UNIT = "sqlite-unit";

    private static EntityManagerFactory entityManagerFactory;
    private static String activeUnit;
    private static boolean usingFallback = false;

    static {
        initializeDatabaseConnection();
    }

    private static void initializeDatabaseConnection() {
        // Primero intentar con PostgreSQL
        try {
            log.info("🔍 Intentando conectar a PostgreSQL...");
            entityManagerFactory = Persistence.createEntityManagerFactory(POSTGRES_UNIT);
            activeUnit = POSTGRES_UNIT;
            usingFallback = false;
            log.info("✅ Conectado exitosamente a PostgreSQL");

        } catch (Exception postgresEx) {
            log.warn("❌ No se pudo conectar a PostgreSQL: {}", postgresEx.getMessage());
            log.info("🔄 Intentando fallback a SQLite...");

            // Fallback a SQLite
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory(SQLITE_UNIT);
                activeUnit = SQLITE_UNIT;
                usingFallback = true;
                log.info("✅ Conectado exitosamente a SQLite (modo fallback)");

            } catch (Exception sqliteEx) {
                log.error("💥 Error crítico: No se pudo conectar a ninguna base de datos");
                log.error("Error PostgreSQL: {}", postgresEx.getMessage());
                log.error("Error SQLite: {}", sqliteEx.getMessage());
                throw new RuntimeException("No se pudo conectar a ninguna base de datos disponible", sqliteEx);
            }
        }
    }

    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            initializeDatabaseConnection();
        }
        return entityManagerFactory.createEntityManager();
    }

    public static boolean isUsingFallback() {
        return usingFallback;
    }

    public static String getActiveDatabase() {
        return usingFallback ? "SQLite" : "PostgreSQL";
    }

    public static String getConnectionDetails() {
        if (usingFallback) {
            return "SQLite (archivo local: gestion_empleados.db)";
        } else {
            return "PostgreSQL (servidor)";
        }
    }

    /**
     * Forzar el uso de una unidad específica (útil para testing)
     */
    public static void forceDatabase(String unitName) {
        close();
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
            activeUnit = unitName;
            usingFallback = SQLITE_UNIT.equals(unitName);
            log.info("Base de datos forzada a: {}", unitName);
        } catch (Exception e) {
            log.error("Error forzando base de datos: {}", e.getMessage());
            throw new RuntimeException("No se pudo forzar la conexión a " + unitName, e);
        }
    }

    /**
     * Reiniciar la conexión (útil para recuperación)
     */
    public static void reconnect() {
        close();
        initializeDatabaseConnection();
    }

    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            entityManagerFactory = null;
            log.info("Conexión de base de datos cerrada");
        }
    }

    /**
     * Configuración personalizada para PostgreSQL
     */
    public static void configurePostgreSQL(String host, String port, String database,
                                           String username, String password) {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url",
                String.format("jdbc:postgresql://%s:%s/%s", host, port, database));
        properties.put("jakarta.persistence.jdbc.user", username);
        properties.put("jakarta.persistence.jdbc.password", password);

        close();
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(POSTGRES_UNIT, properties);
            activeUnit = POSTGRES_UNIT;
            usingFallback = false;
            log.info("PostgreSQL configurado: {}@{}:{}/{}", username, host, port, database);
        } catch (Exception e) {
            log.error("Error configurando PostgreSQL: {}", e.getMessage());
            // Fallback automático
            initializeDatabaseConnection();
        }
    }

    /**
     * Configuración personalizada para SQLite
     */
    public static void configureSQLite(String filePath) {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", "jdbc:sqlite:" + filePath);

        close();
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(SQLITE_UNIT, properties);
            activeUnit = SQLITE_UNIT;
            usingFallback = true;
            log.info("SQLite configurado: {}", filePath);
        } catch (Exception e) {
            log.error("Error configurando SQLite: {}", e.getMessage());
            throw new RuntimeException("No se pudo configurar SQLite", e);
        }
    }
}