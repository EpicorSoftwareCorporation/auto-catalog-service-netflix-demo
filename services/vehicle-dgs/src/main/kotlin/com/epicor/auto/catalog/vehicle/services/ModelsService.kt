package com.epicor.auto.catalog.vehicle.services

import com.epicor.auto.catalog.vehicle.generated.types.Model
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface ModelsService {
    fun models(): List<Model>
}

@Service
class BasicModelsService : ModelsService {
    override fun models(): List<Model> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().hasLabel('Model').valueMap(true)"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<Model> = ArrayList()
        for (node in result2) {
            System.out.println(node)
            // Load up the Year object
            val tmpModId = node.getByKey("ModelID").toString().replace("[", "")
            val modId = tmpModId.replace("]", "").toInt()

            val tmpModTxt = node.getByKey("ModelName").toString().replace("[", "")
            val modTxt = tmpModTxt.replace("]", "")

            resp.add(Model(modId, modTxt))
        }
        // Sort in asending order
        resp.sortBy { it.modelName }

        return resp
    }
}