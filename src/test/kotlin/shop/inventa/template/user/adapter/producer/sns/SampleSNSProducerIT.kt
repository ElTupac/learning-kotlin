package shop.inventa.template.user.adapter.producer.sns

import com.ninjasquad.springmockk.MockkBean
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SampleSNSProducerIT {
    private val expectedTopicName = "sample-topic-test"

    @MockkBean
    private lateinit var notificationMessagingTemplate: NotificationMessagingTemplate

    @Autowired
    private lateinit var sampleSNSProducer: SampleSNSProducer

    @Test
    fun `send - should send payload correctly`() {
        // given
        val payload = "some_payload"
        every { notificationMessagingTemplate.convertAndSend(expectedTopicName, payload) } returns Unit

        // when
        sampleSNSProducer.send(payload)

        // then
        verify(exactly = 1) { notificationMessagingTemplate.convertAndSend(expectedTopicName, payload) }
    }
}
