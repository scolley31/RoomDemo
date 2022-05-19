package com.scolley.roomdemo.database

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Update
    fun updateUsers(user: UserEntity)

    @Query("SELECT * from user_list_table WHERE id = :key")
    fun getUser(key: Int): UserEntity?

    @Query("SELECT * from user_list_table ORDER BY id DESC")
    fun getAllUsers(): List<UserEntity>?

    @Query("DELETE FROM user_list_table")
    fun clear()

}