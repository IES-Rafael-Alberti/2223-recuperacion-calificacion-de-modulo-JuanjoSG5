package dao

import models.criterioEvalu
import railway.Result
import railway.Results
import javax.sql.DataSource

class DaoCE(private var dataSource: DataSource):Dao<criterioEvalu, Results> {
    override fun createTable(): Result<Unit, Results> {
        TODO("Not yet implemented")
    }

    override fun deleteTable(): Result<Unit, Results> {
        TODO("Not yet implemented")
    }

    override fun getById(entity: criterioEvalu): Result<criterioEvalu?, Results> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Result<List<criterioEvalu>, Results> {
        TODO("Not yet implemented")
    }

    override fun deleteEntity(entity: criterioEvalu): Result<criterioEvalu, Results> {
        TODO("Not yet implemented")
    }

    override fun updateEntity(entity: criterioEvalu): Result<criterioEvalu, Results> {
        TODO("Not yet implemented")
    }

    override fun createEntity(entity: criterioEvalu): Result<criterioEvalu, Results> {
        TODO("Not yet implemented")
    }
}