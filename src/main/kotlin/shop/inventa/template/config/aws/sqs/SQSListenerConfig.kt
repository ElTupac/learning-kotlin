package shop.inventa.template.config.aws.sqs

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory
import io.awspring.cloud.messaging.listener.QueueMessageHandler
import io.awspring.cloud.messaging.listener.SimpleMessageListenerContainer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.messaging.converter.MappingJackson2MessageConverter

@Configuration
class SQSListenerConfig(private val amazonSQSAsync: AmazonSQSAsync) {
    @Bean
    @Profile("!integration-test")
    fun messageListenerContainer(): SimpleMessageListenerContainer =
        messageListenerContainerFactory().createSimpleMessageListenerContainer().apply {
            setMessageHandler(messageHandler())
        }

    @Bean
    fun messageListenerContainerFactory(): SimpleMessageListenerContainerFactory =
        SimpleMessageListenerContainerFactory().apply {
            setAmazonSqs(amazonSQSAsync)
            setMaxNumberOfMessages(1)
        }

    @Bean
    fun messageHandler(): QueueMessageHandler {
        val jackson2MessageConverter = MappingJackson2MessageConverter().apply {
            objectMapper = jacksonObjectMapper()
        }
        return QueueMessageHandlerFactory().apply {
            setAmazonSqs(amazonSQSAsync)
            messageConverters = listOf(jackson2MessageConverter)
        }.createQueueMessageHandler()
    }
}
