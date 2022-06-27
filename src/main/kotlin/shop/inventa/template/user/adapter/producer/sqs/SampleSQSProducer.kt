package shop.inventa.template.user.adapter.producer.sqs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import shop.inventa.template.common.adapter.messaging.SQSProducer

@Component
class SampleSQSProducer(@Value("\${messaging.queue.sample-queue}") private val queueName: String) :
    SQSProducer<String>() {
    override fun getQueueName() = queueName
}
