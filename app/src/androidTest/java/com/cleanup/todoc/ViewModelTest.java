package com.cleanup.todoc;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.injections.Injection;
import com.cleanup.todoc.injections.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.ui.TaskViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ViewModelTest {
    // FOR DATA
    private TodocDatabase database;

    private TaskViewModel mTaskViewModel;


    //DATA SET FOR TEST
    private static long TASK_ID = 1;
    private static long mTASK_ID = 2;
    private static long PROJECT_ID = 1L;
    /// ---Task DEMO ---
    private static Task TASK_DEMO = new Task(TASK_ID, PROJECT_ID, "mathieu", 1);
    private static Task mTASK_DEMO = new Task(mTASK_ID, PROJECT_ID, "Thomas", 2);

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);



    @Before
    public void initDb() throws Exception {
        ViewModelFactory mviewModelFactory = Injection.provideViewModelFactory(rule.getActivity());
        this.mTaskViewModel = ViewModelProviders.of(rule.getActivity(), mviewModelFactory).get(TaskViewModel.class);

    }


    @Test
    public void getListProjects() throws Exception{
        List<Project> mListProject = LiveDataTestUtil.getValue(mTaskViewModel.getListProject());
        Assert.assertTrue(mListProject.size() == 3);
    }

    @Test
    public void getCreateTask() throws Exception{
        mTaskViewModel.createTask(TASK_DEMO);

        List<Task> mListTask = LiveDataTestUtil.getValue(mTaskViewModel.getListTask());
        Assert.assertTrue(mListTask.size() == 1);
    }

    @Test
    public void deleteTask()throws Exception{
        mTaskViewModel.deleteTask(TASK_ID);

        List<Task> mListTask = LiveDataTestUtil.getValue(mTaskViewModel.getListTask());
        Assert.assertTrue(mListTask.isEmpty());
    }


}
