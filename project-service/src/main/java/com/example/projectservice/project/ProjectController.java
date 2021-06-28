package com.example.projectservice.project;

import com.example.projectservice.response.AuthenticationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

@RestController
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("api/project")
    public Page<Project> getAllProjectByUser(
            @CookieValue(name = "user") String user,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "id", required = false) String id,
            Pageable pageable){

        Page<Project> projectPage = projectRepository.findAll((Specification<Project>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if (Objects.nonNull(user)) {
                p = cb.and(p, cb.like(root.get("chipset"), "%" + user + "%"));
            }
            if (Objects.nonNull(name)) {
                p = cb.and(p, cb.like(root.get("name"), "%" + name + "%"));
            }
            if (Objects.nonNull(id)) {
                p = cb.and(p, cb.like(root.get("id"), "%" + id + "%"));
            }
            cq.orderBy(cb.desc(root.get("name")), cb.asc(root.get("id")));
            return p;
        }, pageable);

        return projectPage;
    }

    @PostMapping("api/save/project")
    public ResponseEntity<?> saveProject(@RequestBody Project project){
        projectRepository.save(project);
        return ResponseEntity.ok(new AuthenticationResponse("Success!", project.getId()));
    }

}
