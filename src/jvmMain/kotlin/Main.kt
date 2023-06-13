import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

/*
    Box(modifier = Modifier.padding(8.dp)) {
        Column {
            Button(onClick = { expandedCsv = true }) {
                Text(text = "Selecciona una fecha")
            }
            Text(
                text = "Carpeta seleccionada: $fileSeleccionado",
                modifier = Modifier.padding(2.dp)
            )

            DropdownMenu(
                expanded = expandedFecha,
                onDismissRequest = { expandedFecha = false },
                modifier = Modifier.width(105.dp).heightIn(max = 200.dp)
            ) {
                foldersList.forEachIndexed { index, folder ->
                    DropdownMenuItem(onClick = {
                        fechaSeleccionada = folder.name
                        expandedFecha = false
                    }) {
                        Text(folder.name)
                    }
                }
            }
        }
    }

    LaunchedEffect(fechaSeleccionada) {
        val partidas = sacarArchivoPartidas(fechaSeleccionada)
        listaPartidas = partidas
    }*/
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
