package com.example.mortageplan.controller;

import com.example.mortageplan.entity.InvalidInputException;
import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import com.example.mortageplan.util.CSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UploadController {
    @Autowired
    ProspectService prospectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            CSV csv = new CSV(file.getInputStream());
            int i = 0;
            for (List<String> strings : csv.getStrings()) {
                try {
                    ProspectEntity prospectEntity = new ProspectEntity(strings);
                    prospectService.saveOrUpdateProspect(prospectEntity);
                } catch (InvalidInputException e) {
                    if (i > 0) {  // Skip first line header row error
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
}