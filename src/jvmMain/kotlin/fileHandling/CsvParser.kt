package fileHandling

import log.Log
import railway.Result
import railway.Results


class CsvParser {
    val resultAprend = mutableListOf<String>()
    val critEvalu = mutableListOf<String>()
    val ud =  mutableListOf<String>()
    private val reader =  Reader
    val filterUd = Reader.matrix[2][0]
    init {
        criteriosFiller()
        resultAprendFiller()
        udFiller()
    }


    /**
     * Finds the Learning Results
     */
    private fun criteriosFiller() {
        val regex = Regex("""^\w(?:,\w){0,7}$""")

        reader.matrix.forEach { row ->
            if (regex.matches(row[1])) {
                critEvalu.add(row[1])
            }

        }
    }
    private fun resultAprendFiller() {
        val regex = Regex("^${filterUd}\\.[a-i]\$")
        reader.matrix.forEach { row ->
            if (regex.matches(row[1])) {
                resultAprend.add(row[1])
            }

        }
    }



    private fun udFiller() {
            for (row in reader.matrix) {
                for (cell in row) {
                    if (cell == filterUd) {
                        ud.add(cell)
                    }
                }
            }
    }





}

