package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {


    // Repositories
    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDataSource;
    private final Executor mExecutor; // for background execution
    public SortMethod mSortMethod = SortMethod.NONE;


    // DATA


    public TaskViewModel(TaskDataRepository mTaskDataSource, ProjectDataRepository mProjectDataSource, Executor mExecutor) { //Constructor
        this.mTaskDataSource = mTaskDataSource;
        this.mProjectDataSource = mProjectDataSource;
        this.mExecutor = mExecutor;
    }


    public LiveData<List<Project>> getListProject() { //LiveData for asynchron recovery and observe notify update
        Log.d("Debug", "getProjectList TaskViewModel ///---///");
        return mProjectDataSource.getListProject();
    }

    /*public LiveData<Project> getProject(long projectId) {
        return mProjectDataSource.getProject(projectId);

    }*/

    public LiveData<List<Task>> getListTask() {
        return mTaskDataSource.getListTask();
    }

    /*public LiveData<Task> getTask(long taskId) {
        return mTaskDataSource.getTask(taskId);
    }*/

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

    /*public void updateTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataSource.updateTask(task);
        });
    }*/

    public void updateSortMethod(String sortMethod) {
        switch (sortMethod) {
            case "ALPHABETICAL":
                mSortMethod = SortMethod.ALPHABETICAL;
                break;
            case "ALPHABETICAL_INVERTED":
                mSortMethod = SortMethod.ALPHABETICAL_INVERTED;
                break;
            case "RECENT_FIRST":
                mSortMethod = SortMethod.RECENT_FIRST;
                break;
            case "OLD_FIRST":
                mSortMethod = SortMethod.OLD_FIRST;
                break;
            default:
                break;
        }
    }

    public void getSortMethod(List<Task> tasks) {
        switch (mSortMethod) {
            case ALPHABETICAL:
                Collections.sort(tasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new Task.TaskOldComparator());
                break;

        }
    }

    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

}
