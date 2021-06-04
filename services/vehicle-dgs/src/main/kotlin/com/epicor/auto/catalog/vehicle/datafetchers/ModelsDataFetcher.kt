package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.Model
import com.epicor.auto.catalog.vehicle.services.ModelsService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class ModelsDataFetcher(private val modelsService: ModelsService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Models)
    fun models(@InputArgument("modelFilter") modelFilter : String?): List<Model> {
        return if(modelFilter != null) {
            modelsService.models().filter { it.modelName?.contains(modelFilter) == true }
        } else {
            modelsService.models()
        }
    }
}