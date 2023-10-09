package models

data class Modulo(var nombre: String, var siglas : String){
    var notaRa = mutableListOf<ResultAprendi>()
}