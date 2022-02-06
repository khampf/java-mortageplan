package com.example.mortageplan.service;

import com.example.mortageplan.entity.ProspectEntity;

import java.util.List;

public interface ProspectService {
    // Save operation
    void saveOrUpdateProspect(ProspectEntity prospectEntity);

    // Read operation
    ProspectEntity getProspectById(int id);
    List<ProspectEntity> getAllProspects();

    // Delete operation
    void deleteProspectById(int id);
}
