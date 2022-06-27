package shop.inventa.template.config.security.authentication

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val AUTHORIZATION_HEADER = "Authorization"
private const val AUTHORIZATION_HEADER_SPLIT_DELIMITER = " "
private const val AUTHORIZATION_HEADER_TOKEN_INDEX = 1

@Component
class AuthorizationHeaderAuthenticationFilter(
    @Value("\${security.secret.api_token}")
    private val apiToken: String?,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(AUTHORIZATION_HEADER)
            ?.split(AUTHORIZATION_HEADER_SPLIT_DELIMITER)
            ?.get(AUTHORIZATION_HEADER_TOKEN_INDEX)

        if (apiToken != null && apiToken == token) {
            SecurityContextHolder.getContext().authentication = TemplateAuthentication(token)
        } else {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }
}
