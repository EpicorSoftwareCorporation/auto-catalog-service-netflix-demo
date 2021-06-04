package com.epicor.auto.catalog.vehicle.services

import com.epicor.auto.catalog.vehicle.generated.types.BaseVehicle
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface BaseVehiclesService {
    fun baseVehicles(): List<BaseVehicle>
}

@Service
class BasicBaseVehiclesService : BaseVehiclesService {
    override fun baseVehicles(): List<BaseVehicle> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().hasLabel('BaseVehicle').valueMap(true)"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<BaseVehicle> = ArrayList()
        for (node in result2) {
            System.out.println(node)

            val tmpBaseVehicleId = node.getByKey("BaseVehicleID").toString().replace("[", "")
            val baseVehicleId = tmpBaseVehicleId.replace("]", "").toInt()

            val tmpYearId = node.getByKey("YearID").toString().replace("[", "")
            val yrId = tmpYearId.replace("]", "").toInt()

            val tmpMkId = node.getByKey("MakeID").toString().replace("[", "")
            val mkId = tmpMkId.replace("]", "").toInt()

            val tmpModId = node.getByKey("ModelID").toString().replace("[", "")
            val modId = tmpModId.replace("]", "").toInt()

            resp.add(BaseVehicle(baseVehicleId,yrId,mkId,modId))
        }
        // Sort in asending order
        resp.sortBy { it.yearId }

        return resp
    }
}