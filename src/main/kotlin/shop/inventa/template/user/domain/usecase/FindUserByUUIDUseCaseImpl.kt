package shop.inventa.template.user.domain.usecase

import org.springframework.stereotype.Service
import shop.inventa.template.user.domain.port.FindUserPort

@Service
class FindUserByUUIDUseCaseImpl(private val findUserPort: FindUserPort) : FindUserByUUIDUseCase {
    override fun execute(command: FindUserByUUIDUseCase.FindUserByUUIDCommand) =
        findUserPort.findUserByUUID(command.uuid)
}
