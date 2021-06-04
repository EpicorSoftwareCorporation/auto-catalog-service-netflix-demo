package com.epicor.auto.catalog.labor.services

import com.epicor.auto.catalog.labor.generated.types.LaborOperation
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface LaborOperationsService {
    fun laborOperations(baseVehId: Int): List<LaborOperation>
}

@Service
class BasicLaborOperationsService : LaborOperationsService {
    override fun laborOperations(baseVehId: Int): List<LaborOperation> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().has('BaseVehicle', 'BaseVehicleID', " + baseVehId + " ).inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').outE('hasOperationEffort').otherV().outE('hasOperation').otherV().valueMap();"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<LaborOperation> = ArrayList()
        for (node in result2) {
            System.out.println(node)

            val tmpOpId = node.getByKey("OperationID").toString().replace("[", "")
            val opId = tmpOpId.replace("]", "").toInt()

            val tmpDesc = node.getByKey("Description").toString().replace("[", "")
            val desc = tmpDesc.replace("]", "").toString()

            val tmpLiteralName = node.getByKey("LiteralName").toString().replace("[", "")
            val lateralName = tmpLiteralName.replace("]", "").toString()

            resp.add(LaborOperation(opId, desc,lateralName))
        }
        // Perform a distinct on Operation Id and then Sort the Description in asending order
        return (resp.distinctBy {it.operationID}).sortedBy {it.description}

    }
}