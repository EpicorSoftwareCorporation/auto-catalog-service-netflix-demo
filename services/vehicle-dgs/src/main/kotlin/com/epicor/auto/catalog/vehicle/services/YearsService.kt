package com.epicor.auto.catalog.vehicle.services

import com.epicor.auto.catalog.vehicle.generated.types.Year
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface YearsService {
    fun years(): List<Year>
}

@Service
class BasicYearsService : YearsService {
    override fun years(): List<Year> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().hasLabel('Year').valueMap(true)"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<Year> = ArrayList()
        for (node in result2) {
            System.out.println(node)
            // Load up the Year object
            val tmpYrId = node.getByKey("YearID").toString().replace("[", "")
            val yrId = tmpYrId.replace("]", "").toInt()

            val tmpYrTxt = node.getByKey("Year").toString().replace("[", "")
            val yrTxt = tmpYrTxt.replace("]", "")

            resp.add(Year(yrId, yrTxt))
        }
        // Sort in desending order
        resp.sortByDescending { it.yearId }

        return resp
    }
}