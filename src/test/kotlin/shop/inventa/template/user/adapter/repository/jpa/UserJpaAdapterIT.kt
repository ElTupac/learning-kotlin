package shop.inventa.template.user.adapter.repository.jpa

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.jdbc.Sql
import shop.inventa.template.config.annotation.RepositoryIntegrationTest
import shop.inventa.template.user.domain.model.User
import java.util.UUID

@RepositoryIntegrationTest
internal class UserJpaAdapterIT {
    @Autowired
    private lateinit var userJpaAdapter: UserJpaAdapter

    @Test
    @Rollback
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:sql/addUser.sql"])
    fun `findById - should return user when it exists`() {
        // given
        val searchId = 1L

        // when
        val foundUser = userJpaAdapter.findById(searchId)

        assertNotNull(foundUser)
        foundUser!!
        assertAll("found user", {
            assertEquals("Sample", foundUser.name)
            assertEquals("sample@email.com", foundUser.email)
            assertEquals(searchId, foundUser.id)
            assertNotNull(foundUser.uuid)
        })
    }

    @Test
    @Rollback
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:sql/addUser.sql"])
    fun `findById - should return null when user does not exist`() {
        // given
        val searchId = 2L

        // when
        val foundUser = userJpaAdapter.findById(searchId)

        assertNull(foundUser)
    }

    @Test
    @Rollback
    fun `create - should create user as expected`() {
        // given
        val user = User(name = "Sample", email = "sample@email.com")

        // when
        val savedUser = userJpaAdapter.create(user)

        // then
        val foundUser = userJpaAdapter.findById(savedUser.id!!)
        assertNotNull(foundUser)
        foundUser!!
        assertAll("expected user", {
            assertEquals(user.name, foundUser.name)
            assertEquals(user.email, foundUser.email)
            assertNotNull(foundUser.id)
            assertNotNull(foundUser.uuid)
        })
    }

    @Test
    @Rollback
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:sql/addUser.sql"])
    fun `findByEmail - should return user when it exists with specific email`() {
        // given
        val searchEmail = "sample@email.com"

        // when
        val foundUser = userJpaAdapter.findByEmail(searchEmail)

        assertNotNull(foundUser)
        foundUser!!
        assertAll("found user", {
            assertEquals("Sample", foundUser.name)
            assertEquals("sample@email.com", foundUser.email)
            assertEquals(1L, foundUser.id)
            assertNotNull(foundUser.uuid)
        })
    }

    @Test
    @Rollback
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:sql/addUser.sql"])
    fun `findByEmail - should return null when user with email is not found`() {
        // given
        val searchEmail = "non.existent@email.com"

        // when
        val foundUser = userJpaAdapter.findByEmail(searchEmail)

        assertNull(foundUser)
    }

    @Test
    @Rollback
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:sql/addUser.sql"])
    fun `findNameStartsWith - should return user when name starts with provided argument`() {
        // given
        val partialName = "Sam"

        // when
        val foundUsers = userJpaAdapter.findByNameStartsWith(partialName)

        // then
        assertFalse(foundUsers.isEmpty())
    }

    @Test
    @Rollback
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:sql/addUser.sql"])
    fun `findNameStartsWith - should return null when there is no user with name starting with argument`() {
        // given
        val partialName = "ple"

        // when
        val foundUsers = userJpaAdapter.findByNameStartsWith(partialName)

        // then
        assertTrue(foundUsers.isEmpty())
    }

    @Test
    @Rollback
    fun `findUserByUUID - should return user when it exists with specific UUID`() {
        // given
        val savedUser = userJpaAdapter.create(User(name = "Sample", email = "sample@email.com"))

        // when
        val foundUser = userJpaAdapter.findUserByUUID(savedUser.uuid!!)

        // then
        assertNotNull(foundUser)
        foundUser!!
        assertAll("found user", {
            assertEquals(savedUser.name, foundUser.name)
            assertEquals(savedUser.email, foundUser.email)
            assertEquals(savedUser.id, foundUser.id)
            assertEquals(savedUser.uuid, foundUser.uuid)
        })
    }

    @Test
    @Rollback
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:sql/addUser.sql"])
    fun `findUserByUUID - should return null when there is no user with provided UUID`() {
        // given
        val searchUUID = UUID.randomUUID()

        // when
        val foundUser = userJpaAdapter.findUserByUUID(searchUUID)

        // then
        assertNull(foundUser)
    }
}
