package hu.bme.aut.backend.cityofguildsbackend.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.impl.PointService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.UserService
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import kotlin.test.Test


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean private val userService: UserService,
    @MockkBean private val pointService: PointService,
) {

    val objectMapper = jacksonObjectMapper()

    @Test
    fun `Create user returns 201 Created`(){
        every {
            userService.save(any())
        }answers{
            firstArg()
        }


        mockMvc.put("/users/addNewUser") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UserEntity(
                    id = "testID",
                    numberOfPoints = 5,
                    isDeveloper = false
                )
            )
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `Create user saves the new user`(){
        every {
            userService.save(any())
        }answers{
            firstArg()
        }

        mockMvc.put("/users/addNewUser") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UserEntity(
                    id = "testID",
                    numberOfPoints = 5,
                    isDeveloper = false
                )
            )
        }
        val expected = UserEntity(
                id = "testID",
                numberOfPoints = 5,
                isDeveloper = false
            )
        verify { userService.save(expected) }
    }

    @Test
    fun `Create user returns the new user`(){
        every {
            userService.save(any())
        }answers{
            firstArg()
        }

        mockMvc.put("/users/addNewUser") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UserEntity(
                    id = "testID",
                    numberOfPoints = 5,
                    isDeveloper = false
                )
            )
        }.andExpect {
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id"){value("testID")}
            jsonPath("$.numberOfPoints"){value("5")}
            jsonPath("$.isDeveloper"){value(false)}
        }
    }

    @Test
    fun `Get user by id returns user and OK`(){
        val expected = UserEntity(
            id = "testID",
            numberOfPoints = 5,
            isDeveloper = false
        )
        every { userService.getUserById("testID")
        }answers{
            expected
        }

        mockMvc.get("/users/testID") {

        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id"){value("testID")}
            jsonPath("$.numberOfPoints"){value("5")}
            jsonPath("$.isDeveloper"){value(false)}
        }


    }

    @Test
    fun `Get User points returns ok and a list of user's points`(){
        val user = UserEntity(
            id = "testID",
            password = "Haha, no",
        )

        every { userService.getUserById("testID")
        }answers{
            user
        }

        every{
            pointService.getAllUserPoints(user)
        }answers{
            listOf(
                PointEntity(id = "1"),
                PointEntity(id = "2"),
                PointEntity(id = "3"),
                PointEntity(id = "4"),
                PointEntity(id = "5"),
            )
        }
        mockMvc.get("/users/usersPoints/testID") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id") { value("1") }
            jsonPath("$[1].id") { value("2") }
            jsonPath("$[2].id") { value("3") }
            jsonPath("$[3].id") { value("4") }
            jsonPath("$[4].id") { value("5") }
        }

    }

}