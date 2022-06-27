package shop.inventa.template.user.domain.port

import shop.inventa.template.user.domain.model.User

interface CreateUserPort {
    fun create(user: User): User
}
