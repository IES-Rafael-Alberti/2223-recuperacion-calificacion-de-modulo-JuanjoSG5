package calculator

import railway.Result
import railway.Results

interface Calculator {

    val notaCe: MutableList<MutableList<Double>>
    val notaRa: MutableList<MutableList<Double>>
    val ceInTheCsv:MutableList<Pair<String,Double>>
    val udInTheCsv:MutableList<Pair<String,Double>>

     fun convertToPercent(input: String): Result<Double, Results> {
        val numericString = input.replace(Regex("[^\\d.]"), "")
        val numericValue = numericString.toDoubleOrNull() ?: return Result(0.00, Results.FAILURE)

        val decimalValue = numericValue / 100
        return Result(decimalValue, Results.SUCCESSFUL)
    }


    fun deleteRowsWithPercentageOne(): MutableList<MutableList<Double>> {
        val notaRaCopy = ConcreteCalculator.notaCe.toMutableList()
        val rowsToDelete = mutableListOf<MutableList<Double>>()

        for (row in notaRaCopy) {
            val percentage = row[0]
            if (percentage == 1.0) {
                rowsToDelete.add(row)
            }
        }

        notaRaCopy.removeAll(rowsToDelete)
        return notaRaCopy
    }


}