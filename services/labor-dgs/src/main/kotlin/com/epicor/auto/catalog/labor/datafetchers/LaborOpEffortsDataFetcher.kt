package com.epicor.auto.catalog.labor.datafetchers

import com.epicor.auto.catalog.labor.generated.DgsConstants
import com.epicor.auto.catalog.labor.generated.types.LaborOpEffort
import com.epicor.auto.catalog.labor.services.LaborOpEffortsService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class LaborOpEffortsDataFetcher(private val laborOpEffortsService: LaborOpEffortsService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.LaborOpEfforts)
    fun laborOpEfforts(@InputArgument("baseVehId") baseVehId : Int?, @InputArgument ("subGroupId") subGroupId : Int?): List<LaborOpEffort> {
        return if ((baseVehId != null) && (subGroupId != null)) {
            laborOpEffortsService.laborOpEfforts(baseVehId, subGroupId)
        } else {
            return ArrayList()
        }
    }

}