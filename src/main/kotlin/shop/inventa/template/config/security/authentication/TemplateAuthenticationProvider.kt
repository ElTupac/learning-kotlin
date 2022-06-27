package shop.inventa.template.config.security.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class TemplateAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication) = authentication

    override fun supports(authentication: Class<*>) =
        TemplateAuthentication::class.java.isAssignableFrom(authentication)
}
