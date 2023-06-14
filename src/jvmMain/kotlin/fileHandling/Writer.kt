package fileHandling

import calculator.ConcreteCalculator
import java.io.File


/**
 * Writing data from a  matrix into a CSV file.
 */
class Writer() {
    /**
     * Writes the given matrix to a CSV file.
     *
     * @param filename the name of the file to write to
     * @param matrix the matrix to be written to the file
     */
    private fun writeCSV1(filename: String, matrix: MutableList<MutableList<String>>) {
        File(filename).bufferedWriter().use { writer ->
            for (row in matrix) {
                val line = row.joinToString(",")
                writer.write(line)
                writer.newLine()
            }
        }
    }
    /**
     * Writes the values from [notaRa] into a matrix based on the conditions specified in [raInTheCsv].
     *
     * @param notaRa The values to be written into the matrix.
     * @param raInTheCsv The conditions to determine which values to write.
     * @return The modified matrix with the values from [notaRa] inserted.
     */
    fun convertToCsv(notaRa: MutableList<MutableList<Double>>, raInTheCsv: MutableList<Pair<String, Double>>): MutableList<MutableList<String>> {
        val matrix: MutableList<MutableList<String>> = Reader.matrix
        val filteredRows = DataManager.obtainRows(matrix, CsvParser().resultAprend).obj
        val percentage = DataManager.getIndexOfPercentage(filteredRows)
        val parsedRows: MutableList<MutableList<String>> = mutableListOf()
        for (row in filteredRows) {
            val updatedRow = row.toMutableList()
            var counter = 0
            for (inde in percentage.obj + 2 until row.size) {
                var foundMatch = false
                for (crit in raInTheCsv) {
                    if ((row[1].split('.')[1] == crit.first) && (crit.second == 1.0)) {
                        updatedRow[inde] = notaRa[0][counter].toString()
                        if (updatedRow[inde] == "1.0" ) {
                            updatedRow[inde] = ""
                        }
                        foundMatch = true
                        break
                    }
                }
                if (!foundMatch) {
                    updatedRow[inde] = notaRa[1][counter].toString()

                    if (updatedRow[inde] == "1.0" ) {
                        updatedRow[inde] = ""
                    }
                }
                if (counter >= notaRa[0].size) {
                    counter = 1
                } else {
                    counter++
                }
            }

            parsedRows.add(updatedRow)
        }

        return parsedRows
    }

    /**
     * Writes the contents of the [matrix] into a CSV file with the specified [filename].
     *
     * @param filename The name of the output CSV file.
     * @param matrix The matrix to be written into the CSV file.
     */
    fun writeRaCSV(filename: String, matrix: MutableList<MutableList<String>>) {
        val linesToReplace = 4..matrix.size + 3
        val existingLines = File(filename).readLines()

        val newLines = existingLines.subList(0, linesToReplace.first) +
                matrix.map { row ->
                    row.joinToString(",") { element ->
                        val formattedElement = when {
                            element.toDoubleOrNull() != null -> "\"$element\"" // Encapsulate double values in quotes
                            element.endsWith('%') -> "\"$element\"" // Encapsulate percentages in quotes
                            element.contains(',') -> "\"$element\"" // Encapsulate sentences with commas in quotes
                            else -> element
                        }
                        formattedElement
                    }
                } +
                existingLines.subList(linesToReplace.last + 1, existingLines.size)

        File(filename).bufferedWriter().use { writer ->
            for (element in newLines) {
                writer.write(element)
                writer.newLine()
            }
            writer.flush()
        }
    }

    /**
     * Writes the contents of the [matrix] into a CSV file with the specified [filename],
     * replacing only the second row.
     *
     * @param filename The name of the output CSV file.
     * @param matrix The matrix to be written into the CSV file.
     */
    fun writeUdCSV(filename: String, matrix: MutableList<MutableList<String>>) {
            val existingLines = File(filename).readLines()

            val newLines = existingLines.mapIndexed { index, line ->
                if (index == 2) { // Modify the second row (index 1)
                    val updatedRow = matrix.flatten().joinToString(",") { element ->
                        val formattedElement = when {
                            element.toDoubleOrNull() != null -> "\"$element\"" // Encapsulate double values in quotes
                            element.endsWith('%') -> "\"$element\"" // Encapsulate percentages in quotes
                            element.contains(',') -> "\"$element\"" // Encapsulate strings with commas in quotes
                            else -> element
                        }
                        formattedElement
                    }
                    updatedRow
                } else {
                    line
                }
            }

            File(filename).bufferedWriter().use { writer ->
                newLines.forEach { line ->
                    writer.write(line)
                    writer.newLine()
                }
            }
        }




    fun convertUdToCsv(notaRa: MutableList<Double>, raInTheCsv: MutableList<Pair<String, Double>>): MutableList<MutableList<String>> {
        val matrix: MutableList<MutableList<String>> = Reader.matrix
        val filteredRows = DataManager.obtainRows(matrix, CsvParser().ud).obj
        val percentage = DataManager.getIndexOfPercentage(filteredRows)
        val parsedRows: MutableList<MutableList<String>> = mutableListOf()
        notaRa.removeFirstOrNull()
        for (row in filteredRows) {
            val updatedRow = row.toMutableList()

            for (inde in 8 until row.size) { // Start writing from column index 7
                var foundMatch = false

                for (crit in raInTheCsv) {
                    if (row[1] == crit.first) {
                        updatedRow[inde] = notaRa.firstOrNull()?.toString() ?: "" // Use the first element of notaCe or an empty string if notaCe is empty
                        foundMatch = true
                        break
                    }
                }

                if (!foundMatch) {
                    updatedRow[inde] = notaRa.firstOrNull()?.toString() ?: "" // Use the first element of notaCe or an empty string if notaCe is empty
                }

                notaRa.removeFirstOrNull() // Remove the first element from notaCe

                if (notaRa.isEmpty()) {
                    break // Stop updating if notaCe is empty
                }
            }

            parsedRows.add(updatedRow)
        }

        return parsedRows
    }
}
fun main() {
    val test = mutableListOf("a,b,c,f",
            "d,e,g,h,i",
            "d,e,g,h,i"
    )
    val test2 = mutableListOf("UD1.a",
            "UD1.b",
            "UD1.c",
            "UD1.d",
            "UD1.e",
            "UD1.f",
            "UD1.g",
            "UD1.h",
            "UD1.i")
    val  filename: String = "src/jvmMain/kotlin/examples/un2-PlanificaciónyDiario PRO - 2223 -v1 - 3EV Actividades_Instrumentos-vacio.csv"

    Reader.read(filename)
    val test1 = ConcreteCalculator.calculateCe(Reader.matrix, test)
    val test4 = Writer().convertToCsv(test1.obj, ConcreteCalculator.ceInTheCsv)
    Writer().writeRaCSV(filename, test4)
    val test3 = ConcreteCalculator.calculateRa(Reader.matrix, test2)
    println(test3)
    val test5 = Writer().convertUdToCsv(test3.obj, ConcreteCalculator.udInTheCsv)
    println("-----------------------------")
    println(test5)
    Writer().writeUdCSV(filename,test5)
}


