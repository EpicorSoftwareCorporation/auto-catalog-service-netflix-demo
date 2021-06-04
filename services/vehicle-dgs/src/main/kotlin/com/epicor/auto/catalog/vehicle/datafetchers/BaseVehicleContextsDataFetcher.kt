package com.epicor.auto.catalog.vehicle.datafetchers

import com.datastax.oss.driver.shaded.fasterxml.jackson.databind.ObjectMapper
import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.BaseVehicle
import com.epicor.auto.catalog.vehicle.services.BaseVehicleContextsService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import org.springframework.http.HttpHeaders


@DgsComponent
class BaseVehicleContextsDataFetcher(private val baseVehicleContextsService: BaseVehicleContextsService) {

        @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.BaseVehicleContexts)
        fun baseVehicleContexts(dfe: DgsDataFetchingEnvironment): List<BaseVehicle> {
            val baseVehId = dfe.arguments.get("baseVehId")
            val response: List<BaseVehicle>

            val headers: HttpHeaders? = dfe.getDgsContext().requestData?.headers
            val vehicleContext = headers?.get("vehicle-context")


            if(baseVehId != null) {
                response = baseVehicleContextsService.baseVehicleContexts().filter { it.baseVehicleId?.equals(baseVehId) == true }
            } else {
                response = baseVehicleContextsService.baseVehicleContexts()
            }

            return response
        }
    }