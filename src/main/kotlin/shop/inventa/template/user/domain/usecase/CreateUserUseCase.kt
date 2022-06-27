package shop.inventa.template.user.domain.usecase

import shop.inventa.template.user.domain.model.User

interface CreateUserUseCase {
    fun execute(command: CreateUserCommand): User

    data class CreateUserCommand(val user: User)
}
