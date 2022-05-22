package com.nighthawk.csa.mvc.upload;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;

@Controller
public class UploadController {
    @Autowired
    private UploadRepository repo;

    @GetMapping("/mvc/upload")
    public String mvcUpload(Model model) {
        List<Upload> files = repo.findAll();

        model.addAttribute("files", files);
        return "mvc/upload";
    }

    @PostMapping("/mvc/uploader")
    public String mvcUploader(@RequestParam("filename") MultipartFile formFile, Model modelMap)  {
        Upload repoFile = new Upload();
        String filePath = "target/classes/static/images/uploads/";
        String webPath = "/images/uploads/";

        repoFile.setFile(webPath + formFile.getOriginalFilename());
        repoFile.setType(formFile.getContentType());
        repoFile.setSize(formFile.getSize());

        try {
            // Creating the directory to store file
            File dir = new File( filePath );
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server
            byte[] bytes = formFile.getBytes();

            // File write alternatives (going with Stream for now)
            if (false) {
                Path path = Paths.get(filePath + formFile.getOriginalFilename());
                Files.write(path, bytes);
            } else {
                String path = filePath + formFile.getOriginalFilename();
                File serverFile = new File( path );
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            }

            repo.save(repoFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Redirect to next step
        return "redirect:/mvc/upload";
    }

}
