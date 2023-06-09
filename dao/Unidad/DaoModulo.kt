package dao.Unidad

import dao.Dao
import log.Log
import models.Modulo
import railway.Result
import railway.Results
import java.sql.SQLException
import javax.sql.DataSource

class DaoModulo(private var dataSource: DataSource):Dao<Modulo,Results> {
    override fun createEntity(modulo: Modulo): Result<Modulo, Results> {
        val sql = "INSERT INTO Unidad (nombre) VALUES (?)"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, modulo.nombre)
                try {
                    Log.info("DaoUnidad.createEntity -> Executing query")
                    stmt.executeUpdate()
            } catch (e: Exception) {
                Log.warning("DaoUnidad.createEntity -> $e")
                return Result(modulo, Results.FAILURE)
            }
                Log.info("DaoUnidad.createEntity -> Query executed")
                return Result(modulo, Results.SUCCESSFUL)
            }
        }
    }

    override fun getById(moduloId: Modulo): Result<Modulo?, Results> {
        val id = moduloId.nombre
        val sql = "SELECT * FROM Unidad WHERE NOMBRE = ?"
        var modulo: Modulo? = null
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, id)
                try {
                    Log.info("DaoUnidad.getById -> Executing query")
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        modulo = Modulo(
                                nombre = rs.getString("nombre")
                        )
                    }
                    Log.info("DaoUnidad.getById -> Query executed")
                    return Result(modulo, Results.SUCCESSFUL)
                } catch (e: Exception) {
                    Log.warning("DaoUnidad.getById -> $e")
                    return Result(modulo, Results.FAILURE)
                }
            }
        }
    }

    override fun getAll(): Result<List<Modulo>, Results> {
        val sql = "SELECT * FROM Unidad"
        val result = mutableListOf<Modulo>()
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                try {
                    Log.info("DaoUnidad.getAll -> Executing query")
                    val resultSet = stmt.executeQuery()
                    while (resultSet.next()) {
                        val nombre = resultSet.getString("nombre")

                        val unid = Modulo(nombre)
                        result.add(unid)
                    }
                    Log.info("DaoUnidad.getAll -> Query executed")
                    return Result(result, Results.SUCCESSFUL)
                } catch (e: SQLException) {
                    Log.warning("DaoUnidad.getAll -> $e")
                    return Result(emptyList(), Results.FAILURE)
                }
            }
        }
    }

    override fun deleteEntity(entity: Modulo): Result<Modulo, Results> {
        val sql = "DELETE FROM Unidad WHERE nombre = ?"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, entity.nombre)
                try {
                    Log.info("DaoUnidad.deleteEntity -> Executing delete")
                    val rowsAffected = stmt.executeUpdate()
                    if (rowsAffected > 0) {
                        Log.info("DaoUnidad.deleteEntity -> $rowsAffected rows deleted")
                        return Result(entity, Results.SUCCESSFUL)
                    } else {
                        Log.info("DaoUnidad.deleteEntity -> 0 rows deleted")
                        return Result(entity, Results.FAILURE)
                    }
                } catch (e: SQLException) {
                    Log.warning("DaoUnidad.deleteEntity -> $e")
                    return Result(entity, Results.FAILURE)
                }
            }
        }
    }
    override fun updateEntity(modulo: Modulo): Result<Modulo, Results> {
        val sql = "UPDATE Unidad SET nombre = ? WHERE nombre = ?"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, modulo.nombre)
                try {
                    Log.info("DaoUnidad.updateEntity -> Executing Update")
                    val rowsAffected = stmt.executeUpdate()
                    if (rowsAffected > 0) {
                        Log.info("DaoUnidad.updateEntity -> $rowsAffected rows updated")
                        return Result(modulo, Results.SUCCESSFUL)
                    } else {
                        Log.info("DaoUnidad.updateEntity -> 0 rows updated")
                        return Result(modulo, Results.FAILURE)
                    }
                }catch (e: SQLException) {
                        Log.warning("DaoUnidad.updateEntity -> $e")
                        return Result(modulo, Results.FAILURE)
                }
            }
        }
    }




    override fun createTable(): Result<Unit, Results> {
        val sql = "CREATE TABLE Unidad (nombre VARCHAR2(50) PRIMARY KEY);"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                try {
                    stmt.executeUpdate()
                    Log.info("Tabla Unidad creada")
                    return Result(Unit, Results.SUCCESSFUL)
                }catch (e: SQLException) {
                        Log.warning("DaoUnidad.createTable -> $e")
                        return Result(Unit, Results.FAILURE)
                    }
            }
        }
    }

    override fun deleteTable(): Result<Unit, Results> {
        val sql = "DROP TABLE Unidad;"
        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                try {
                    stmt.executeUpdate()
                    return Result(Unit, Results.SUCCESSFUL)
                } catch (e: SQLException) {
                    Log.warning("DaoUnidad.deleteTable -> $e")
                    return Result(Unit, Results.FAILURE)
                }
            }
        }

    }

}



