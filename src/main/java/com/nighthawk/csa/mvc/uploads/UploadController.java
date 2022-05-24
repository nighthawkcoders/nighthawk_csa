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

/* UploadController
To upload dynamic image there are many considerations.  Here are highlights:

Step 1: Create a directory outside "static" folder and "limit" size
... i. ) MvcConfig.java is used to addResourceHandlers, SecurityConfig exception if using Spring Security
... ii.) MainWebAppInitializer.java sets limits on upload size

Step 2: Upload files in media directory
... i.  ) see "uploads" below and handling of filepath and web path
... ii. ) see "Files.write" or "stream.write" below as options in writing file to server

Step 3: Implement Frontend
... i.  ) upload.html uses Form with enctype="multipart/form-data"
... ii  ) MultipartFile is used below to extract data and metadata from upload action
... iii ) upload.html image tag requires preprocessor Thymeleaf syntax: <image th:src="@{__${file.file}__}" width="150px"></image>
 */
@Controller
public class UploadController {
    @Autowired
    private UploadRepository repo;

    // user action page:  upload controls and displays a history of images
    @GetMapping("/mvc/upload")
    public String mvcUpload(Model model) {
        List<Upload> files = repo.findAll();    // extract image history

        model.addAttribute("files", files);
        return "mvc/upload";
    }

    // uploader page: filesystem and database management of uploaded image
    @PostMapping("/mvc/uploader")
    public String mvcUploader(@RequestParam("filename") MultipartFile formFile, Model modelMap)  {
        /* The static directory is loaded at startup. UploadING images or makING changes to any files or
         folders under the static folder will not reflect as ApplicationContext is already initialized. */
        String filePath = "uploads/";       // thus, uploads defined outside of static
        String webPath = "/" + filePath;    // webPath

        // A database table, using Upload POJO, is remembers location of upload and associated metadata
        Upload repoFile = new Upload();
        repoFile.setFile(webPath + formFile.getOriginalFilename());
        repoFile.setType(formFile.getContentType());
        repoFile.setSize(formFile.getSize());

        // try/catch is in place, but error handling is not implemented (returns without alerts)
        try {
            // Creating the directory to store file
            File dir = new File( filePath );
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server
            byte[] bytes = formFile.getBytes();

            // File write alternatives (going with Stream for now as in theory it would be non-blocking)
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

            // JPA save
            repo.save(repoFile);

        } catch (IOException e) {
            e.printStackTrace();        // app stays alive, errors go to run console, /var/log/syslog
        }

        // Redirect back to action page
        return "redirect:/mvc/upload";
    }

}