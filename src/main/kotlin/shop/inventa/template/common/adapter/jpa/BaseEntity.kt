package shop.inventa.template.common.adapter.jpa

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.envers.Audited
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
@Audited(withModifiedFlag = true)
abstract class BaseEntity {
    @get:[Id GeneratedValue Column(updatable = false, nullable = false)]
    abstract var id: Long?

    @get:[CreationTimestamp Column(updatable = false, nullable = false)]
    var createdAt: LocalDateTime = LocalDateTime.now()

    @get:[UpdateTimestamp Column(nullable = false)]
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
