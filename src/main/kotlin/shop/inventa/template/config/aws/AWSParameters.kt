package shop.inventa.template.config.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AWSParameters(
    @Value("\${cloud.aws.region.static}")
    val region: String,

    @Value("\${cloud.aws.credentials.access-key}")
    val awsAccessKey: String,

    @Value("\${cloud.aws.credentials.secret-key}")
    val awsSecretKey: String,
) {
    companion object {
        const val LOCALSTACK_URL = "http://localhost:4566"
    }
}
