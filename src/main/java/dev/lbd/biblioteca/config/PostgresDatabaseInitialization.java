package dev.lbd.biblioteca.config;

import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class PostgresDatabaseInitialization {

    @Value("${biblioteca-lbd.db.db-name}")
    private String dbName;
    @Value("${biblioteca-lbd.spring-boot.environment}")
    private String environment;
    private final JdbcTemplate jdbcTemplate;
//    private final SeedDataService seedDataService; ver se vamos implementar seeds

    public PostgresDatabaseInitialization(JdbcTemplate jdbcTemplate/*, SeedDataService seedDataService*/) {
        this.jdbcTemplate = jdbcTemplate;
//        this.seedDataService = seedDataService;
    }

    @PostConstruct
    public void initializeDatabase() {
        if (!databaseExists()) {
            this.jdbcTemplate.execute("CREATE DATABASE " + this.dbName);
        }
        /*
         * Verifique o valor de spring.profiles.active é "development" para executar seed
         */
        if ("development".equalsIgnoreCase(this.environment)) {
//            this.seedDataService.executeAllSeed();
        }
    }

    /**
     * Consulta SQL para verificar se o banco de dados existe
     */
    private boolean databaseExists() {
        String sql = "SELECT 1 FROM pg_database WHERE datname = ?";
        try {
            Integer result = this.jdbcTemplate.queryForObject(sql, Integer.class, this.dbName);
            return result != null && result == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
