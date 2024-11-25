package hu.bme.aut.backend.cityofguildsbackend.controllers

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.impl.PointService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.UserService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.mockito.AdditionalAnswers.answer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put


@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean private val userService: UserService,
    @MockkBean private val pointService: PointService,
) {

    val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @Test
    fun `Get all points returns all the points`(){

        every{
            pointService.getAllPoints()
        }answers{ listOf(
            PointEntity(id = "1"),
            PointEntity(id = "2"),
            PointEntity(id = "3"),
            PointEntity(id = "4"),
            PointEntity(id = "5"),
        )}

        mockMvc.get("/points/getAll"){
            accept = MediaType.APPLICATION_JSON
        }.andExpect{
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id") { value("1") }
            jsonPath("$[1].id") { value("2") }
            jsonPath("$[2].id") { value("3") }
            jsonPath("$[3].id") { value("4") }
            jsonPath("$[4].id") { value("5") }
        }
    }

    @Test
    fun `Can capture point`(){

        val user = UserEntity(
            id = "tesztID",
        )

        val point = PointEntity(
            id = "1"
        )

        every{
            userService.getUserById(user.id)
        }answers{
            user
        }

        every {
            pointService.capturePoint("1", user)
        }answers{
            true
        }

        mockMvc.put("/points/capture/1"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                user
            )
        }.andExpect {
            status { isOk() }
            content{ string("true") }
        }

    }

    @Test
    fun `Bad request if body is empty and tries to capture point`(){

        val user = UserEntity(
            id = "tesztID",
        )

        val point = PointEntity(
            id = "1"
        )

        every{
            userService.getUserById(user.id)
        }answers{
            user
        }

        every {
            pointService.capturePoint("1", user)
        }answers{
            true
        }

        mockMvc.put("/points/capture/1"){
            contentType = MediaType.APPLICATION_JSON

        }.andExpect {
            status { isBadRequest() }
        }

    }

    @Test
    fun `Not found if user is not found`(){
        val user = UserEntity(
            id = "tesztID",
        )

        val point = PointEntity(
            id = "1"
        )

        every{
            userService.getUserById(user.id)
        }answers{
            null
        }

        every {
            pointService.capturePoint("1", user)
        }answers{
            false
        }

        mockMvc.put("/points/capture/1"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                user
            )

        }.andExpect {
            status { isNotFound() }
        }
    }


    @Test
    fun `Get point by id returns the point by id`(){
        val point = PointEntity(
            id = "1",
            guildname = "TesztGuildName"
        )


        every {
            pointService.getPointById("1")
        }answers{
            point
        }

        mockMvc.get("/points/get/1"){
            contentType = MediaType.APPLICATION_JSON

        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(point.id) }
            jsonPath("$.guildname") { value(point.guildname) }
        }
    }

    @Test
    fun `Add new point returns ok and new point`(){
        val point = PointEntity(
            id = "1",
            guildname = "TesztGuildName"
        )

        every {
            pointService.addPoint(point)
        }answers {
            point
        }


        every {
            pointService.getPointById("1")
        }answers{
            point
        }

        mockMvc.post("/points/addNewPoint"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                point
            )
        }.andExpect {
            status { isOk() }
            jsonPath("$.id") { value(point.id) }
            jsonPath("$.guildname") { value(point.guildname) }
        }


    }




}