package com.example.mortageplan.controller;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class ProspectRestController {
    @Autowired
    ProspectServiceImpl prospectServiceImpl;

    @GetMapping("/Prospects")
    public List<ProspectEntity> getAllProspects(){
        return prospectServiceImpl.getAllProspects();
    }
    @GetMapping("/Prospect/{id}")
    public ProspectEntity getProspect(@PathVariable("id") int id) {
        return prospectServiceImpl.getProspectById(id);
    }
    @DeleteMapping("/Prospect/{id}")
    public void deleteProspect(@PathVariable("id") int id) {
        prospectServiceImpl.deleteProspectById(id);
    }
    @PostMapping("/Prospect")
    public void addProspect(@RequestBody ProspectEntity Prospect) {
        prospectServiceImpl.saveOrUpdateProspect(Prospect);
    }
    @PutMapping("/Prospect")
    public void updateProspect(@RequestBody ProspectEntity Prospect) {
        prospectServiceImpl.saveOrUpdateProspect(Prospect);
    }
}