package shop.inventa.template.user.adapter.producer.sqs

import com.ninjasquad.springmockk.MockkBean
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SampleSQSProducerIT {
    private val expectedQueueName = "sample-queue-test"

    @MockkBean
    private lateinit var queueMessagingTemplate: QueueMessagingTemplate

    @Autowired
    private lateinit var sampleSQSProducer: SampleSQSProducer

    @Test
    fun `send - should send payload correctly`() {
        // given
        val payload = "some_payload"
        every { queueMessagingTemplate.convertAndSend(expectedQueueName, payload) } returns Unit

        // when
        sampleSQSProducer.send(payload)

        // then
        verify(exactly = 1) { queueMessagingTemplate.convertAndSend(expectedQueueName, payload) }
    }
}
