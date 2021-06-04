package com.epicor.auto.catalog.vehicle.services

import com.epicor.auto.catalog.vehicle.generated.types.Year
import org.springframework.stereotype.Service

interface YearsTestService {
    fun yearsTest(): List<Year>
}

@Service
class BasicYearsTestService : YearsTestService {
    override fun yearsTest(): List<Year> {
        return listOf(
                Year(2000, "2000"),
                Year(2001, "2001"),
                Year(2002, "2002"),
                Year(2003, "2003"),
                Year(2004, "2004")
        )
    }
}