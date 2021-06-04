package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.epicor.auto.catalog.vehicle.generated.types.Make
import com.epicor.auto.catalog.vehicle.services.MakesTestService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument


@DgsComponent
class MakesTestDataFetcher(private val makesTestService: MakesTestService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.MakesTest)
    fun makes(@InputArgument("makeFilter") makeFilter : String?): List<Make> {
        return if(makeFilter != null) {
            makesTestService.makesTest().filter { it.makeName?.contains(makeFilter) == true }
        } else {
            makesTestService.makesTest()
        }
    }
}