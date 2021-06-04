package com.epicor.auto.catalog.labor.services

import com.epicor.auto.catalog.labor.generated.types.LaborVertice
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface LaborVerticesService {
    fun laborVertices(baseVehId: Int): List<LaborVertice>
}

@Service
class BasicLaborVerticesService : LaborVerticesService {
    override fun laborVertices(baseVehId: Int): List<LaborVertice> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().has('BaseVehicle','BaseVehicleID',7933 ).union(inE('hasBaseVehicle').otherV().hasLabel('LaborApplication'), inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').outE('hasOperationEffort').otherV(),inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').outE('hasOperationEffort').otherV().outE('hasOperation').otherV(),inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').outE('hasOperationEffort').otherV().outE('hasEffort').otherV().outE('hasBaseAdditionalDesc').bothV(),    inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').outE('hasPosition').otherV())";

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<LaborVertice> = ArrayList()
        for (node in result2) {
            System.out.println(node)
            val id = (node.asVertex()).id().toString();
            val label = (node.asVertex()).label();
            resp.add(LaborVertice(id, label))
        }
        return resp

    }
}