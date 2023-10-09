package dao

import railway.Result

/**
 * The `Dao` interface defines common operations for data access objects.
 *
 * @param T The type of entity.
 * @param U The type of error or failure.
 */
interface Dao<T, U> {
    /**
     * Creates a table to store entities.
     *
     * @return The result of the operation.
     */
    fun createTable(): Result<Unit, U>

    /**
     * Deletes the table that stores entities.
     *
     * @return The result of the operation.
     */
    fun deleteTable(): Result<Unit, U>

    /**
     * Retrieves an entity by its ID.
     *
     * @param entity The entity to retrieve.
     * @return The retrieved entity, or null if not found.
     */
    fun getById(entity: T): Result<T?, U>

    /**
     * Retrieves all entities.
     *
     * @return A list of all entities.
     */
    fun getAll(): Result<List<T>, U>

    /**
     * Creates a new entity.
     *
     * @param entity The entity to create.
     * @return The created entity.
     */
    fun createEntity(entity: T): Result<T, U>

    /**
     * Updates an existing entity.
     *
     * @param entity The entity to update.
     * @return The updated entity.
     */
    fun updateEntity(entity: T): Result<T, U>

    /**
     * Deletes an existing entity.
     *
     * @param entity The entity to delete.
     * @return The deleted entity.
     */
    fun deleteEntity(entity: T): Result<T, U>
}