package shop.inventa.template.config.aws.sns

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sns.AmazonSNS
import com.amazonaws.services.sns.AmazonSNSClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import shop.inventa.template.config.aws.AWSParameters

@Configuration
class SNSConfig(
    private val parameters: AWSParameters
) {
    @Bean
    @Primary
    @Profile("!dev")
    fun amazonSNS(): AmazonSNS =
        AmazonSNSClientBuilder
            .standard()
            .withRegion(parameters.region)
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(parameters.awsAccessKey, parameters.awsSecretKey)
                )
            )
            .build()

    @Bean
    @Primary
    @Profile("dev")
    fun amazonSNSDev(): AmazonSNS =
        AmazonSNSClientBuilder
            .standard()
            .withEndpointConfiguration(
                AwsClientBuilder.EndpointConfiguration(AWSParameters.LOCALSTACK_URL, parameters.region)
            )
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(parameters.awsAccessKey, parameters.awsSecretKey)
                )
            )
            .build()
}
