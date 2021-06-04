package com.epicor.auto.catalog.vehicle.datafetchers

import com.epicor.auto.catalog.vehicle.generated.DgsConstants
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestHeader

@DgsComponent
class RequestHeadersDataFetcher {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = "headers")
    fun headers(dfe: DgsDataFetchingEnvironment): String {
        val headers: HttpHeaders? = dfe.getDgsContext().requestData?.headers
        return headers.toString()
    }

    @DgsData(parentType = "Query", field = "referer")
    fun referer(@RequestHeader referer: List<String?>): String? {
        return referer.toString()
    }

}