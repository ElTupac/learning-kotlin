package shop.inventa.template.common.adapter.messaging

import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.beans.factory.annotation.Autowired

abstract class SQSProducer<T : Any> {
    @Autowired
    private lateinit var queueMessagingTemplate: QueueMessagingTemplate

    abstract fun getQueueName(): String

    fun send(message: T) {
        queueMessagingTemplate.convertAndSend(this.getQueueName(), message)
    }
}
