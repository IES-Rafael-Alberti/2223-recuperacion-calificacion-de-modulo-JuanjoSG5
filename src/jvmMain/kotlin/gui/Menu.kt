package gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import classCreator.ClassCreator
import fileHandling.DataManager
import fileHandling.FolderHandler
import fileHandling.ParameterManager
import fileHandling.Reader
import java.io.File
class Menu(){
    var siglas = mutableListOf<String>()
    var nombre = mutableListOf<String>()
    init{
        FolderHandler.filePaths.forEach { path ->
            DataManager.updateCsv(path)
        }
        siglas.addAll(Reader.matrix[0].subList(8,Reader.matrix[0].size))
        siglas.addAll(Reader.matrix[1].subList(8,Reader.matrix[0].size))
    }
    @Composable
    @Preview
    fun App(
            saveToDatabase: Boolean,
            clearDatabase: Boolean,
            queryDatabase: Boolean
    ) {
        var text by remember { mutableStateOf("Hello, World!") }
        var expandedCsv by remember { mutableStateOf(false) }
        var expandedMenu by remember { mutableStateOf(false) }
        var currentAlumno by remember { mutableStateOf("") }
        var indexAlumno = 0
        var siglas = remember { mutableStateListOf<String>() }
        var saveToDatabase by remember { mutableStateOf(saveToDatabase) }
        var clearDatabase by remember { mutableStateOf(clearDatabase) }
        var queryDatabase by remember { mutableStateOf(queryDatabase) }
    
        Box(modifier = Modifier.padding(8.dp)) {
            Column {
                Button(onClick = { expandedCsv = true
                    expandedMenu = true  }) {
                    Text(text = "Selecciona un Alumno")
                }
                DropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false },
                        modifier = Modifier.width(205.dp).heightIn(max = 200.dp)
                ) {
                    siglas.forEachIndexed { index, alumno ->
                        DropdownMenuItem(onClick = {
                            currentAlumno = alumno
                            indexAlumno = index
                            expandedMenu = false
                        }) {
                            Text(alumno)
                        }
                    }
                }
                Text(
                        text = "Inicial Alumno : $currentAlumno",
                        modifier = Modifier.padding(2.dp)
                )
                Text(
                        text = "Nombre Alumno : ${nombre[indexAlumno]}",
                        modifier = Modifier.padding(2.dp)
                )
                Row(){
                    Text(
                            text = "Modulo: ${ParameterManager.module}",
                            modifier = Modifier.padding(2.dp)
                    )
                    Text(
                            text = "Nota: ${ClassCreator.notaUd}",
                            modifier = Modifier.padding(2.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Row{
                    Text(
                        text = Reader.matrix[2][1],
                                modifier = Modifier.padding(2.dp)
                    )
                    Text(text = "%",
                        modifier = Modifier.padding(2.dp)
                    )
                    Text(
                        text = "nota" + ClassCreator.notaRa[FolderHandler.filePaths.indexOf(FolderHandler.chosenFile)]
                    )
                }
            }
        }
    
    
    }
}

