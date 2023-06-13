package classCreator

import calculator.ConcreteCalculator
import fileHandling.CsvParser
import fileHandling.DataManager
import fileHandling.Reader

object ClassCreator {
    var reader:Reader =Reader
    fun createAlumno( siglas:String,
                      ){
        var indexColumn: Int = 0
        var notaUd :Double
        var notaRa = mutableListOf<Double>()
        var notaCe = mutableListOf<Double>()
        reader.read("src/jvmMain/kotlin/examples/un1-PlanificaciónyDiario PRO - 2223 -v1 - 3EV Actividades_Instrumentos-vacio.csv")
        reader.matrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, element ->
                if (element == siglas) {
                    indexColumn = columnIndex

                    return@forEachIndexed // Break out of the loop
                }
            }
        }
        println(indexColumn)
        reader.matrix.forEachIndexed { rowIndex, row ->
            for ((index, element) in row.withIndex()) {
                if ((rowIndex == 2) && index ==indexColumn) {
                    notaRa.add(element.toDouble())
                } else if (rowIndex in 4..12 && index ==indexColumn) {
                    notaCe.add(element.toDouble())
                }
            }
        }
        if (notaRa.size == 1){
            notaUd = notaRa[0]
        }else {
            notaUd = ConcreteCalculator.calculateUd(reader.matrix,notaUd).obj
        }
    }
}
fun main(){
    ClassCreator.createAlumno("SBM")
}