package com.epicor.auto.catalog.labor.datafetchers

import com.epicor.auto.catalog.labor.generated.DgsConstants
import com.epicor.auto.catalog.labor.generated.types.PartTerminology
import com.epicor.auto.catalog.labor.services.PartTerminologysService

import com.netflix.graphql.dgs.*


@DgsComponent
class PartTerminologysDataFetcher(private val partTerminologysService: PartTerminologysService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.PartTerminologys)
    fun partTerminologys(@InputArgument("baseVehId") baseVehId : Int?, @InputArgument ("subGroupId") subGroupId : Int?): List<PartTerminology> {
        return if((baseVehId != null) && (subGroupId != null) ) {
            partTerminologysService.partTerminologys(baseVehId, subGroupId)
        } else {
            return  ArrayList()
        }
    }
}