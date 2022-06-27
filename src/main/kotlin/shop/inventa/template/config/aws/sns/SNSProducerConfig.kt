package shop.inventa.template.config.aws.sns

import com.amazonaws.services.sns.AmazonSNS
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SNSProducerConfig(private val amazonSNS: AmazonSNS) {
    @Bean
    fun notificationMessagingTemplate() = NotificationMessagingTemplate(amazonSNS)
}
