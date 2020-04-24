package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.ClipData;
import android.database.Cursor;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task WHERE projectId= :projectId")
    LiveData<List<Task>> getListTask(long projectId);

    @Insert
    long createTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE projectId= :projectId")
    int deleteTask(long projectId);

    @Query("SELECT * FROM Task WHERE id= :id")
    LiveData<Task> getTask(long id);

}