package com.epicor.auto.catalog.labor.services

import com.epicor.auto.catalog.labor.generated.types.PartTerminology
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface PartTerminologysService {
    fun partTerminologys(baseVehId: Int, subGroupId: Int): List<PartTerminology>
}

@Service
class BasicPartTerminologysService : PartTerminologysService {
    override fun partTerminologys(baseVehId: Int, subGroupId: Int): List<PartTerminology> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().has('BaseVehicle', 'BaseVehicleID', " + baseVehId + " ).inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').outE('hasOperationEffort').otherV().outE('hasOperation').otherV().inE('hasOperation').otherV().has(\"LaborSubGroup\", \"SubGroupID\", " + subGroupId + ").outE('hasOperation').otherV().outE('hasPart').otherV().dedup().valueMap();"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<PartTerminology> = ArrayList()
        for (node in result2) {
            System.out.println(node)

            val tmpPartId = node.getByKey("PartTerminologyID").toString().replace("[", "")
            val partId = tmpPartId.replace("]", "").toInt()

            val tmpName = node.getByKey("Name").toString().replace("[", "")
            val name = tmpName.replace("]", "").toString()

            resp.add(PartTerminology(partId, name))
        }
        // Perform a distinct on Operation Id and then Sort the Description in asending order
        return (resp.distinctBy {it.partTerminologyID}).sortedBy {it.name}

    }
}