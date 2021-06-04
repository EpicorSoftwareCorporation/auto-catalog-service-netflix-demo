package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.Year
import com.epicor.auto.catalog.vehicle.services.YearsTestService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument


@DgsComponent
class YearsTestDataFetcher(private val yearsTestService: YearsTestService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.YearsTest)

    fun years(@InputArgument("yearFilter") yearFilter : String?): List<Year> {
        return if(yearFilter != null) {
            yearsTestService.yearsTest().filter { it.year?.contains(yearFilter) == true }
        } else {
            yearsTestService.yearsTest()
        }
    }
}