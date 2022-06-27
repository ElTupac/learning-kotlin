package shop.inventa.template.user.adapter.producer.sns

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import shop.inventa.template.common.adapter.messaging.SNSProducer

@Component
class SampleSNSProducer(@Value("\${messaging.topic.sample-topic}") private val topicName: String) :
    SNSProducer<String>() {
    override fun getTopicName() = topicName
}
