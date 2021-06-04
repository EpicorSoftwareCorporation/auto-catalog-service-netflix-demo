package com.epicor.auto.catalog.vehicle.services

import com.epicor.auto.catalog.vehicle.generated.types.Make
import org.springframework.stereotype.Service

interface MakesTestService {
    fun makesTest(): List<Make>
}

@Service
class BasicMakesTestService : MakesTestService {
    override fun makesTest(): List<Make> {
        return listOf(
                Make(47, "Chevrolet"),
                Make(54, "Ford"),
                Make(78, "Ferrari"),
                Make(1164, "Tesla"),
                Make(27, "Volvo")
        )
    }
}