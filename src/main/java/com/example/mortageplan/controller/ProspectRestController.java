package com.example.mortageplan.controller;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest")
public class ProspectRestController {
    @Autowired
    ProspectService prospectService;

    @GetMapping("/Prospects")
    public List<ProspectEntity> getAllProspects(){
        return prospectService.getAllProspects();
    }
    @GetMapping("/Prospect/{id}")
    public ProspectEntity getProspect(@PathVariable("id") int id) {
        return prospectService.getProspectById(id);
    }
    @DeleteMapping("/Prospect/{id}")
    public void deleteProspect(@PathVariable("id") int id) {
        prospectService.deleteProspectById(id);
    }
    @PostMapping("/Prospect")
    public void addProspect(@RequestBody ProspectEntity Prospect) {
        prospectService.saveOrUpdateProspect(Prospect);
    }
    @PutMapping("/Prospect")
    public void updateProspect(@RequestBody ProspectEntity Prospect) {
        prospectService.saveOrUpdateProspect(Prospect);
    }
}