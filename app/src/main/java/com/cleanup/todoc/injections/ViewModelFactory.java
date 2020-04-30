package com.cleanup.todoc.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDataSource;
    private final Executor mExecutor;

    public ViewModelFactory(TaskDataRepository mTaskDataSource,ProjectDataRepository mProjectDataSource,Executor mExecutor){
        this.mTaskDataSource = mTaskDataSource;
        this.mProjectDataSource = mProjectDataSource;
        this.mExecutor = mExecutor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mTaskDataSource, mProjectDataSource, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
