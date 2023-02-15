package com.example.projectservice.controller;

import com.example.projectservice.project.Project;
import com.example.projectservice.project.ProjectRepository;
import com.example.projectservice.response.AuthenticationResponse;
import com.example.projectservice.version.Version;
import com.example.projectservice.version.VersionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.HashMap;
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
    public Page<Project> getAllProjectByUser(@RequestParam(name = "user", required = false) String user,
                                             @RequestParam(name = "name", required = false) String name,
                                             @RequestParam(name = "type", required = false) String type,
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
            if (Objects.nonNull(type)){
                p = cb.and(p, cb.equal(root.get("type"), type));
            }
            cq.orderBy(cb.desc(root.get("name")), cb.asc(root.get("id")));
            return p;
        }, pageable);

        return projectPage;
    }

    @PostMapping("/project/save")
    public ResponseEntity<?> saveProject(@RequestBody Project project){

        //save project
//        int count = 0;
//        for (Project project1: projectRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))) {
//            int index = project1.getName().lastIndexOf("(");
//            int index1 = project1.getName().lastIndexOf(")");
//            if (index != -1) {
//                String copyProj = project1.getName().substring(0, index - 1);
//                if (copyProj.equals(project.getName())) {
//                    String s = project1.getName().substring(index+1, index1);
//                    Integer integer = Integer.parseInt(s);
//                    count = integer + 1;
//                    break;
//                }
//            }
//        }
//        project.setName(project.getName() + " (" + count + ")");
        Project tmpProject = new Project(project.getId(), project.getName(), project.getType(), project.getUser());
        projectRepository.save(tmpProject);

        //save version
        for(Version version : project.getVersionList()) {
            System.out.println(projectRepository.getById(tmpProject.getId()));
            version.setProject(projectRepository.getById(tmpProject.getId()));
            versionRepository.save(version);
        }
        return ResponseEntity.ok(new AuthenticationResponse("Success!", tmpProject.getId()));
    }

    @GetMapping("/version/get")
    public Page<Version> getAllVersionInProjectByUser(@RequestParam(name = "name", required = false) String name,
                                                      @RequestParam(name = "pid") Integer id,
                                                      Pageable pageable){
        System.out.println("1111");
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
        for (Version version1: versionRepository.findAll()) {
            System.out.println(version1.getName() + " " + version1.getProject());
        }
        versionRepository.save(version);
        return ResponseEntity.ok(new AuthenticationResponse("Success!", version.getId()));
    }

}
