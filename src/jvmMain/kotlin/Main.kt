import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import fileHandling.FolderHandler
import fileHandling.ParameterManager

import gui.Menu
import java.io.File




fun main(args: Array<String>){
    ParameterManager.checkParameters(args)
    var test = application{
        Window(onCloseRequest = ::exitApplication) {
            ParameterManager.checkParameters(args)
            if (args.isNotEmpty()) {
                Menu().App(ParameterManager.saveToDatabase, ParameterManager.clearDatabase, ParameterManager.queryDatabase)
            }
        }
    }
}



