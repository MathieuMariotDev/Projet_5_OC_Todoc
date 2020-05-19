package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project WHERE id = :projectId") /// Use for instru TEST///// Not Use add for eventually update app //
    LiveData<Project> getProject(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getListProject(); // LiveData for async + observe /// --- List for Spinner and display name and picture project --- Use //

    @Insert(onConflict = OnConflictStrategy.REPLACE) ///Use for instru TEST///// Not Use in app add for eventually update //
    void createProject(Project project);

    @Query("DELETE FROM Project WHERE id= :id")  /// Not Use add for eventually update app //
    void deleteProject(long id);

    @Update
    void updateProject(Project project); /// Not Use add for eventually update app //
}