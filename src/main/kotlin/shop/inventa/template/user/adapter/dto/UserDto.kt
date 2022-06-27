package shop.inventa.template.user.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema
import shop.inventa.template.user.domain.model.User

@Schema(description = "DTO used to return the user information")
data class UserDto(
    @field:Schema(
        description = "User unique id",
        type = "long",
        minimum = "1"
    )
    val id: Long?,

    @field:Schema(
        description = "User name",
        example = "Sample",
    )
    val name: String,

    @field:Schema(
        description = "User email",
        example = "sample@email.com",
    )
    val email: String
) {
    constructor(user: User) : this(user.id, user.name, user.email)
}
