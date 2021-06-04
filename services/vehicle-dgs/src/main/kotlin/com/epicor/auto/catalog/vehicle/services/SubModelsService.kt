package com.epicor.auto.catalog.vehicle.services

import com.epicor.auto.catalog.vehicle.generated.types.SubModel
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface SubModelsService {
    fun subModels(): List<SubModel>
}

@Service
class BasicSubModelsService : SubModelsService {
    override fun subModels(): List<SubModel> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().hasLabel('SubModel').valueMap(true)"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<SubModel> = ArrayList()
        for (node in result2) {
            System.out.println(node)
            // Load up the Year object
            val tmpSubModId = node.getByKey("SubModelID").toString().replace("[", "")
            val subModId = tmpSubModId.replace("]", "").toInt()

            val tmpSubModTxt = node.getByKey("SubModelName").toString().replace("[", "")
            val subModTxt = tmpSubModTxt.replace("]", "")

            resp.add(SubModel(subModId, subModTxt))
        }
        // Sort in asending order
        resp.sortBy { it.subModelName }

        return resp
    }
}