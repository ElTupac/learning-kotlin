package shop.inventa.template.user.adapter.dto

import java.util.UUID

data class UserEventDto(val event: Event, val uuid: UUID) {
    enum class Event {
        CREATED
    }
}
