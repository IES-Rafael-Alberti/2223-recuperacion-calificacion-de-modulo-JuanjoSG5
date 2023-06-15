package classCreator

import calculator.ConcreteCalculator
import fileHandling.CsvParser
import fileHandling.DataManager
import fileHandling.ParameterManager
import fileHandling.Reader
import models.Alumno
import models.Modulo
import models.ResultAprendi
import models.criterioEvalu
import railway.Result
import railway.Results

object ClassCreator {
    private var reader:Reader =Reader
    var alumnos = mutableListOf<Alumno>()
    var resultadosAprendizaje = mutableListOf<ResultAprendi>()
    var criteriosEvalu = mutableListOf<criterioEvalu>()
    var udMarks = mutableListOf<Double>()
    var notaRa = mutableListOf<Double>()
    var notaCe = mutableListOf<Double>()
    var notaUd :Double = 0.0
    fun createAlumno( siglas:String){
        var indexColumn: Int = 0
        var nombre :String =""

        reader.read("src/jvmMain/kotlin/examples/un1-PlanificaciónyDiario PRO - 2223 -v1 - 3EV Actividades_Instrumentos-vacio.csv")
        reader.matrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, element ->
                if (element == siglas) {
                    indexColumn = columnIndex
                    nombre = row[indexColumn]
                    return@forEachIndexed // Break out of the loop
                }
            }
        }
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
            udMarks.add(notaUd)
        }else {
            notaUd = ConcreteCalculator.calculateUdCsv(reader.matrix,notaUd).obj
        }
        alumnos.add(Alumno(siglas,nombre,notaUd,  notaRa,notaCe))
    }
    fun createCriterioEvalu(siglas:String):Result<MutableList<criterioEvalu>,Results> {
        var ids = CsvParser().resultAprend
        var counter = 0
        while (counter < notaCe.size){
            criteriosEvalu.add(criterioEvalu(ids[counter],siglas, notaCe[counter]))
            counter++
        }
        return Result(criteriosEvalu,Results.SUCCESSFUL)
    }
    fun createResultadoAprend(siglas:String):Result<MutableList<ResultAprendi>,Results> {
        var ids = CsvParser().ud
        var counter = 0
        while (counter < notaRa.size){
            var result = ResultAprendi(ids[counter],siglas, notaRa[counter])
            result.notaCe = criteriosEvalu
            resultadosAprendizaje.add(result )
            counter++
        }
        return Result(resultadosAprendizaje,Results.SUCCESSFUL)
    }
    fun createModulo(siglas:String):Result<Modulo,Results> {
        var ids = ParameterManager.module


        var modulo  = Modulo(ids,siglas)
        modulo.notaRa = resultadosAprendizaje

        return Result(modulo,Results.SUCCESSFUL)
    }
}
fun main(){
    val test = mutableListOf("a,b,c,f",
            "d,e,g,h,i",
            "d,e,g,h,i"
    )
    ClassCreator.createAlumno("SBM")
    ClassCreator.createCriterioEvalu("SBM")
    ClassCreator.createResultadoAprend("SBM")
}