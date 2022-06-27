package shop.inventa.template.user.domain.usecase

import org.springframework.stereotype.Service
import shop.inventa.template.user.domain.port.FindUserPort

@Service
class FindUsersByNameStartingWithUseCaseImpl(private val findUserPort: FindUserPort) :
    FindUsersByNameStartingWithUseCase {
    override fun execute(command: FindUsersByNameStartingWithUseCase.FindUsersByNameStartingWithCommand) =
        findUserPort.findByNameStartsWith(command.partialName)
}
