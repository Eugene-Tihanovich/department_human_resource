package com.tihanovich.humanresource.controller;

import com.tihanovich.humanresource.model.Status;
import com.tihanovich.humanresource.model.Task;
import com.tihanovich.humanresource.repository.TaskRepository;
import com.tihanovich.humanresource.service.ProjectService;
import com.tihanovich.humanresource.service.TaskService;
import com.tihanovich.humanresource.service.WorkerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final WorkerService workerService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, ProjectService projectService, WorkerService workerService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.workerService = workerService;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public String taskPage(Model model, HttpServletRequest request){
        int page = 0;
        int size = 6;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("tasks", taskService.findAllTasks(PageRequest.of(page, size)));
        return "tasks";
    }

    @GetMapping("/tasks/add-task")
    public String addTaskPage(Model model1, Model model2, Model model3, Model model4){
        model1.addAttribute("task", new Task());
        model2.addAttribute("statuses", Status.values());
        model3.addAttribute("projects",projectService.allProjects());
        model4.addAttribute("workers", workerService.allWorkers());

        return "add-task";
    }

    @PostMapping("/tasks/add-task")
    public String addTask(@Valid @ModelAttribute("task") Task task, Model model1, Model model2, Model model3) {

        model1.addAttribute("statuses", Status.values());
        model2.addAttribute("projects",projectService.allProjects());
        model3.addAttribute("workers", workerService.allWorkers());

        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks")
    public String deleteTask(@RequestParam(name = "deleteId") Long id){
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit-task/{id}")
    public String editTaskPage(@PathVariable("id") Long id, Model model1, Model model2, Model model3, Model model4) {
        model1.addAttribute("task", taskService.findTaskById(id));
        model2.addAttribute("statuses", Status.values());
        model3.addAttribute("projects",projectService.allProjects());
        model4.addAttribute("workers", workerService.allWorkers());
        return "edit-task";
    }

    @PostMapping("/tasks/edit-task/{id}")
    public String editTask(@Valid @ModelAttribute("task") Task task, @PathVariable Long id, Model model1, Model model2, Model model3){

        model1.addAttribute("statuses", Status.values());
        model2.addAttribute("projects",projectService.allProjects());
        model3.addAttribute("workers", workerService.allWorkers());

        task.setId(id);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/search-task")
    public String searchTask(@RequestParam(value = "param", required = false) String param, Model model1,
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
            model1.addAttribute("tasks", taskRepository.searchTask(param, PageRequest.of(page, size)));
            model2.addAttribute("text", param);
            return "search-task";
        }
        return "redirect:/tasks";
    }
}
