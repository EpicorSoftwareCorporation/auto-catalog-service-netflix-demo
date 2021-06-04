package com.epicor.auto.catalog.labor.datafetchers

import com.epicor.auto.catalog.labor.generated.DgsConstants
import com.epicor.auto.catalog.labor.generated.types.Footnote
import com.epicor.auto.catalog.labor.services.FootnotesService
import com.netflix.graphql.dgs.*

@DgsComponent
class FootnotesDataFetcher(private val footnotesService: FootnotesService) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Footnotes)
    fun footnotes(@InputArgument("baseVehId") baseVehId : Int?): List<Footnote> {
        return if(baseVehId != null) {
            footnotesService.footnotes(baseVehId)
        } else {
            return  ArrayList()
        }
    }

}