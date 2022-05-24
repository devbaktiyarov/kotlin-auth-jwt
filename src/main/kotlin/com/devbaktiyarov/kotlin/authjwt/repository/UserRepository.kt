package com.devbaktiyarov.kotlin.authjwt.repository

import com.devbaktiyarov.kotlin.authjwt.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findUserByEmail(email: String): User?
}