package shop.inventa.template.user.domain.usecase

import shop.inventa.template.user.domain.model.User

interface FindUserByEmailUseCase {
    fun execute(command: FindUserByEmailCommand): User?

    data class FindUserByEmailCommand(val email: String)
}
