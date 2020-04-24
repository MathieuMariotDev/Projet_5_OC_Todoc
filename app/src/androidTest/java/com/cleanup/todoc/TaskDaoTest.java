package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private TodocDatabase database;

    //DATA SET FOR TEST
    private static long TASK_ID =1;
    private static long mTASK_ID =2;
    private static long PROJECT_ID=11;
    private static Task TASK_DEMO = new Task (TASK_ID,PROJECT_ID,"mathieu",1);
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);
    private static Task mTASK_DEMO = new Task (mTASK_ID,PROJECT_ID,"Thomas",2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }



    @Test
    public void insertAndGetProject() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.mProjectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getTaskWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> task = LiveDataTestUtil.getValue(this.database.mTaskDao().getListTask(PROJECT_ID));
        Assert.assertTrue(task.isEmpty());
    }

    @Test
    public void insertAndGetItems() throws InterruptedException {
        // BEFORE : Adding demo user & demo items
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().createTask(TASK_DEMO);
        this.database.mTaskDao().createTask(mTASK_DEMO);
        //this.database.mTaskDao().insertItem(NEW_ITEM_IDEA);
        //this.database.mTaskDao().insertItem(NEW_ITEM_RESTAURANTS);

        // TEST
        List<Task> task = LiveDataTestUtil.getValue(this.database.mTaskDao().getListTask(PROJECT_ID));
        assertTrue(task.size() == 2);
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
