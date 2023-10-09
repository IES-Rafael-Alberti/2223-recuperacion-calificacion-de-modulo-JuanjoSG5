package log

import java.util.logging.*
/**
 * Log handler
 * Es una clase que recoge la información y los posibles advertencias que puedan surgir a lo largo del programa.
 * @constructor Create empty Log handler crea un objeto del mismo vacío para poder usarlo en cualquier otra parte.
 */
object Log {
    private val logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
    private val fileHandler = FileHandler("log.log", true)
    private val formatter = SimpleFormatter()

    init {
        fileHandler.formatter = formatter
        logger.addHandler(fileHandler)
        logger.level = Level.INFO
    }

    /**
     * Info
     * Función que usaremos para informar de las diferentes funciones o eventos que surjan a lo largo del programa
     * @param message es el mensaje que le pasaremos por parametro al llamar a la función
     */
    fun info(message: String) {
        logger.info(message)

    }



    /**
     * Warning
     * Es una función que usaremos para informar de los posibles fallos que pueda tener el programa a lo largo de la
     * ejecución del mismo
     * @param message es el mensaje que le pasaremos por paramtro a la hora de llamar a la función
     */
    fun warning(message: String) {
        logger.warning(message)
    }
}