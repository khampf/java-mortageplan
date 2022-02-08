package com.example.mortageplan.controller;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProspectsController {
    @Autowired
    ProspectService prospectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            // model.addAttribute("prospect", prospect);
            model.addAttribute("message",
                    "Unable to add prospect using form input data");
            return "index";
        }
        redirectAttributes.addFlashAttribute("message",
                "Prospect added");
        prospectService.saveOrUpdateProspect(prospect);
        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        prospectService.deleteProspectById(id);
        redirectAttributes.addFlashAttribute("message",
                "Prospect deleted");
        return "redirect:/";
    }
}