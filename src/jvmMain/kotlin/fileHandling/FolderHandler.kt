package fileHandling

import java.io.File


object FolderHandler {
     val filePaths = mutableListOf<File>()
    lateinit var directoryPth : File

    fun obtainFiles(){
        directoryPth.walk().forEach {
            filePaths.add(it)
        }
    }
    fun getPath(path:String){
        directoryPth = File(path)
    }
}

