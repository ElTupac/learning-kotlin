package shop.inventa.template.user.domain.usecase

import shop.inventa.template.user.domain.model.User
import java.util.UUID

interface FindUserByUUIDUseCase {
    fun execute(command: FindUserByUUIDCommand): User?

    data class FindUserByUUIDCommand(val uuid: UUID)
}
