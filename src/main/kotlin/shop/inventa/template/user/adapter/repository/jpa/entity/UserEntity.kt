package shop.inventa.template.user.adapter.repository.jpa.entity

import shop.inventa.template.common.adapter.jpa.BaseEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.PrePersist
import javax.persistence.Table

@Entity(name = "user_sample")
@Table(name = "user_sample")
class UserEntity(
    var name: String,

    var email: String,

    override var id: Long? = null,

    @get:Column(unique = true, updatable = false, nullable = false)
    var uuid: UUID? = null
) : BaseEntity() {
    @PrePersist
    fun initializeUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID()
        }
    }
}
