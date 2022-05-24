package com.devbaktiyarov.kotlin.authjwt.service

import com.devbaktiyarov.kotlin.authjwt.model.User
import java.util.*

interface UserService {
    fun save(user: User): User
    fun findByEmail(email: String): User?
    fun findById(id: Int): Optional<User>
}