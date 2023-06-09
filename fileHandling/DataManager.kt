package fileHandling

import log.Log
import railway.Result
import railway.Results

object DataManager {
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

    /**
     * Retrieves the rows from the matrix that contain any of the specified elements to calculate.
     *
     * @param matrix The matrix represented as a list of rows, where each row is represented as a list of strings.
     * @param raToCalculate The elements to search for within the rows.
     * @return A list of rows that match the specified elements.
     * If no matching rows are found, an empty list is returned.
     */
    private fun obtainRows(
            matrix: MutableList<MutableList<String>>,
            raToCalculate: MutableList<String>
    ): Result<MutableList<MutableList<String>>, Results> {
        val rowOfResult = mutableListOf<MutableList<String>>()
        matrix.forEach { row ->
            for (crit: String in raToCalculate) {
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


        internal fun filterRowsToSum(rowsToFilter: MutableList<MutableList<Double>>, raInTheCsv: MutableList<Pair<String, Double>>): Result<MutableList<MutableList<Double>>, Results> {
            val test = rowsToFilter.filter { it[0] != 1.0 }.toMutableList()

            for (element in test) {
                val pair = raInTheCsv.find { it.second == element[0] }
                if (pair == null || pair.second != element[0])
                    return Result(test,Results.FAILURE)
            }

            return Result(test,Results.SUCCESSFUL)
        }

    fun filterRows(matrix: MutableList<MutableList<String>>,rowsToFilter:MutableList<String>):
            Result<MutableList<MutableList<String>>, Results> {
        val rows = DataManager.obtainRows(matrix, rowsToFilter)
        if (rows.result == Results.FAILURE) Log.warning("CsvParser.filterRows -> ${rows.result}")
        return Result(matrix.takeLast(3).toMutableList(), Results.SUCCESSFUL)
    }

    }

