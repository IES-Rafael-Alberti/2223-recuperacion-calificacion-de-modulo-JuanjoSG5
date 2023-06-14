package models


data class Alumno(  val siglas:String,
                    val nombre: String,
                    var notaMod : Double,
                    var notaRa:MutableList<Double>,
                    var notaCe: MutableList<Double> ,
        )
// cambiar ntoa ra para que almacene listas de sus clases

