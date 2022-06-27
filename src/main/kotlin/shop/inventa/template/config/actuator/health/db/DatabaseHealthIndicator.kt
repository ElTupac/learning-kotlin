package shop.inventa.template.config.actuator.health.db

import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!integration-test")
@Component
class DatabaseHealthIndicator(private val databaseHealthService: DatabaseHealthService) : AbstractHealthIndicator() {
    override fun doHealthCheck(builder: Health.Builder) {
        if (databaseHealthService.probeConnectionCreation()) {
            builder.up()
        } else {
            builder.down()
        }
    }
}
