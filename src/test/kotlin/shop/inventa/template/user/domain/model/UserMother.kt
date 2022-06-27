package shop.inventa.template.user.domain.model

import java.util.UUID

object UserMother {
    fun validUser(name: String = "Sample", email: String = "sample@email.com") = User(
        id = 1,
        uuid = UUID.randomUUID(),
        name = name,
        email = email
    )

    fun invalidEmailUser(name: String = "Sample") = User(
        id = 1,
        uuid = UUID.randomUUID(),
        name = name,
        email = ""
    )

    fun nullUIIDUser(name: String = "Sample", email: String = "sample@email.com") = User(
        id = 1,
        uuid = null,
        name = name,
        email = email
    )
}
