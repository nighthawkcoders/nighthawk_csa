package com.nighthawk.csa.mvc.content;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

@Controller
public class ContentUploadController {
    @Autowired
    private UploadRepository repo;

    @GetMapping("/mvc/content")
    public String mvcContent(Model model) {
        List<Upload> files = repo.findAll();

        model.addAttribute("files", files);
        return "mvc/content";
    }

    @PostMapping("/mvc/content/upload")
    public String mvcUpload(@RequestParam("filename") MultipartFile formFile, Model modelMap)  {
        Upload repoFile = new Upload();
        String filePath = "target/classes/static/images/uploads/";
        String webPath = "/images/uploads/";

        repoFile.setFile(webPath + formFile.getOriginalFilename());
        repoFile.setType(formFile.getContentType());
        repoFile.setSize(formFile.getSize());

        try {
            // Get the file and save it somewhere
            Path path = Paths.get(filePath + formFile.getOriginalFilename());
            byte[] bytes = formFile.getBytes();
            Files.write(path, bytes);
            repo.save(repoFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Redirect to next step
        return "redirect:/mvc/content";
    }

}
