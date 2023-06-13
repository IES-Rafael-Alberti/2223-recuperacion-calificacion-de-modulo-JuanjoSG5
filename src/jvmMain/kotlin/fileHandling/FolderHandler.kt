package fileHandling

import java.io.File

const val path = "src/jvmMain/kotlin/examples"
object FolderHandler {
    private val filePaths = mutableListOf<File>()
    fun obtainFiles(){
        val directoryPth = File(path)
        directoryPth.walk().forEach {
            filePaths.add(it)
        }
    }
}