package fileHandling

object ParameterManager {
    var folderHandler = FolderHandler
    var module = "PRO"
    var saveToDatabase = false
    var clearDatabase = false
    var queryDatabase = false
    fun checkParameters(args:Array<String>){

        // Parse command-line arguments
        for (i in args.indices) {
            when (args[i]) {
                "-mo" -> module = args[i + 1]
                "-pi" -> folderHandler.getPath(args[i + 1])
                "-bd" -> {
                    saveToDatabase = true
                    if (i + 1 < args.size && args[i + 1] == "d") {
                        clearDatabase = true
                    } else if (i + 1 < args.size && args[i + 1] == "q") {
                        queryDatabase = true
                    }
                }
            }
        }

    }
}