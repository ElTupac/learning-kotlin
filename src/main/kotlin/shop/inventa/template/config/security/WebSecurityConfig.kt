package shop.inventa.template.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import shop.inventa.template.config.security.authentication.AuthorizationHeaderAuthenticationFilter
import shop.inventa.template.config.security.authentication.TemplateAuthenticationProvider

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val authenticationProvider: TemplateAuthenticationProvider,
    private val authorizationHeaderAuthenticationFilter: AuthorizationHeaderAuthenticationFilter,
) : WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/actuator/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**"
        )
    }

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .disable()
            .authorizeHttpRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterAfter(authorizationHeaderAuthenticationFilter, BasicAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider)
    }
}
