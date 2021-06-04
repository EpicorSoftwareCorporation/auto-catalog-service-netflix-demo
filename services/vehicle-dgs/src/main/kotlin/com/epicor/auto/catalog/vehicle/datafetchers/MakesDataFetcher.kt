package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.Make
import com.epicor.auto.catalog.vehicle.services.MakesService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class MakesDataFetcher(private val makesService: MakesService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Makes)
    fun makes(@InputArgument("makeFilter") makeFilter : String?): List<Make> {
        return if(makeFilter != null) {
            makesService.makes().filter { it.makeName?.contains(makeFilter) == true }
        } else {
            makesService.makes()
        }
    }
}