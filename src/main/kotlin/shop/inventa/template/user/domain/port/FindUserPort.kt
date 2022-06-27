package shop.inventa.template.user.domain.port

import shop.inventa.template.user.domain.model.User
import java.util.UUID

interface FindUserPort {
    fun findById(id: Long): User?

    fun findByEmail(email: String): User?

    fun findByNameStartsWith(partialName: String): List<User>

    fun findUserByUUID(uuid: UUID): User?
}
