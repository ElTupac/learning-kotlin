package shop.inventa.template.user.domain.usecase

import shop.inventa.template.user.domain.model.User

interface FindUserByIdUseCase {
    fun execute(command: FindUserByIdCommand): User?

    data class FindUserByIdCommand(val id: Long)
}
