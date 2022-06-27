package shop.inventa.template.user.adapter.repository.jpa

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import shop.inventa.template.user.adapter.repository.jpa.entity.UserEntity
import shop.inventa.template.user.domain.model.User
import shop.inventa.template.user.domain.port.CreateUserPort
import shop.inventa.template.user.domain.port.FindUserPort
import java.util.UUID

@Component
class UserJpaAdapter(val userJpaRepository: UserJpaRepository) : FindUserPort, CreateUserPort {

    override fun findById(id: Long) = userJpaRepository.findByIdOrNull(id)?.toDomain()

    override fun create(user: User) = userJpaRepository.save(user.toEntity()).toDomain()

    override fun findByEmail(email: String) = userJpaRepository.findByEmail(email)?.toDomain()

    override fun findByNameStartsWith(partialName: String) =
        userJpaRepository.findByNameStartsWith(partialName).map { it.toDomain() }

    override fun findUserByUUID(uuid: UUID) = userJpaRepository.findByUuid(uuid)?.toDomain()
}

private fun UserEntity.toDomain() = User(
    name,
    email,
    id,
    uuid,
)

private fun User.toEntity() = UserEntity(
    name,
    email,
    id,
    uuid,
)
