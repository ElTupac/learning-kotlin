package shop.inventa.template.user.adapter.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import shop.inventa.template.user.adapter.repository.jpa.entity.UserEntity
import java.util.UUID

@Repository
interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?

    @Query("select u from user_sample u where u.name like ?1%")
    fun findByNameStartsWith(partialName: String): List<UserEntity>

    fun findByUuid(uuid: UUID): UserEntity?
}
