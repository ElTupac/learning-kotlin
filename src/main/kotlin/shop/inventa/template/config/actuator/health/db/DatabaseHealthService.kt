package shop.inventa.template.config.actuator.health.db

import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

/**
 * a select one query shouldn't take more than 3 seconds in normal conditions.
 */
private const val HEALTH_CHECK_TIMEOUT_SECONDS = 3

private val logger = KotlinLogging.logger {}

@Profile("!integration-test")
@Service
class DatabaseHealthService(private val dataSource: HealthDataSource) {

    private val logIfInvalid: (Boolean) -> Unit = { valid ->
        if (!valid) logger.error { "[HealthService] Database connection is closed" }
    }

    /**
     * Tries to open a new connection to the database. Useful to validate if
     * database credentials are still valid.
     *
     * @return true if a new connection could be created
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    fun probeConnectionCreation() =
        try {
            dataSource.createSingleConnection(HEALTH_CHECK_TIMEOUT_SECONDS).use { connection ->
                connection.isValid(HEALTH_CHECK_TIMEOUT_SECONDS).also(logIfInvalid)
            }
        } catch (e: SQLException) {
            logger.error { "[HealthService] Couldn't create a database connection: $e" }
            false
        }
}
