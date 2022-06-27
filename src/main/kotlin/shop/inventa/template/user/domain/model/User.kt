package shop.inventa.template.user.domain.model

import shop.inventa.template.user.domain.exception.InvalidEmailException
import java.util.UUID

private val EMAIL_REGEX = """^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$""".toRegex()

data class User(val name: String, val email: String, val id: Long? = null, val uuid: UUID? = null) {
    init {
        if (!(EMAIL_REGEX matches this.email)) {
            throw InvalidEmailException("The email $email is not valid")
        }
    }
}
