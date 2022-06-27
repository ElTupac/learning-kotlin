package shop.inventa.template.user.adapter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import shop.inventa.template.user.adapter.dto.UserCreationDto
import shop.inventa.template.user.adapter.dto.UserDto
import shop.inventa.template.user.domain.model.User
import shop.inventa.template.user.domain.usecase.CreateUserUseCase
import shop.inventa.template.user.domain.usecase.FindUserByIdUseCase
import javax.validation.Valid

@RestController
@SecurityRequirement(name = "bearer-auth")
@RequestMapping("/v1/users")
class UserController(
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val createUserUseCase: CreateUserUseCase
) {

    @Operation(
        summary = "Creates new user based on provided parameters",
        description = "On success returns CREATED status code",
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "User created successfully"),
            ApiResponse(
                responseCode = "400",
                description = "Input DTO is not valid",
                content = [Content(schema = Schema(hidden = true))]
            ),
        ],
    )
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun createUser(@Valid @RequestBody userDto: UserCreationDto): ResponseEntity<UserDto> {
        val inputUser = User(userDto.name, userDto.email)

        val createdUser: User = this.createUserUseCase.execute(CreateUserUseCase.CreateUserCommand(inputUser))

        return ResponseEntity(UserDto(createdUser), HttpStatus.CREATED)
    }

    @Operation(
        summary = "Fetches user based on unique identifier",
        description = "Returns the user if it exists or NOT FOUND otherwise",
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User was found"),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [Content(schema = Schema(hidden = true))]
            ),
        ]
    )
    @GetMapping("/{userId}", produces = ["application/json"])
    fun getUser(
        @PathVariable(required = true)
        @Schema(description = "User unique id", example = "1", type = "long")
        userId: Long
    ): ResponseEntity<UserDto> {
        val user: User = this.findUserByIdUseCase.execute(FindUserByIdUseCase.FindUserByIdCommand(userId))
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(UserDto(user), HttpStatus.OK)
    }
}
