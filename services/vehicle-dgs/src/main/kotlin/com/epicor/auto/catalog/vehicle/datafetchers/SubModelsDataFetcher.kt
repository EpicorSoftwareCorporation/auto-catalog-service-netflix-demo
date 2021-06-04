package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.SubModel
import com.epicor.auto.catalog.vehicle.services.SubModelsService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class SubModelsDataFetcher(private val subModelsService: SubModelsService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.SubModels)
    fun subModels(@InputArgument("subModelFilter") subModelFilter : String?): List<SubModel> {
        return if(subModelFilter != null) {
            subModelsService.subModels().filter { it.subModelName?.contains(subModelFilter) == true }
        } else {
            subModelsService.subModels()
        }
    }
}