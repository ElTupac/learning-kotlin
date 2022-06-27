package shop.inventa.template.user.adapter.producer.sqs

import com.ninjasquad.springmockk.MockkBean
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import shop.inventa.template.user.adapter.dto.UserEventDto
import shop.inventa.template.user.domain.model.UserMother

@SpringBootTest
internal class UserSQSProducerAdapterIT {
    private val expectedQueueName = "user-event-queue-test"

    @MockkBean
    private lateinit var queueMessagingTemplate: QueueMessagingTemplate

    @Autowired
    private lateinit var userSQSProducerAdapter: UserSQSProducerAdapter

    @Test
    fun `created - should send event correctly when uuid is not null`() {
        // given
        val user = UserMother.validUser()
        val userEventDtoSlot = slot<UserEventDto>()
        every { queueMessagingTemplate.convertAndSend(expectedQueueName, capture(userEventDtoSlot)) } returns Unit

        // when
        userSQSProducerAdapter.created(user.uuid!!)

        // then
        val capturedUserEventDto = userEventDtoSlot.captured
        assertAll("user event DTO", {
            assertEquals(UserEventDto.Event.CREATED, capturedUserEventDto.event)
            assertEquals(user.uuid, capturedUserEventDto.uuid)
        })
        verify(exactly = 1) { queueMessagingTemplate.convertAndSend(expectedQueueName, capturedUserEventDto) }
    }
}
