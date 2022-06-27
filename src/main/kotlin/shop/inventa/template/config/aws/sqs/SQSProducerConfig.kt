package shop.inventa.template.config.aws.sqs

import com.amazonaws.services.sqs.AmazonSQSAsync
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SQSProducerConfig(private val amazonSQSAsync: AmazonSQSAsync) {
    @Bean
    fun queueMessagingTemplate() = QueueMessagingTemplate(amazonSQSAsync)
}
