package com.example.mortageplan.service;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.repository.ProspectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProspectServiceImpl implements ProspectService {
    @Autowired
    ProspectRepository repository;

    public ProspectEntity getProspectById(int id) {
        return repository.findById(id).get();
    }
    public List<ProspectEntity> getAllProspects() {
        List<ProspectEntity> prospectEntities = new ArrayList<ProspectEntity>();
        repository.findAll().forEach(prospectEntity -> prospectEntities.add(prospectEntity));
        return prospectEntities;
    }
    public void saveOrUpdateProspect(ProspectEntity prospectEntity) {
        repository.save(prospectEntity);
    }
    public void deleteProspectById(int id) {
        repository.deleteById(id);
    }}
