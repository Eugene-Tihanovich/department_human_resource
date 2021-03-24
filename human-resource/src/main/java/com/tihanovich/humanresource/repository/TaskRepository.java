package com.tihanovich.humanresource.repository;

import com.tihanovich.humanresource.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t from Task t where concat(t.id, '') like %?1%" +
            "or t.project.abbreviation like %?1%" +
            "or t.name like %?1%" +
            "or concat(t.startDate, '') like %?1%" +
            "or concat(t.endDate, '') like %?1%" +
            "or t.worker.name like %?1%" +
            "or t.worker.surname like %?1%" +
            "or t.worker.middleName like %?1%" +
            "or t.status like %?1%")
    Page<Task> searchTask(String param, Pageable pageable);
}
