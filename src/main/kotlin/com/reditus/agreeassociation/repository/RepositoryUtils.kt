package com.reditus.profilegen.infrastructure

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.NoSuchElementException

inline fun <reified T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id)
        ?: throw NoSuchElementException("Resource ${T::class.simpleName} Not Found")
}