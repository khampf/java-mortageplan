package com.example.mortageplan.service;

import com.example.mortageplan.entity.ProspectEntity;

import java.util.List;

/**
 * Prospect entity JPA service
 */
public interface ProspectService {

    /**
     * Get prospect entity by specified id
     *
     * @param id Prospect entity id
     * @return Prospect entity
     */
    ProspectEntity getProspectById(int id);

    /**
     * Get prospect entities as list
     *
     * @return List of prospect entities
     */
    List<ProspectEntity> getAllProspects();

    /**
     * Save or update JPA entity
     *
     * @param prospectEntity Prospect entity
     */
    void saveOrUpdateProspect(ProspectEntity prospectEntity);

    /**
     * Delete prospect entity specified by id
     *
     * @param id Entity id
     */
    void deleteProspectById(int id);
}
