package com.cleanup.todoc;

import android.arch.lifecycle.ViewModelProviders;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.injections.Injection;
import com.cleanup.todoc.injections.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
//@RunWith(MockitoJUnitRunner.class)
public class ViewModelTest {


    @Mock
    public ProjectDataRepository mProjectDataSource;
    @Mock
    public TaskDataRepository mTaskDataSource;
    public TaskViewModel mTaskViewModel;
    private Executor mExecutor;
    //DATA SET FOR TEST
    private static long TASK_ID = 1;
    private static long mTASK_ID = 2;
    private static long PROJECT_ID = 1L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);

    private Task task1 = new Task(TASK_ID, PROJECT_ID, "mathieu", 1);



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mTaskViewModel = new TaskViewModel(mTaskDataSource, mProjectDataSource,mExecutor);
        
    }

    @Test
    public void createTask(){

        //mock creation
        List mockedList = mock(List.class);
        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
        mProjectDataSource.getListProject();
        mTaskViewModel.createTask(task1);

    }
    //Let's import Mockito statically so that the code looks clearer



}
