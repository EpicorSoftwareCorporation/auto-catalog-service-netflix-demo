package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.Year
import com.epicor.auto.catalog.vehicle.services.YearsService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class YearsDataFetcherr(private val yearsService: YearsService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Years)
    fun years(@InputArgument("yearFilter") yearFilter : String?): List<Year> {
        return if(yearFilter != null) {
            yearsService.years().filter { it.year?.contains(yearFilter) == true }
        } else {
            yearsService.years()
        }
    }
}


