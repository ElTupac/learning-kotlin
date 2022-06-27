package shop.inventa.template.config.actuator.health.db

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

@Profile("!integration-test")
@Component
class HealthDataSource(
    @Value("\${spring.datasource.url}")
    private val datasourceUrl: String,

    @Value("\${spring.datasource.username}")
    private val datasourceUsername: String,

    @Value("\${spring.datasource.password}")
    private val datasourcePassword: String,
) {
    @Throws(SQLException::class)
    fun createSingleConnection(timeoutInSeconds: Int): Connection {
        DriverManager.setLoginTimeout(timeoutInSeconds)
        return DriverManager.getConnection(datasourceUrl, datasourceUsername, datasourcePassword)
    }
}
