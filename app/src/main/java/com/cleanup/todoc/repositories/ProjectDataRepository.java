package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao mProjectDao) { this.mProjectDao = mProjectDao; }

    // --- GET Project ---
    //public LiveData<Project> getProject(long projectId) { return this.mProjectDao.getProject(projectId); }

    // --- GET LIST Project ---

    public LiveData<List<Project>>  getListProject(){
        return this.mProjectDao.getListProject();
    } //No need liveData
}
