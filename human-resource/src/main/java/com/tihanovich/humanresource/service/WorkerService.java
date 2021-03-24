package com.tihanovich.humanresource.service;

import com.tihanovich.humanresource.model.Worker;
import com.tihanovich.humanresource.repository.WorkerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Page<Worker> findAllWorkers(Pageable pageable) {
        return workerRepository.findAll(pageable);
    }

    public List<Worker> allWorkers(){
        return workerRepository.findAll();
    }

    public void saveWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public void deleteWorkerById(Long id) {
        workerRepository.deleteById(id);
    }

    public Worker findWorkerById(Long id) {
        return workerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid worker id:" + id));
    }
}
