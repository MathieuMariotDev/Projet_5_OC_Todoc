package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao mProjectDao) { this.mProjectDao = mProjectDao; }


    // --- GET LIST Project ---

    public LiveData<List<Project>>  getListProject(){
        return this.mProjectDao.getListProject();
    }

    public LiveData<Project> getProject(long projectId){
        return this.mProjectDao.getProject(projectId);
    }

    public void createProject(Project project){
        this.mProjectDao.createProject(project);
    }

    public void updateProject(Project project){
        this.mProjectDao.updateProject(project);
    }

    public void deleteProject(long id){
        this.mProjectDao.deleteProject(id);
    }


}
