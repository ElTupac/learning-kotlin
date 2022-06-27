package shop.inventa.template.user.domain.usecase

import shop.inventa.template.user.domain.model.User

interface FindUsersByNameStartingWithUseCase {
    fun execute(command: FindUsersByNameStartingWithCommand): List<User>

    data class FindUsersByNameStartingWithCommand(val partialName: String)
}
