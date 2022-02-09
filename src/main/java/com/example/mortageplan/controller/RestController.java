package com.example.mortageplan.controller;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(path = "/rest")
public class RestController {
    final ProspectService prospectService;

    /**
     * Constructor to avoid @Autowired
     * @param prospectService JPA Service
     */
    public RestController(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    /**
     * Returns a list of all Prospect entities
     * @return ProspectEntity
     */
    @GetMapping("/Prospects")
    public List<ProspectEntity> getAllProspects(){
        return prospectService.getAllProspects();
    }

    /**
     * Returns Prospect entity specified by id
     * @param id Entity id
     * @return ProspectEntity
     */
    @GetMapping("/Prospect/{id}")
    public ProspectEntity getProspect(@PathVariable("id") int id) {
        return prospectService.getProspectById(id);
    }

    /**
     * Deletes Prospect entity specified by id
     * @param id Entity id
     */
    @DeleteMapping("/Prospect/{id}")
    public void deleteProspect(@PathVariable("id") int id) {
        prospectService.deleteProspectById(id);
    }

    /**
     * Adds Prospect entity
     * @param Prospect Prospect entity
     */
    @PostMapping("/Prospect")
    public void addProspect(@RequestBody ProspectEntity Prospect) {
        prospectService.saveOrUpdateProspect(Prospect);
    }

    /**
     * Updates Prospect entity
     * @param Prospect Prospect entity
     */
    @PutMapping("/Prospect")
    public void updateProspect(@RequestBody ProspectEntity Prospect) {
        prospectService.saveOrUpdateProspect(Prospect);
    }
}