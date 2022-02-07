package com.example.mortageplan.controller;

import com.example.mortageplan.entity.InvalidInputException;
import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import com.example.mortageplan.util.CSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

// TODO Delete buttons
// TODO Remove all button

@Controller
public class ProspectsController {
    @Autowired
    ProspectService prospectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
/*
    @GetMapping({"/", "/hello"})
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }*/

    @GetMapping({"/"})
    public String webpage(Model model, ProspectEntity prospect) {
        List<ProspectEntity> prospects = prospectService.getAllProspects();
        model.addAttribute("prospects", prospects);
        model.addAttribute("prospect", prospect);
        return "index";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            CSV csv = new CSV(file.getInputStream());
            int i=0;
            for (List<String> strings : csv.getStrings()) {
                try {
                    ProspectEntity prospectEntity = new ProspectEntity(strings);
                    prospectService.saveOrUpdateProspect(prospectEntity);
                } catch (InvalidInputException e) {
                    if (i>0) {  // Skip first line header row error
                        logger.warn("Invalid CSV input: " + e.getMessage());
                    }
                }
                i++;
            }
            logger.debug("INPUT FILE: {}", file);
            redirectAttributes.addFlashAttribute("message",
                    "File " + file.getOriginalFilename() + " added.");

        } catch (IOException e) {
            // e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Error loading file " + file.getOriginalFilename() + "!");

        }
        return "redirect:/";
    }

    @PostMapping("/add")
    public String addProspect(@Valid ProspectEntity prospect, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("prospect", prospect);
            redirectAttributes.addFlashAttribute("message",
                    "Unable to add prospect using form input");
            return "redirect:/";
        }
        prospectService.saveOrUpdateProspect(prospect);
        return "redirect:/";
    }
}
