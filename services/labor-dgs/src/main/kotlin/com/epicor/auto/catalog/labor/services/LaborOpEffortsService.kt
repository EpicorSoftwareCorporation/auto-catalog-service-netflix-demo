package com.epicor.auto.catalog.labor.services

import com.epicor.auto.catalog.labor.generated.types.LaborOpEffort
import org.springframework.stereotype.Service

import com.datastax.dse.driver.api.core.graph.GraphResultSet
import com.datastax.dse.driver.api.core.graph.ScriptGraphStatement
import com.datastax.oss.driver.api.core.CqlSession
import com.epicor.auto.catalog.labor.generated.types.Effort
import com.epicor.auto.catalog.labor.generated.types.LaborOperation


interface LaborOpEffortsService {
    fun laborOpEfforts(baseVehId: Int, subGroupId: Int): List<LaborOpEffort>
}

@Service
class BasicLaborOpEffortsService : LaborOpEffortsService {
    override fun laborOpEfforts(baseVehId: Int, subGroupId: Int): List<LaborOpEffort> {
        // Connects to the default localhost DataStax DB running on my office laptop
        val session: CqlSession = CqlSession.builder().withKeyspace("cobra").build()
        val script2 = "g.V().has('BaseVehicle', 'BaseVehicleID', " + baseVehId + ").inE('hasBaseVehicle').otherV().hasLabel('LaborApplication').as('app').outE('hasOperationEffort').otherV().as('opeffort').outE('hasOperation').otherV().as('op').inE('hasOperation').otherV().has(\"LaborSubGroup\", \"SubGroupID\", " + subGroupId + ").project('Operation','Effort').by(select('op').valueMap()).by(select('opeffort').outE('hasEffort').otherV().valueMap())"

        val statement: ScriptGraphStatement =  //ScriptGraphStatement.builder(script2).build().setGraphName("user_graph");
                ScriptGraphStatement.builder(script2).build().setGraphName("cobra")
        val result2: GraphResultSet = session.execute(statement)

        val resp: MutableList<LaborOpEffort> = ArrayList()

        for (node in result2) {
            System.out.println(node)
            val operations: MutableList<LaborOperation> = ArrayList()
            val efforts: MutableList<Effort> = ArrayList()

            val op = node.getByKey("Operation")
            val tmpOpId = op.getByKey("OperationID").toString().replace("[", "")
            val opId = tmpOpId.replace("]", "").toInt()
            val tmpDesc = op.getByKey("Description").toString().replace("[", "")
            val desc = tmpDesc.replace("]", "").toString()
            val tmpLiteralName = op.getByKey("LiteralName").toString().replace("[", "")
            val lateralName = tmpLiteralName.replace("]", "").toString()
            operations.add(LaborOperation(opId,desc,lateralName))

            val eff = node.getByKey("Effort")
            val tmpEffId = eff.getByKey("EffortGUID").toString().replace("[", "")
            val effId = tmpEffId.replace("]", "").toString()
            val tmpSC = eff.getByKey("SkillCode").toString().replace("[", "")
            val sc = tmpSC.replace("]", "").toString()
            val tmpBaseWork = eff.getByKey("BaseEstWorkTime").toString().replace("[", "")
            val baseWork = tmpBaseWork.replace("]", "").toString()
            efforts.add(Effort(effId,sc,baseWork))

            resp.add(LaborOpEffort(operations, efforts))

        }
            return resp

        // Perform a distinct on Operation Id and then Sort the Description in asending order
        //return (resp.distinctBy {it.operationID}).sortedBy {it.description}

    }
}