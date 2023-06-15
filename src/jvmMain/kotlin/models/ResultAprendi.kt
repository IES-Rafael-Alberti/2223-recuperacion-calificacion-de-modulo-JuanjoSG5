package models

data class ResultAprendi(var id: String , var siglas:String,var nota: Double){
    var notaCe = mutableListOf<criterioEvalu>()
}