package shop.inventa.template.user.adapter.dto.mother

import shop.inventa.template.user.adapter.dto.UserCreationDto

object UserCreationDtoMother {
    fun getValidDto() = UserCreationDto("name", "email@test.com")
}
