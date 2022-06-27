package shop.inventa.template.user.domain.usecase

import org.springframework.stereotype.Service
import shop.inventa.template.user.domain.model.User
import shop.inventa.template.user.domain.port.CreateUserPort
import shop.inventa.template.user.domain.port.UserEventPort

@Service
class CreateUserUseCaseImpl(private val createUserPort: CreateUserPort, private val userEventPort: UserEventPort) :
    CreateUserUseCase {
    override fun execute(command: CreateUserUseCase.CreateUserCommand): User {
        val createdUser = createUserPort.create(command.user)
        if (createdUser.uuid != null) {
            userEventPort.created(createdUser.uuid)
        }
        return createdUser
    }
}
