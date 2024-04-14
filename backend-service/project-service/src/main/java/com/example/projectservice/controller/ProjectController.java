package com.example.projectservice.controller;

import com.example.projectservice.project.Project;
import com.example.projectservice.project.ProjectRepository;
import com.example.projectservice.response.AuthenticationResponse;
import com.example.projectservice.utils.DeleteFile;
import com.example.projectservice.version.Version;
import com.example.projectservice.version.VersionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                                             @RequestParam(name = "language", required = false) String language,
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
            if (Objects.nonNull(language)){
                p = cb.and(p, cb.equal(root.get("language"), language));
            }
            cq.orderBy(cb.desc(root.get("name")), cb.asc(root.get("id")));
            return p;
        }, pageable);

        return projectPage;
    }

    @PostMapping("/project/save")
    public ResponseEntity<?> saveProject(@RequestBody Project project){
        Project tmpProject = new Project(project.getId(), project.getName(), project.getLanguage(), project.getUser());
        projectRepository.save(tmpProject);

        //save version
        for(Version version : project.getVersionList()) {
            System.out.println(projectRepository.getById(tmpProject.getId()));
            version.setProject(projectRepository.getById(tmpProject.getId()));
            versionRepository.save(version);
        }
        return ResponseEntity.ok(new AuthenticationResponse("Success!", tmpProject.getId()));
    }
    @GetMapping("/project/delete")
    public ResponseEntity<?> deleteProject(@RequestParam(name = "id", required = false) Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse("ID is required"));
        }

        Optional<Project> projectOptional = projectRepository.findById(id);

        if (projectOptional.isPresent()) {
            // Kiểm tra xem có phiên bản nào liên kết với dự án không
            List<Version> versions = versionRepository.findByPId(id);
            if (!versions.isEmpty()) {
                return ResponseEntity.badRequest().body(new AuthenticationResponse("There are versions associated with this project. Please delete the versions first."));
            }

            // Xóa dự án nếu không có phiên bản liên kết
            projectRepository.deleteById(id);
            return ResponseEntity.ok(new AuthenticationResponse("Project with ID " + id + " has been deleted"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/project/delete-v")
    public ResponseEntity<?> deleteProjectVerion(@RequestParam(name = "id", required = false) Integer id) throws IOException {
        if (id == null) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse("ID is required"));
        }

        Optional<Project> projectOptional = projectRepository.findById(id);

        if (projectOptional.isPresent()) {
            // Kiểm tra xem có phiên bản nào liên kết với dự án không
            List<Version> versions = versionRepository.findByPId(id);
            if (!versions.isEmpty()) {
                List<String> pathProject = versionRepository.findPathByPId(id);
                for(String path: pathProject){
                    DeleteFile.deleteFolder(path);
                    DeleteFile.deleteZipFile(path);
                }
                versionRepository.deleteAllByProjectId(id);
            }
            // Xóa dự án nếu không có phiên bản liên kết
            projectRepository.deleteAllByProjectId(id);
            return ResponseEntity.ok(new AuthenticationResponse("Project with ID " + id + " has been deleted"));
        } else {
            return ResponseEntity.notFound().build();
        }
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
    @GetMapping("/version/delete")
    public ResponseEntity<?> deleteVersion(@RequestParam(name = "id", required = false) Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse("ID is required"));
        }

        Optional<Version> projectOptional = versionRepository.findById(id);
        if (projectOptional.isPresent()) {
            versionRepository.deleteById(id);
            return ResponseEntity.ok(new AuthenticationResponse("Version with ID " + id + " has been deleted"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
