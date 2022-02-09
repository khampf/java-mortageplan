package com.example.mortageplan.repository;

import com.example.mortageplan.entity.ProspectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Prospect entity JPA repository
 */
@Repository
public interface ProspectRepository extends CrudRepository<ProspectEntity, Integer> {
}
