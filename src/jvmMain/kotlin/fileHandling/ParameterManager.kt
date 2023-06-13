package fileHandling

object ParameterManager {
    fun checkParameters(args:Array<String>){
        var module = "PRO"
        var folderPath = ""
        var saveToDatabase = false
        var clearDatabase = false
        var queryDatabase = false
        var folderHandler = FolderHandler

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