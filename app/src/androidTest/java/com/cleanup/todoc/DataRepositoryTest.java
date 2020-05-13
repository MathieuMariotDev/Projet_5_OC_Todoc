package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
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
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class DataRepositoryTest {

    // FOR DATA
    private TodocDatabase database;

    public static ProjectDataRepository mProjectDataRepository;

    public static TaskDataRepository mTaskDataRepository;

    public Injection mInjection;

    //DATA SET FOR TEST
    private static long TASK_ID = 1;
    private static long mTASK_ID = 2;
    private static long PROJECT_ID = 1L;
    //    /// --- Project DEMO ---
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);
    private static Project PROJECT_DEMO_bis = new Project(2L, "Projet Tartam", 0xFFEADAD1);
    /// ---Task DEMO ---
    private static Task TASK_DEMO = new Task(TASK_ID, PROJECT_ID, "mathieu", 1);
    private static Task mTASK_DEMO = new Task(mTASK_ID, PROJECT_ID, "Thomas", 2);
    //private static Task mTASK_DEMO_INSER = new Task (0,PROJECT_ID,"Thomas",2);

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initDb() throws Exception {
        mProjectDataRepository = mInjection.provideProjectDataSource(rule.getActivity());
        mTaskDataRepository = mInjection.provideTaskDataSource(rule.getActivity());

    }

    @Test
    public void getListProjects() throws Exception{
        List<Project> mListProject = LiveDataTestUtil.getValue(mProjectDataRepository.getListProject());
        Assert.assertTrue(mListProject.size() == 3);
    }

    @Test
    public void getCreateTask() throws Exception{
        mTaskDataRepository.createTask(TASK_DEMO);
        mTaskDataRepository.createTask(mTASK_DEMO);

        List<Task> mListTask = LiveDataTestUtil.getValue(mTaskDataRepository.getListTask());
        Assert.assertTrue(mListTask.size() == 2);
    }

    @Test
    public void deleteTask()throws Exception{
        mTaskDataRepository.deleteTask(TASK_ID);
        mTaskDataRepository.deleteTask(mTASK_ID);

        List<Task> mListTask = LiveDataTestUtil.getValue(mTaskDataRepository.getListTask());
        Assert.assertTrue(mListTask.isEmpty());
    }

}
