package com.example.mortageplan.controller;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Default controller for Web Application content
 */
@Controller
public class ProspectsController {
    final
    ProspectService prospectService;

    /**
     * @param prospectService Prospect entity JPA service
     */
    public ProspectsController(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    @GetMapping({"/"})
    public String webpage(Model model, ProspectEntity prospect) {
        List<ProspectEntity> prospects = prospectService.getAllProspects();
        model.addAttribute("prospects", prospects);
        model.addAttribute("prospect", prospect);
        return "index";
    }

    @PostMapping("/")
    public String addProspect(@Valid @ModelAttribute("prospect") ProspectEntity prospect, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<ProspectEntity> prospects = prospectService.getAllProspects();
            model.addAttribute("prospects", prospects);
            model.addAttribute("message",
                    "Unable to add prospect using form input data");
            return "index";
        }
        redirectAttributes.addFlashAttribute("message",
                "Prospect added");
        prospectService.saveOrUpdateProspect(prospect);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        prospectService.deleteProspectById(id);
        redirectAttributes.addFlashAttribute("message",
                "Prospect deleted");
        return "redirect:/";
    }
}