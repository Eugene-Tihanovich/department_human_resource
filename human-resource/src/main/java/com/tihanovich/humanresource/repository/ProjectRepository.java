package com.tihanovich.humanresource.repository;

import com.tihanovich.humanresource.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p from Project p where concat(p.id, '') like %?1%" +
            "or p.name like %?1%" +
            "or p.abbreviation like %?1%" +
            "or p.description like %?1%")
    Page<Project> searchProject(String param, Pageable pageable);
}
