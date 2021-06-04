package com.epicor.auto.catalog.vehicle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VehicleApplication

fun main(args: Array<String>) {
	runApplication<VehicleApplication>(*args)
}
