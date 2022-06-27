package shop.inventa.template.user.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

@Schema(description = "DTO used on the user creation endpoint")
data class UserCreationDto(
    @field:Schema(
        description = "User name",
        example = "Sample",
    )
    @field:NotBlank
    val name: String,

    @field:Schema(
        description = "User email",
        example = "sample@email.com",
    )
    @field:NotBlank
    val email: String
)
