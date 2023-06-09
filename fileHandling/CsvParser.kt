package fileHandling

import log.Log
import railway.Result
import railway.Results

class CsvParser {
    val resultAprend = mutableListOf<String>()
    val critEvalu = mutableListOf<String>()
    private val reader =  Reader
    init {
        reader.read("src/jvmMain/kotlin/files/ud1.csv")

        resultAprendFiller()
        critEvaluFiller()
    }



    private fun resultAprendFiller() {
        val regex = Regex("^UD1\\.[a-i]\$")
        reader.matrix.forEach { row ->
            if (regex.matches(row[1])) {
                resultAprend.add(row[1])
            }

        }
    }

    private fun critEvaluFiller() {
        val regex = Regex("^(?!UD1\\.[a-i])[a-z](,[a-z])*$")
        reader.matrix.forEach { row ->
            if (regex.matches(row[1])) {
                println(row[1])
            }
        }
    }


}

