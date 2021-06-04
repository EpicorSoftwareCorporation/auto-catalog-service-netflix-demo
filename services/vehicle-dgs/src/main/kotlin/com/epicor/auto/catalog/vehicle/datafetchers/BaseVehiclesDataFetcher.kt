package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.BaseVehicle
import com.epicor.auto.catalog.vehicle.services.BaseVehiclesService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument


@DgsComponent
class BaseVehiclesDataFetcher(private val baseVehiclesService: BaseVehiclesService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.BaseVehicles)
    fun baseVehicles(@InputArgument("baseVehId") baseVehId : Int?): List<BaseVehicle> {
        return if(baseVehId != null) {
            baseVehiclesService.baseVehicles().filter { it.baseVehicleId?.equals(baseVehId) == true }
        } else {
            baseVehiclesService.baseVehicles()
        }
    }
}