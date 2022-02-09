package com.example.mortageplan.service;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.repository.ProspectRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of prospect entity JPA service interface
 */
@Service
public class ProspectServiceImpl implements ProspectService {
    final ProspectRepository repository;

    /**
     * Constructor to avoid @Autowired
     *
     * @param repository Entity JPA repository
     */
    public ProspectServiceImpl(ProspectRepository repository) {
        this.repository = repository;
    }

    /**
     * Get prospect entity by specified id
     *
     * @param id Prospect entity id
     * @return Prospect entity
     */
    public ProspectEntity getProspectById(int id) {
        if (repository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Entity not present");
        }
        return repository.findById(id).get();
    }

    /**
     * Get prospect entities as list
     *
     * @return List of prospect entities
     */
    public List<ProspectEntity> getAllProspects() {
        List<ProspectEntity> prospectEntities = new ArrayList<>();
        repository.findAll().forEach(prospectEntities::add);
        return prospectEntities;
    }

    /**
     * Save or update JPA entity
     *
     * @param prospectEntity Prospect entity
     */
    public void saveOrUpdateProspect(ProspectEntity prospectEntity) {
        repository.save(prospectEntity);
    }

    /**
     * Delete prospect entity specified by id
     *
     * @param id Entity id
     */
    public void deleteProspectById(int id) {
        repository.deleteById(id);
    }
}
