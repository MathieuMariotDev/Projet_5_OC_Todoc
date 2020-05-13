package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao mTaskDao) { this.mTaskDao = mTaskDao; }

    // --- GET LIST ---

    public LiveData<List<Task>> getListTask(){ return this.mTaskDao.getListTask(); }



    // --- CREATE ---

    public void createTask(Task task){ mTaskDao.createTask(task); }

    // --- DELETE ---
    public void deleteTask(long taskId){ mTaskDao.deleteTask(taskId); }

    // --- UPDATE ---
    public void updateTask(Task task){ mTaskDao.updateTask(task); }

    // --- GET ---
    public LiveData<Task> getTask(long taskId){ return this.mTaskDao.getTask(taskId); }
}
