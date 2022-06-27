package shop.inventa.template.user.domain.port

import java.util.UUID

interface UserEventPort {
    fun created(uuid: UUID)
}
