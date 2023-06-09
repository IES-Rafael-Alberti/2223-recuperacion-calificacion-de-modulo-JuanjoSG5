package calculator

import fileHandling.DataManager
import railway.Result
import railway.Results


object ConcreteCalculator:Calculator {
    override val notaRa: MutableList<MutableList<Double>> = mutableListOf()
    override val raInTheCsv: MutableList<Pair<String, Double>> = mutableListOf()
    private val manager = DataManager
    /**
     * Calculates the RA values based on the given matrix and RA rows to calculate.
     *
     * @param matrix The matrix containing the CSV data.
     * @param raToCalculate The RA rows to calculate.
     * @return A [Result] object containing the calculated RA values if successful, or a failure result otherwise.
     */
    fun calculateRa(matrix: MutableList<MutableList<String>>, raToCalculate: MutableList<String>): Result<MutableList<MutableList<Double>>, Results> {
        val rows: MutableList<MutableList<String>> = manager.filterRows(matrix, raToCalculate).obj

        rows.forEachIndexed { _, row ->
            val percentage: Double = convertToPercent(row[manager.getIndexOfPercentage(rows).obj]).obj
            processRaInTheCsv(row[1], percentage)
            val calculatedRow: MutableList<Double> = createCalculatedRow(percentage)

            for (i in 7 until row.size) {
                val element: String = row[i]
                val value: Double? = element.replace(",", ".").toDoubleOrNull()

                if (value != null) {
                    val calculatedValue: Double = calculateValue(value, percentage)
                    calculatedRow.add(calculatedValue)
                } else {
                    return Result(emptyList<MutableList<Double>>().toMutableList(), Results.FAILURE)
                }
            }

            addCalculatedRow(calculatedRow)
        }

        return Result(notaRa, Results.SUCCESSFUL)
    }

    /**
     * Process the RA value and add it to the 'raInTheCsv' list.
     *
     * @param rowValue The value of the current row.
     * @param percentage The calculated percentage for the row.
     */
    private fun processRaInTheCsv(rowValue: String, percentage: Double) {
        raInTheCsv.add(Pair(rowValue, percentage))
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
     * Adds the calculated row to the 'notaRa' list.
     *
     * @param calculatedRow The calculated row to add.
     */
    private fun addCalculatedRow(calculatedRow: MutableList<Double>) {
        notaRa.add(calculatedRow)
    }


    fun addMarks() {
        val rowsToSum: Result<MutableList<MutableList<Double>>, Results> = manager.filterRowsToSum(notaRa, raInTheCsv)
        val newRow: MutableList<Double> = mutableListOf()
        var counter = 0

        if (rowsToSum.result == Results.SUCCESSFUL) {
            while (counter < rowsToSum.obj[0].size) {
                val value = rowsToSum.obj[0][counter] + rowsToSum.obj[1][counter]
                newRow.add(value)
                counter++
            }
        }
        // TODO Find a way to automate this
        notaRa.removeAt(1)
        notaRa.removeAt(1)
        notaRa.add(newRow)
    }






}


