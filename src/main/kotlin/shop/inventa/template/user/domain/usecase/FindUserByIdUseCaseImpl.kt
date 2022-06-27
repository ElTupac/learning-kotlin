package shop.inventa.template.user.domain.usecase

import org.springframework.stereotype.Service
import shop.inventa.template.user.domain.port.FindUserPort

@Service
class FindUserByIdUseCaseImpl(private val findUserPort: FindUserPort) : FindUserByIdUseCase {
    override fun execute(command: FindUserByIdUseCase.FindUserByIdCommand) = findUserPort.findById(command.id)
}
