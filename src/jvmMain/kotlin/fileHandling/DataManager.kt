package fileHandling

import calculator.ConcreteCalculator
import log.Log
import railway.Result
import railway.Results
import java.io.File

object DataManager {

    fun updateCsv(path: File){
        var matrix = Reader.read(path.toString())
        FolderHandler.chosenFile = path
        val criterios = CsvParser().critEvalu
        val notaCriterios = ConcreteCalculator.calculateCe(matrix, criterios)
        if (notaCriterios.result == Results.FAILURE){
            Log.warning("DataManager.updateCsv -> calculateCe.FAILURE")
            return
        }
        val matrixToAdd = Writer().convertToCsv(notaCriterios.obj, ConcreteCalculator.ceInTheCsv)
        Writer().writeRaCSV(path.toString(), matrixToAdd)
        matrix.clear()
        matrix = Reader.read(path.toString())
        val resultAprendi = CsvParser().resultAprend
        val notaRa = ConcreteCalculator.calculateRa(matrix, resultAprendi)
        if (notaRa.result == Results.FAILURE){
            Log.warning("DataManager.updateCsv -> calculateRa.FAILURE")
            return
        }
        val matrixToAddUd = Writer().convertUdToCsv(notaRa.obj, ConcreteCalculator.udInTheCsv)
        Writer().writeUdCSV(path.toString(),matrixToAddUd)
    }

    /**
     * Finds the position of the first element in the rows that contains a percentage sign (%).
     *
     * @param rows A list of rows, where each row is represented as a list of strings.
     * @return The index of the first element with a percentage sign. If no element is found, -1 is returned.
     */
    fun getIndexOfPercentage(rows: MutableList<MutableList<String>>): Result<Int, Results> {
        rows.forEach { row ->
            row.forEach { element ->
                if (element.contains('%')) {
                    return Result(row.indexOf(element), Results.SUCCESSFUL)
                }
            }
        }
        return Result(-1, Results.FAILURE)
    }
    fun getIndexOfSecondPercentage(rows: MutableList<MutableList<String>>): Result<Int, Results> {
        for (row in rows) {
            var count = 0
            for ((index, element) in row.withIndex()) {
                if (element.contains('%')) {
                    count++
                    if (count == 2) {
                        return Result(index, Results.SUCCESSFUL)
                    }
                }
            }
        }
        return Result(-1, Results.FAILURE)
    }


    /**
     * Retrieves the rows from the matrix that contain any of the specified elements to calculate.
     *
     * @param matrix The matrix represented as a list of rows, where each row is represented as a list of strings.
     * @param rowsToFind The elements to search for within the rows.
     * @return A list of rows that match the specified elements.
     * If no matching rows are found, an empty list is returned.
     */
    internal fun obtainRows(
        matrix: MutableList<MutableList<String>>,
        rowsToFind: MutableList<String>
    ): Result<MutableList<MutableList<String>>, Results> {
        val rowOfResult = mutableListOf<MutableList<String>>()
        matrix.forEach { row ->
            for (crit: String in rowsToFind) {
                if (row.contains(crit) && !rowOfResult.contains(row)) {
                    rowOfResult.add(row)
                }
            }
        }
        return if (rowOfResult.isNotEmpty()) {
            Result(rowOfResult, Results.SUCCESSFUL)
        } else {
            Result(rowOfResult, Results.FAILURE)
        }
    }


    internal fun filterRowsToSumCe(rowsToFilter: MutableList<MutableList<Double>>, raInTheCsv: MutableList<Pair<String, Double>>): Result<MutableList<MutableList<Double>>, Results> {
        val filteredRows = rowsToFilter.filter { it[0] != 1.0 }.toMutableList()

        for (element in filteredRows) {
            val pair = raInTheCsv.find { it.second == element[0] }
            if (pair == null || pair.second != element[0])
                return Result(filteredRows,Results.FAILURE)
        }

        return Result(filteredRows,Results.SUCCESSFUL)
    }
    internal fun filterRowsToSumRa(rowsToFilter: MutableList<MutableList<Double>>, raInTheCsv: MutableList<Pair<String, Double>>): Result<MutableList<MutableList<Double>>, Results> {
        val filteredRows = rowsToFilter.filter { it[0] != 10.0 }.toMutableList()

        for (element in filteredRows) {
            val pair = raInTheCsv.find { it.second == element[0] }
            if (pair == null || pair.second != element[0])
                return Result(filteredRows,Results.FAILURE)
        }

        return Result(filteredRows,Results.SUCCESSFUL)
    }

    fun filterCe(matrix: MutableList<MutableList<String>>, rowsToFilter:MutableList<String>):
            Result<MutableList<MutableList<String>>, Results> {
        val rows = DataManager.obtainRows(matrix, rowsToFilter)
        if (rows.result == Results.FAILURE) Log.warning("CsvParser.filterCe -> ${rows.result}")
        return Result(matrix.takeLast(rows.obj.size).toMutableList(), Results.SUCCESSFUL)
    }

}

fun main(){
    DataManager.updateCsv(File("src/jvmMain/kotlin/examples/un1-PlanificaciónyDiario PRO - 2223 -v1 - 3EV Actividades_Instrumentos-vacio.csv"))
}

