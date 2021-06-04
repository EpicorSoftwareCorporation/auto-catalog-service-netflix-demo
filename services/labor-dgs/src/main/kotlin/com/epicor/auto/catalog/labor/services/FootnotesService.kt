package com.epicor.auto.catalog.labor.services

import com.epicor.auto.catalog.labor.generated.types.Footnote
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession


interface FootnotesService {
    fun footnotes(baseVehId: Int): List<Footnote>
}

@Service
class BasicFootnotesService : FootnotesService {
    override fun footnotes(baseVehId: Int): List<Footnote> {

        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().has('BaseVehicle', 'BaseVehicleID', 8001 ).inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').outE('hasOperationEffort').otherV().outE('hasFootnote').otherV().valueMap();"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<Footnote> = ArrayList()
        for (node in result2) {
            System.out.println(node)

            val tmpFootId = node.getByKey("FootnoteID").toString().replace("[", "")
            val footId = tmpFootId.replace("]", "").toInt()

            val tmpDesc = node.getByKey("FootnoteDescription").toString().replace("[", "")
            val footDesc = tmpDesc.replace("]", "").toString()

            resp.add(Footnote(footId, footDesc))
        }
        // Perform a distinct on Operation Id and then Sort the Description in asending order
        return (resp.distinctBy {it.footnoteID}).sortedBy {it.footnoteID}

    }
}