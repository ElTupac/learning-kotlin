package shop.inventa.template.user.adapter.producer.sqs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import shop.inventa.template.common.adapter.messaging.SQSProducer
import shop.inventa.template.user.adapter.dto.UserEventDto
import shop.inventa.template.user.domain.port.UserEventPort
import java.util.UUID

@Component
class UserSQSProducerAdapter(@Value("\${messaging.queue.user-event}") private val queueName: String) :
    UserEventPort, SQSProducer<UserEventDto>() {
    override fun getQueueName() = queueName

    override fun created(uuid: UUID) {
        this.send(UserEventDto(UserEventDto.Event.CREATED, uuid))
    }
}
