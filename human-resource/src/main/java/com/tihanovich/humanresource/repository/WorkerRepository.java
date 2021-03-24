package com.tihanovich.humanresource.repository;

import com.tihanovich.humanresource.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    @Query("SELECT w from Worker w where concat(w.id, '') like %?1%" +
            "or w.name like %?1%" +
            "or w.surname like %?1%" +
            "or w.middleName like %?1%" +
            "or w.position like %?1%")
    Page<Worker> searchWorker(String param, Pageable pageable);
}
