package com.tihanovich.humanresource.controller;

import com.tihanovich.humanresource.model.Project;
import com.tihanovich.humanresource.model.Status;
import com.tihanovich.humanresource.model.Task;
import com.tihanovich.humanresource.repository.ProjectRepository;
import com.tihanovich.humanresource.service.ProjectService;
import com.tihanovich.humanresource.service.TaskService;
import com.tihanovich.humanresource.service.WorkerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final WorkerService workerService;
    private final TaskService taskService;
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectService projectService, WorkerService workerService, TaskService taskService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.workerService = workerService;
        this.taskService = taskService;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/projects")
    public String projectPage(Model model, HttpServletRequest request) {
        int page = 0;
        int size = 6;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("projects", projectService.findAllProjects(PageRequest.of(page, size)));
        return "projects";
    }

    @GetMapping("/projects/add-project")
    public String AddProjectPage(Model model) {
        model.addAttribute("project", new Project());
        return "add-project";
    }

    @PostMapping("/projects/add-project")
    public String addProject(@Valid @ModelAttribute("project") Project project, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add-project";
        }

        projectService.saveProject(project);
        return "redirect:/projects/project-" + project.getId() + "/add-task";
    }

    @GetMapping("/projects/project-{id}/add-task")
    public String AddProjectTaskPage(@PathVariable("id") Long id, Model model1, Model model2, Model model3, Model model4) {
        model1.addAttribute("task", new Task());
        model2.addAttribute("statuses", Status.values());
        model3.addAttribute("project", projectService.findProjectById(id));
        model4.addAttribute("workers", workerService.allWorkers());
        return "add-project-task";
    }

    @PostMapping("/projects/project/add-task")
    public String addProjectTask(@Valid @ModelAttribute("task") Task task) {
        taskService.saveTask(task);
        return "redirect:/projects";
    }

    @PostMapping("/projects")
    public String deleteProject(@RequestParam(name = "deleteId") Long id) {
        projectService.deleteProjectById(id);
        return "redirect:/projects";
    }

    @GetMapping("/projects/edit-project/{id}")
    public String editProjectPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.findProjectById(id));
        return "edit-project";
    }

    @PostMapping("/projects/edit-project/{id}")
    public String editProject(@Valid @ModelAttribute("project") Project project, @PathVariable Long id) {
        project.setId(id);
        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/search-project")
    public String searchProject(@RequestParam(value = "param", required = false) String param, Model model1,
                             Model model2, HttpServletRequest request){
        int page = 0;
        int size = 6;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        if (param != null && !"".equals(param.trim())) {
            model1.addAttribute("projects", projectRepository.searchProject(param, PageRequest.of(page, size)));
            model2.addAttribute("text", param);
            return "search-project";
        }
        return "redirect:/projects";
    }
}
