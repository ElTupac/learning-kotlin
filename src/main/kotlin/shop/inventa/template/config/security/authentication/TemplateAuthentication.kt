package shop.inventa.template.config.security.authentication

import org.springframework.security.authentication.AbstractAuthenticationToken

class TemplateAuthentication(private val token: String) : AbstractAuthenticationToken(null) {
    override fun getCredentials() = null

    override fun getPrincipal() = token

    override fun isAuthenticated() = true
}
