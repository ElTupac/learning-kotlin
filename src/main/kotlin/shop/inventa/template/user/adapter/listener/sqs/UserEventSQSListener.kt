package shop.inventa.template.user.adapter.listener.sqs

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import mu.KotlinLogging
import org.springframework.stereotype.Component
import shop.inventa.template.user.adapter.dto.UserEventDto
import shop.inventa.template.user.domain.usecase.FindUserByUUIDUseCase

private val logger = KotlinLogging.logger {}

@Component
class UserEventSQSListener(private val findUserByUUIDUseCase: FindUserByUUIDUseCase) {
    @SqsListener("\${messaging.queue.user-event}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun listen(event: UserEventDto) =
        when (event.event) {
            UserEventDto.Event.CREATED -> handleUserCreation(event)
        }

    private fun handleUserCreation(event: UserEventDto) {
        val user = findUserByUUIDUseCase.execute(FindUserByUUIDUseCase.FindUserByUUIDCommand(event.uuid))
        if (user != null) {
            logger.info { "User ${user.name} was created!" }
        } else {
            logger.info { "User with UUID ${event.uuid} was not found" }
        }
    }
}
