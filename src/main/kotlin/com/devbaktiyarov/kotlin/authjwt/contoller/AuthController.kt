package com.devbaktiyarov.kotlin.authjwt.contoller;

import com.devbaktiyarov.kotlin.authjwt.contoller.message.ResponseMessage
import com.devbaktiyarov.kotlin.authjwt.dto.LoginDTO
import com.devbaktiyarov.kotlin.authjwt.dto.RegisterDTO;
import com.devbaktiyarov.kotlin.authjwt.model.User
import com.devbaktiyarov.kotlin.authjwt.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
class AuthController(val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user))
    }

    @PostMapping("/login")
    fun login(@RequestBody body: LoginDTO): ResponseEntity<ResponseMessage> {
        val user: User = userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(ResponseMessage("Email or password is incorrect"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(ResponseMessage("Email or password is incorrect"))
        }


        return ResponseEntity.ok(ResponseMessage("Successfully logged in"))
    }
}
