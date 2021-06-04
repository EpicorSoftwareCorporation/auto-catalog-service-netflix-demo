package com.epicor.auto.catalog.vehicle.services

import com.epicor.auto.catalog.vehicle.generated.types.Make
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface MakesService {
    fun makes(): List<Make>
}

@Service
class BasicMakesService : MakesService {
    override fun makes(): List<Make> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().hasLabel('Make').valueMap(true)"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<Make> = ArrayList()
        for (node in result2) {
            System.out.println(node)
            // Load up the Year object
            val tmpMkId = node.getByKey("MakeID").toString().replace("[", "")
            val mkId = tmpMkId.replace("]", "").toInt()

            val tmpMkTxt = node.getByKey("MakeName").toString().replace("[", "")
            val mkTxt = tmpMkTxt.replace("]", "")

            resp.add(Make(mkId, mkTxt))
        }
        // Sort in asending order
        resp.sortBy { it.makeName }

        return resp
    }
}