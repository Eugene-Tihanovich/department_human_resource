package com.tihanovich.humanresource.controller;

import com.tihanovich.humanresource.model.Worker;
import com.tihanovich.humanresource.repository.WorkerRepository;
import com.tihanovich.humanresource.service.WorkerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class WorkerController {

    private final WorkerService workerService;
    private final WorkerRepository workerRepository;

    public WorkerController(WorkerService workerService, WorkerRepository workerRepository) {
        this.workerService = workerService;
        this.workerRepository = workerRepository;
    }

    @GetMapping("/workers")
    public String workerPage(Model model, HttpServletRequest request){
        int page = 0;
        int size = 6;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("workers", workerService.findAllWorkers(PageRequest.of(page, size)));
        return "workers";
    }

    @GetMapping("/workers/add-worker")
    public String AddWorkerPage(Model model){
        model.addAttribute("worker", new Worker());
        return "add-worker";
    }

    @PostMapping("/workers/add-worker")
    public String addWorker(@Valid @ModelAttribute("worker") Worker worker, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add-worker";
        }

        workerService.saveWorker(worker);
        return "redirect:/workers";
    }

    @PostMapping("/workers")
    public String deleteWorker(@RequestParam(name = "deleteId") Long id){
        workerService.deleteWorkerById(id);
        return "redirect:/workers";
    }

    @GetMapping("/workers/edit-worker/{id}")
    public String editWorkerPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("worker", workerService.findWorkerById(id));
        return "edit-worker";
    }

    @PostMapping("/workers/edit-worker/{id}")
    public String editWorker(@Valid @ModelAttribute("worker") Worker worker, BindingResult bindingResult,
                             @PathVariable Long id){

        if (bindingResult.hasErrors()) {
            worker.setId(id);
            return "edit-worker";
        }

        worker.setId(id);
        workerService.saveWorker(worker);
        return "redirect:/workers";
    }

    @GetMapping("/workers/search-worker")
    public String searchWorker(@RequestParam(value = "param", required = false) String param, Model model1,
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
            model1.addAttribute("workers", workerRepository.searchWorker(param, PageRequest.of(page, size)));
            model2.addAttribute("text", param);
            return "search-worker";
        }
        return "redirect:/workers";
    }

}
