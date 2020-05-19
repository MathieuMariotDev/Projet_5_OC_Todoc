package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getListTask(); /// Use for getListTask LiveData for asynchrone recovery and observer //

    @Insert
    long createTask(Task task); /// Use for create a task  //

    @Update
    void updateTask(Task task); /// Not Use add for eventually update app //

    @Query("DELETE FROM Task WHERE id= :id")  /// Use for delete a task //
    void deleteTask(long id);

    @Query("SELECT * FROM Task WHERE id= :taskId")  ///  Not Use add for eventually update app  //
    LiveData<Task> getTask(long taskId);

}