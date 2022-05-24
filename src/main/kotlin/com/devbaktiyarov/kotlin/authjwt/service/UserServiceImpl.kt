package com.devbaktiyarov.kotlin.authjwt.service

import com.devbaktiyarov.kotlin.authjwt.model.User
import com.devbaktiyarov.kotlin.authjwt.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(val userRepository: UserRepository) : UserService {
    override fun save(user: User): User {
        return userRepository.save(user)
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findUserByEmail(email)
    }

    override fun findById(id: Int): Optional<User> {
        return userRepository.findById(id)
    }
}