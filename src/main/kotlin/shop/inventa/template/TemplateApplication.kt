package shop.inventa.template

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(title = "Template API", version = "1.0"),
    externalDocs = ExternalDocumentation(
        description = "Template repository",
        url = "https://github.com/inventa-shop/kotlin-spring-template"
    ),
)
@SecurityScheme(name = "bearer-auth", scheme = "bearer", type = SecuritySchemeType.HTTP, `in` = SecuritySchemeIn.HEADER)
class TemplateApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<TemplateApplication>(*args)
}
