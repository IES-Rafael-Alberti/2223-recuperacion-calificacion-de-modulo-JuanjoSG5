package consoleOutput

import calculator.ConcreteCalculator
import classCreator.ClassCreator
import de.m3y.kformat.table
import fileHandling.ParameterManager


class TableCreator {
    val alumnos = ClassCreator.alumnos
    val resultadosAprendi = ClassCreator.resultadosAprendizaje
    fun createTableOutput(siglasToFind:String){
        val index = alumnos.indexOfFirst { it.siglas == siglasToFind }
            val tabla = table {
                header(
                    alumnos[index].siglas,alumnos[index].nombre

                )

                row(
                    ParameterManager.module,ClassCreator.notaUd
                )

            }
    }
    fun createRaCe(siglasToFind:String) {
        val tabla = table {
            resultadosAprendi.forEachIndexed{index,resultado ->
                header(
                    resultado.id, ConcreteCalculator.udInTheCsv[index].second.toString()
                )

            }
        }
    }
}