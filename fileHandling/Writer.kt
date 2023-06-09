package fileHandling

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
    fun writeCSV(filename: String, matrix: MutableList<MutableList<String>>) {
        File(filename).bufferedWriter().use { writer ->
            for (row in matrix) {
                val line = row.joinToString(",")
                writer.write(line)
                writer.newLine()
            }
        }
    }
}