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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Controller for receiving manually uploaded CSV input
 */
@Controller
public class UploadController {
    final ProspectService prospectService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor to avoid @Autowired
     *
     * @param prospectService JPA entity service
     */
    public UploadController(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            int addedCount = prospectsFromCSV(file.getInputStream());
            redirectAttributes.addFlashAttribute("message",
                    "Read " + addedCount + " prospects from uploaded file " + file.getOriginalFilename());
        } catch (IOException e) {
            // e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Error loading file " + file.getOriginalFilename() + "!");
        }
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