package com.example.projectservice.controller;

import com.example.projectservice.project.Project;
import com.example.projectservice.project.ProjectRepository;
import com.example.projectservice.response.AuthenticationResponse;
import com.example.projectservice.version.Version;
import com.example.projectservice.version.VersionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

@RestController
@RequestMapping("/api/project-service/")
public class ProjectController {

    private final ProjectRepository projectRepository;

    private final VersionRepository versionRepository;

    public ProjectController(ProjectRepository projectRepository, VersionRepository versionRepository) {
        this.projectRepository = projectRepository;
        this.versionRepository = versionRepository;
    }

    @GetMapping("/project/get")
    public Page<Project> getAllProjectByUser(@CookieValue(name = "user") String user,
                                             @RequestParam(name = "name", required = false) String name,
                                             @RequestParam(name = "id", required = false) String id,
            Pageable pageable){

        Page<Project> projectPage = projectRepository.findAll((Specification<Project>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if (Objects.nonNull(user)) {
                p = cb.and(p, cb.like(root.get("user"), "%" + user + "%"));
            }
            if (Objects.nonNull(name)) {
                p = cb.and(p, cb.like(root.get("name"), "%" + name + "%"));
            }
            if (Objects.nonNull(id)) {
                p = cb.and(p, cb.equal(root.get("id"), id));
            }
            cq.orderBy(cb.desc(root.get("name")), cb.asc(root.get("id")));
            return p;
        }, pageable);

        return projectPage;
    }

    @PostMapping("/project/save")
    public ResponseEntity<?> saveProject(@RequestBody Project project){
        Project tmpProject = new Project(project.getId(), project.getName(), project.getUser());

        //save project
        projectRepository.save(tmpProject);

        //save version
        for(Version version : project.getVersionList()) {
            version.setProject(projectRepository.getById(tmpProject.getId()));
            versionRepository.save(version);
        }
        return ResponseEntity.ok(new AuthenticationResponse("Success!", tmpProject.getId()));
    }

    @GetMapping("/version/get")
    public Page<Version> getAllVersionInProjectByUser(@RequestParam(name = "name", required = false) String name,
                                                      @RequestParam(name = "pid") Integer id,
                                                      Pageable pageable){

        Page<Version> versionPage = versionRepository.findAll((Specification<Version>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if (Objects.nonNull(name)) {
                p = cb.and(p, cb.like(root.get("name"), "%" + name + "%"));
            }
            if (Objects.nonNull(id)) {
                p = cb.and(p, cb.equal(root.get("project"), id));
            }
            cq.orderBy(cb.desc(root.get("project")), cb.asc(root.get("project")));
            return p;
        }, pageable);

        return versionPage;
    }

    @PostMapping("/version/save")
    public ResponseEntity<?> saveVersion(@RequestBody Version version){
        versionRepository.save(version);
        return ResponseEntity.ok(new AuthenticationResponse("Success!", version.getId()));
    }

}
