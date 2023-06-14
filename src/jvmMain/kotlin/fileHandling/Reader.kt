package fileHandling

import log.Log
import java.io.File

/**
 * Provides functionality to read data from a CSV file.
 */
object Reader {
    var matrix: MutableList<MutableList<String>> = mutableListOf()

    /**
     * Reads the contents of a CSV file located at the given [path] and returns the data as a mutable list of mutable lists.
     * Each inner list represents a row in the CSV file, and each element in the inner list represents a cell value.
     *
     * @param path The path to the CSV file.
     * @return The data from the CSV file as a mutable list of mutable lists.
     */
    fun read(path: String): MutableList<MutableList<String>> {
        File(path).bufferedReader().use { reader ->
            Log.info("Reader.read -> Starting to read the CSV file")
            var line = reader.readLine()
            while (line != null) {
                val row = parseRow(line)
                matrix.add(row)
                Log.info("Reader.read -> Added a row of data from the CSV file")
                line = reader.readLine()
            }
        }
        return matrix
    }

    /**
     * Parses a single row from a CSV file line and returns it as a mutable list of cell values.
     *
     * @param line The line from the CSV file.
     * @return The row parsed from the CSV line.
     */
    private fun parseRow(line: String): MutableList<String> {
        val row = mutableListOf<String>()
        val currentCell = StringBuilder()

        var i = 0
        Log.info("Reader.parseRow -> Parsing a row of data")
        while (i < line.length) {
            when (val currentChar = line[i]) {
                ',' -> {
                    addCellToRow(row, currentCell)
                    currentCell.clear()
                }
                '"' -> {
                    val quotedValue = extractQuotedValue(line, i)
                    currentCell.append(quotedValue)
                    i += quotedValue.length + 1
                }
                else -> currentCell.append(currentChar)
            }
            i++
        }
        addCellToRow(row, currentCell)
        Log.info("Reader.parseRow -> Finished parsing a row of data")
        return row
    }

    /**
     * Extracts the value within quotes from a CSV line, starting from the given [startIndex].
     *
     * @param line The line from the CSV file.
     * @param startIndex The index where the quoted value starts.
     * @return The extracted quoted value.
     */
    private fun extractQuotedValue(line: String, startIndex: Int): String {
        Log.info("Reader.extractQuotedValue -> Extracting the value enclosed in quotes")
        val endIndex = line.indexOf('"', startIndex + 1)
        return line.substring(startIndex + 1, endIndex)
    }

    /**
     * Adds a cell value to a row after trimming it and converting it to a string.
     *
     * @param row The row to which the cell value will be added.
     * @param cell The cell value.
     */
    private fun addCellToRow(row: MutableList<String>, cell: StringBuilder) {
        Log.info("Reader.addCellToRow -> Adding a cell value to the row")
        row.add(cell.toString().trim())
    }
}


