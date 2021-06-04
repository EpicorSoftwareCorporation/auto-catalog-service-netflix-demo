package com.epicor.auto.catalog.labor.datafetchers

import com.epicor.auto.catalog.labor.generated.DgsConstants
import com.epicor.auto.catalog.labor.generated.types.BaseVehicle
import com.epicor.auto.catalog.labor.generated.types.LaborOperation
import com.epicor.auto.catalog.labor.services.LaborOperationsService
import com.netflix.graphql.dgs.*

@DgsComponent
class LaborOperationsDataFetcher(private val laborOperationsService: LaborOperationsService) {

    @DgsEntityFetcher(name = "BaseVehicle")
    fun baseVehicleEntity(values: Map<Int?, Any?>): BaseVehicle? {
        return BaseVehicle(values["baseVehicleId"] as Int?, null)
    }

    @DgsData(parentType = "BaseVehicle", field = "laborOperations")
    fun laborOperationsFetcher(dfg: DgsDataFetchingEnvironment): List<LaborOperation?> {
        val baseVehicle: BaseVehicle = dfg.getSource()
        return laborOperations(baseVehicle.baseVehicleId)
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.LaborOperations)
    fun laborOperations(@InputArgument("baseVehId") baseVehId : Int?): List<LaborOperation> {
        return if(baseVehId != null) {
            laborOperationsService.laborOperations(baseVehId)
        } else {
            return  ArrayList()
        }
    }

}