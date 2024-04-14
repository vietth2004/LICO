package com.example.projectservice.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

    @Query("SELECT DISTINCT project FROM Project project WHERE project.user=:user")
    public List<Project> findProjectByUser(String user);
    @Modifying
    @Transactional
    @Query("DELETE FROM Project project WHERE project.id  = :id")
    void deleteAllByProjectId(Integer id);
}
