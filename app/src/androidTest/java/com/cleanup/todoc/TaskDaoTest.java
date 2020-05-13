package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ArrayAdapter;

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

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private TodocDatabase database;

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
        // BEFORE : Adding a new Demo Project
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.mProjectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    /// Test for list ---
    @Test
    public void insertAndGetProjectList() throws InterruptedException {
        // BEFORE : Adding a new Demo Project
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mProjectDao().createProject(PROJECT_DEMO_bis);
        // TEST
        List<Project> project = LiveDataTestUtil.getValue(this.database.mProjectDao().getListProject());
        assertTrue(project.size() == 2);
    }

    @Test
    public void getTaskWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> task = LiveDataTestUtil.getValue(this.database.mTaskDao().getListTask());
        assertTrue(task.isEmpty());
    }

    @Test
    public void insertAndGetListTask() throws InterruptedException {
        // BEFORE : Adding demo Project & demo Task
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().createTask(TASK_DEMO);
        this.database.mTaskDao().createTask(mTASK_DEMO);
        //this.database.mTaskDao().insertItem(NEW_ITEM_IDEA);
        //this.database.mTaskDao().insertItem(NEW_ITEM_RESTAURANTS);

        // TEST
        List<Task> task = LiveDataTestUtil.getValue(this.database.mTaskDao().getListTask());
        assertTrue(task.size() == 2);
    }


    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE : Adding demo Project & demo Task. Next, get the task Added & delete it.
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().createTask(TASK_DEMO);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.mTaskDao().getTask(TASK_ID));
        this.database.mTaskDao().deleteTask(taskAdded.getId());

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.mTaskDao().getListTask());
        assertTrue(tasks.isEmpty());
    }


    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mTaskDao().createTask(TASK_DEMO);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.mTaskDao().getTask(TASK_ID));
        taskAdded.setName("David");
        this.database.mTaskDao().updateTask(taskAdded);

        //TEST
        Task task = LiveDataTestUtil.getValue(this.database.mTaskDao().getTask(TASK_ID));
        assertTrue(taskAdded.getName().equals(task.getName())&& taskAdded.getId()==(task.getId())); //add id
    }


    @Test
    public void deleteProject() throws InterruptedException {
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mProjectDao().deleteProject(PROJECT_ID);

        List<Project> projects = LiveDataTestUtil.getValue(database.mProjectDao().getListProject());
        assertTrue(projects.isEmpty());

    }

    @Test
    public void updateProject() throws InterruptedException{
        this.database.mProjectDao().createProject(PROJECT_DEMO);
        this.database.mProjectDao().updateProject(new Project(PROJECT_ID, "Projet Mathieu", 0xFFEADAD1));
        Project project = LiveDataTestUtil.getValue(this.database.mProjectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().contains("Projet Mathieu"));
    }

    @After
    public void closeDb() {
        database.clearAllTables();
        database.close();
    }
}
