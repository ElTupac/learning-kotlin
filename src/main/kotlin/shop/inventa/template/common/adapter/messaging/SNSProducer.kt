package shop.inventa.template.common.adapter.messaging

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate
import org.springframework.beans.factory.annotation.Autowired

abstract class SNSProducer<T : Any> {
    @Autowired
    private lateinit var notificationMessagingTemplate: NotificationMessagingTemplate

    abstract fun getTopicName(): String

    fun send(message: T) {
        notificationMessagingTemplate.convertAndSend(getTopicName(), message)
    }
}
