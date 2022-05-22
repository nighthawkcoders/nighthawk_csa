package com.nighthawk.csa.mvc.content;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
    public String mvcUpload(@RequestParam("filename") MultipartFile formFile, Model modelMap) {
        Upload repoFile = new Upload();

        repoFile.setFile(formFile.getOriginalFilename());
        repoFile.setType(formFile.getContentType());
        repoFile.setSize(formFile.getSize());

        repo.save(repoFile);

        // Redirect to next step
        return "redirect:/mvc/content";
    }

}
