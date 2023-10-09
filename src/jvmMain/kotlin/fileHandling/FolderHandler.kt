package fileHandling

import java.io.File


object FolderHandler {
     val filePaths = mutableListOf<File>()
    lateinit var directoryPth : File
    lateinit var chosenFile:File

    private fun obtainFiles(){
        directoryPth.walk().forEach {
            filePaths.add(it)
        }
        filePaths.removeFirstOrNull()
    }
    fun getPath(path:String){
        directoryPth = File(path)
        obtainFiles()
    }
}

