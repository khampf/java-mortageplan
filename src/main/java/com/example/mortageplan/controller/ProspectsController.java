package com.example.mortageplan.controller;

import com.example.mortageplan.entity.InvalidInputException;
import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import com.example.mortageplan.util.CSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Default controller for Web Application content
 */
@Controller
public class ProspectsController {
    final ProspectService prospectService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param prospectService Prospect entity JPA service
     */
    public ProspectsController(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    @GetMapping({"/"})
    public String webpage(Model model) {
        model.addAttribute("prospect", new ProspectEntity());
        List<ProspectEntity> prospects = prospectService.getAllProspects();
        model.addAttribute("prospects", prospects);
        return "index";
    }

    @PostMapping("/")
    public String addProspect(@Valid @ModelAttribute("prospect") ProspectEntity prospect, BindingResult result,
                              Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("prospects", prospectService.getAllProspects());
            model.addAttribute("message",
                    "Unable to add prospect using form input data");
            return "index";
        }
        redirectAttributes.addFlashAttribute("message",
                "Prospect added");
        prospectService.saveOrUpdateProspect(prospect);
        redirectAttributes.addFlashAttribute("prospect", new ProspectEntity());
        redirectAttributes.addFlashAttribute("prospects", prospectService.getAllProspects());
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            prospectService.deleteProspectById(id);
            redirectAttributes.addFlashAttribute("message",
                    "Prospect deleted");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",
                    "Unable to delete prospect with nonexisting id " + id);
        }
        redirectAttributes.addFlashAttribute("prospect", new ProspectEntity());
        redirectAttributes.addFlashAttribute("prospects", prospectService.getAllProspects());
        return "redirect:/";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            int addedCount = prospectsFromCSV(file.getInputStream());
            redirectAttributes.addFlashAttribute("message",
                    "Read " + addedCount + " prospects from uploaded file " + file.getOriginalFilename());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message",
                    "Error loading file " + file.getOriginalFilename() + "!");
        }
        redirectAttributes.addFlashAttribute("prospect", new ProspectEntity());
        redirectAttributes.addFlashAttribute("prospects", prospectService.getAllProspects());
        return "redirect:/";
    }

    @GetMapping({"/demo"})
    public String demo(RedirectAttributes redirectAttributes) {
        Resource prospectsFile = new ClassPathResource("prospects.txt");
        try {
            int addedCount = prospectsFromCSV(prospectsFile.getInputStream());
            redirectAttributes.addFlashAttribute("message",
                    "Read " + addedCount + " prospects from file " + prospectsFile.getFilename());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message",
                    "Error loading file " + prospectsFile.getFilename() + "!");
        }
        redirectAttributes.addFlashAttribute("prospect", new ProspectEntity());
        redirectAttributes.addFlashAttribute("prospects", prospectService.getAllProspects());
        return "redirect:/";
    }

    /**
     * Add prospect entities from CSV content
     *
     * @param inputStream InputStream source for CSV containing prospects
     */
    private int prospectsFromCSV(InputStream inputStream) {
        CSV csv = new CSV(inputStream);
        int i = 0;
        int addedCount = 0;
        for (List<String> strings : csv.getStrings()) {
            try {
                ProspectEntity prospectEntity = new ProspectEntity(strings);
                prospectService.saveOrUpdateProspect(prospectEntity);
                addedCount++;
            } catch (InvalidInputException e) {
                if (i > 0) {  // Skip first line header row error
                    logger.warn("Invalid CSV input: " + e.getMessage());
                }
            }
            i++;
        }
        return addedCount;
    }
}