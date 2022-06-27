package shop.inventa.template.user.domain.usecase

import org.springframework.stereotype.Service
import shop.inventa.template.user.domain.port.FindUserPort

@Service
class FindUserByEmailUseCaseImpl(private val findUserPort: FindUserPort) : FindUserByEmailUseCase {
    override fun execute(command: FindUserByEmailUseCase.FindUserByEmailCommand) =
        findUserPort.findByEmail(command.email)
}
