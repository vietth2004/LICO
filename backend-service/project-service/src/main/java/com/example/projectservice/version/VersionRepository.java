package com.example.projectservice.version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface VersionRepository extends JpaRepository<Version, Integer>, JpaSpecificationExecutor<Version> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Version v WHERE v.project.id  = :pid")
    void deleteAllByProjectId(Integer pid);
    @Modifying
    @Transactional
    @Query("SELECT v FROM Version v WHERE v.project.id = :pid")
    public List<Version> findByPId(Integer pid);
    @Modifying
    @Transactional
    @Query("SELECT v.path FROM Version v WHERE v.project.id = :pid")
    public List<String> findPathByPId(Integer pid);
}
