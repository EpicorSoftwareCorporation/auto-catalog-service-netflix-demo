package com.epicor.auto.catalog.labor.datafetchers

import com.epicor.auto.catalog.labor.generated.DgsConstants
import com.epicor.auto.catalog.labor.generated.types.LaborVertice
import com.epicor.auto.catalog.labor.services.LaborVerticesService
import com.netflix.graphql.dgs.*

@DgsComponent
class LaborVerticesDataFetcher(private val laborVerticesService: LaborVerticesService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.LaborVertices)
    fun laborVertices(@InputArgument("baseVehId") baseVehId : Int?): List<LaborVertice> {
        return if(baseVehId != null) {
            laborVerticesService.laborVertices(baseVehId)
        } else {
            return  ArrayList()
        }
    }

}