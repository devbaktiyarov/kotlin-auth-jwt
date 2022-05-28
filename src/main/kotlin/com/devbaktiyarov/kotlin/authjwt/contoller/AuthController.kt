package com.devbaktiyarov.kotlin.authjwt.contoller;

import com.devbaktiyarov.kotlin.authjwt.contoller.message.ResponseMessage
import com.devbaktiyarov.kotlin.authjwt.dto.LoginDTO
import com.devbaktiyarov.kotlin.authjwt.dto.RegisterDTO;
import com.devbaktiyarov.kotlin.authjwt.model.User
import com.devbaktiyarov.kotlin.authjwt.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1")
class AuthController(val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<ResponseMessage> {
        userService.findByEmail(body.email)?.let {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseMessage("User with ${body.email} already exists"))
        }
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseMessage("User registered successfully"))
    }

    @PostMapping("/login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<ResponseMessage> {
        val user: User = userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(ResponseMessage("Email or password is incorrect"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(ResponseMessage("Email or password is incorrect"))
        }

        val jwt = Jwts.builder()
            .setIssuer(user.id.toString())
            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, "kotlin-auth-key").compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        return ResponseEntity.ok(ResponseMessage("User logged in successfully"))
    }

    @GetMapping("/user")
    fun getUser(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        jwt?.let {
            val body = Jwts.parser().setSigningKey("kotlin-auth-key").parseClaimsJws(jwt).body
            return ResponseEntity.ok(userService.findById(body.issuer.toInt()))
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage("User unauthenticated"))
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<ResponseMessage> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.ok(ResponseMessage("User logged out successfully"))
    }

}
