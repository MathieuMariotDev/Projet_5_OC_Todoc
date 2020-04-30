package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {


    // Repositories
    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDataSource;
    private final Executor mExecutor; // for background execution

    // DATA
    //@Nullable
    private List<Project> currentProject = new ArrayList<>();

    public TaskViewModel(TaskDataRepository mTaskDataSource, ProjectDataRepository mProjectDataSource, Executor mExecutor) { //Constructor
        this.mTaskDataSource = mTaskDataSource;
        this.mProjectDataSource = mProjectDataSource;
        this.mExecutor = mExecutor;
    }

    /*public void init(long userId) {
        if (this.currentProject != null) {
            return;
        }
        currentProject = mProjectDataSource.getUser(userId);
    }*/

    public LiveData<Project[]>  getListProject() { //LiveData for asynchron recovery and observe notify update
        Log.d("Debug", "getProjectList TaskViewModel ///---///");
        return mProjectDataSource.getListProject();
    }

    public LiveData<List<Task>> getListTask() {
        return mTaskDataSource.getListTask();
    }

    public LiveData<Task> getTask(long taskId) {
        return mTaskDataSource.getTask(taskId);
    }

    public void createTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        mExecutor.execute(() -> {
            mTaskDataSource.deleteTask(taskId);
        });

    }

    public void updateTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.updateTask(task);
        });
    }
}
