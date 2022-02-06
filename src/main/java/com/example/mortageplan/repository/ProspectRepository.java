package com.example.mortageplan.repository;

import com.example.mortageplan.entity.ProspectEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProspectRepository extends CrudRepository<ProspectEntity, Integer> {
}
