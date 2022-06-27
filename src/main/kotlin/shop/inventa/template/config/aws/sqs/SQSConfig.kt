package shop.inventa.template.config.aws.sqs

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import shop.inventa.template.config.aws.AWSParameters

@Configuration
class SQSConfig(
    private val parameters: AWSParameters
) {
    @Bean
    @Primary
    @Profile("dev")
    fun amazonSQSAsyncDev(): AmazonSQSAsync =
        AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(AWSParameters.LOCALSTACK_URL, parameters.region)
            )
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(parameters.awsAccessKey, parameters.awsSecretKey)
                )
            )
            .build()

    @Bean
    @Primary
    @Profile("!dev")
    fun amazonSQSAsync(): AmazonSQSAsync =
        AmazonSQSAsyncClientBuilder.standard()
            .withRegion(parameters.region)
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(parameters.awsAccessKey, parameters.awsSecretKey)
                )
            )
            .build()
}
