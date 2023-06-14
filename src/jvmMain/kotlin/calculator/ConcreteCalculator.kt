package calculator

import classCreator.ClassCreator
import fileHandling.CsvParser
import fileHandling.DataManager
import railway.Result
import railway.Results


object ConcreteCalculator:Calculator {
    override val notaCe: MutableList<MutableList<Double>> = mutableListOf()
    override val ceInTheCsv: MutableList<Pair<String, Double>> = mutableListOf()
    override val notaRa: MutableList<MutableList<Double>> = mutableListOf()
    private val manager = DataManager
    private var udPercentage = mutableListOf<Double>()

    override val udInTheCsv: MutableList<Pair<String, Double>> = mutableListOf()

    /**
     * Calculates the RA values based on the given matrix and RA rows to calculate.
     *
     * @param matrix The matrix containing the CSV data.
     * @param ceToCalculate The RA rows to calculate.
     * @return A [Result] object containing the calculated RA values if successful, or a failure result otherwise.
     */
    fun calculateCe(matrix: MutableList<MutableList<String>>, ceToCalculate: MutableList<String>): Result<MutableList<MutableList<Double>>, Results> {
        val rows: MutableList<MutableList<String>> = manager.filterCe(matrix, ceToCalculate).obj
        val indexOfPercentage = manager.getIndexOfPercentage(rows)
        when (indexOfPercentage.result){
            Results.FAILURE -> return Result(emptyList<MutableList<Double>>().toMutableList(), Results.FAILURE)
            Results.SUCCESSFUL -> rows.forEach { row ->
                val percentage: Double = convertToPercent(row[manager.getIndexOfPercentage(rows).obj]).obj
                processCeInTheCsv(row[1], percentage)
                val calculatedRow: MutableList<Double> = createCalculatedRow(percentage)
                for (index: Int in indexOfPercentage.obj+1 until row.size) {
                    val element: String = row[index]
                    val value: Double? = element.replace(",", ".").toDoubleOrNull()
                    if (value != null) {
                        val calculatedValue: Double = calculateValue(value, percentage)
                        calculatedRow.add(calculatedValue)
                    } else {
                        return Result(emptyList<MutableList<Double>>().toMutableList(), Results.FAILURE)
                    }
                }
                addCalculatedRowCe(calculatedRow)
            }
        }

        addMarks()

        return Result(notaCe, Results.SUCCESSFUL)
    }

    fun calculateRa(matrix: MutableList<MutableList<String>>, raToCalculate: MutableList<String>): Result<MutableList<Double>, Results> {
        val rows: MutableList<MutableList<String>> = manager.obtainRows(matrix, raToCalculate).obj
        val indexOfPercentage = manager.getIndexOfSecondPercentage(rows)
        when (indexOfPercentage.result){
            Results.FAILURE -> return Result(emptyList<Double>().toMutableList(), Results.FAILURE)
            Results.SUCCESSFUL -> rows.forEach { row ->
                val percentage: Double = convertToPercent(row[manager.getIndexOfSecondPercentage(rows).obj]).obj
                processRaInTheCsv(row[1], percentage)
                val calculatedRow: MutableList<Double> = createCalculatedRow(percentage)
                for (index: Int in indexOfPercentage.obj+3 until row.size) {
                    val element: String = row[index]
                    val value: Double? = element.replace(",", ".").toDoubleOrNull()
                    if (value != null) {
                        val calculatedValue: Double = calculateValue(value, percentage)
                        calculatedRow.add(calculatedValue)
                    } else {
                        return Result(emptyList<Double>().toMutableList(), Results.FAILURE)
                    }
                }
                notaRa.add(calculatedRow)
            }
        }
        val summedRow = addPercentagesUD()

        return Result(summedRow, Results.SUCCESSFUL)
    }



    /**
     * Process the RA value and add it to the 'ceInTheCsv' list.
     *
     * @param rowValue The value of the current row.
     * @param percentage The calculated percentage for the row.
     */
    private fun processCeInTheCsv(rowValue: String, percentage: Double) {
        var criterios = rowValue.split(",")
        for (criterio in criterios){
            ceInTheCsv.add(Pair(criterio, percentage))
        }
    }
    private fun processRaInTheCsv(rowValue: String, percentage: Double) {
        var criterios = rowValue.split(",")
        for (criterio in criterios){
            udInTheCsv.add(Pair(criterio, percentage))
        }
    }

    /**
     * Creates a new calculated row list with the given percentage.
     *
     * @param percentage The calculated percentage for the row.
     * @return The newly created calculated row list.
     */
    private fun createCalculatedRow(percentage: Double): MutableList<Double> {
        val calculatedRow: MutableList<Double> = mutableListOf()
        calculatedRow.add(percentage)
        return calculatedRow
    }

    /**
     * Calculates the value based on the given original value and percentage.
     *
     * @param value The original value from the row.
     * @param percentage The calculated percentage for the row.
     * @return The calculated value.
     */
    private fun calculateValue(value: Double, percentage: Double): Double {
        return if (percentage == 1.0) {
            value
        } else {
            value * percentage
        }
    }

    /**
     * Adds the calculated row to the 'notaCe' list.
     *
     * @param calculatedRow The calculated row to add.
     */
    private fun addCalculatedRowCe(calculatedRow: MutableList<Double>) {
        notaCe.add(calculatedRow)
    }
    private fun addMarks() {
        val rowsToSum: Result<MutableList<MutableList<Double>>, Results> = manager.filterRowsToSumCe(notaCe, ceInTheCsv)
        val newRow: MutableList<Double> = mutableListOf()
        var counter = 0

        if (rowsToSum.result == Results.SUCCESSFUL) {
            while (counter < rowsToSum.obj[0].size) {
                val value = rowsToSum.obj[0][counter] + rowsToSum.obj[1][counter]
                newRow.add(value)
                counter++
            }
        }
        notaCe.remove(rowsToSum.obj[0])
        notaCe.remove(rowsToSum.obj[1])
        notaCe.add(newRow)
    }

    fun calculateUdCsv(matrix: MutableList<MutableList<String>>, notaUd :Double): Result<Double,Results> {
        var rows = DataManager.obtainRows(matrix, CsvParser().ud).obj
        val indexOfPercentage = manager.getIndexOfPercentage(rows)
        when (indexOfPercentage.result){
            Results.FAILURE -> return Result(0.0, Results.FAILURE)
            Results.SUCCESSFUL -> rows.forEach { row ->
                val percentage: Double = convertToPercent(row[manager.getIndexOfSecondPercentage(rows).obj]).obj
                udPercentage.add(percentage)
                for (index: Int in indexOfPercentage.obj+3 until row.size) {
                    val element: String = row[index]
                    val value: Double? = element.replace(",", ".").toDoubleOrNull()
                    if (value != null) {
                        return Result(calculateUd(ClassCreator.udMarks, udPercentage),Results.SUCCESSFUL)
                    } else {
                        return Result(0.0, Results.FAILURE)
                    }
                }
            }
        }
        return Result(0.0, Results.FAILURE)
    }
    fun calculateUd(marks: List<Double>, percentages: List<Double>): Double {
        var finalMark: Double = 0.0
        var totalPercentage = percentages.sum()

        for (i in marks.indices) {
            val mark = marks[i]
            val percentage = percentages[i]
            finalMark += mark * (percentage / 100)
        }

        println(finalMark / totalPercentage * 100)
        return(finalMark / totalPercentage * 100)
    }




    fun addPercentagesUD(): MutableList<Double> {
            val rowsToSum: Result<MutableList<MutableList<Double>>, Results> = manager.filterRowsToSumRa(notaRa, ceInTheCsv)
            var summedRows: MutableList<Double> = mutableListOf()
            var sum = 0.0

            for (column: Int in 0 until rowsToSum.obj[0].size) {
                summedRows.add(sum)
                rowsToSum.obj.forEach { row ->
                    summedRows[column] += row[column]
                }
            }

            // Divide the values by 10
            summedRows = summedRows.map { it / 10 }.toMutableList()

            return summedRows
        }
    }

fun main(){
    val test = mutableListOf<Double>(5.0,10.00 )
    val test2 = mutableListOf<Double>(0.3,0.7 )
    ConcreteCalculator.calculateUd(test,test2)
}

