package ru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin
@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    FileUpload fileUpload;
    String url ="";

    @GetMapping("/")
    public String m(Model model)
    {
        model.addAttribute("linkToFile",url);
        System.out.println("Url from get" + url);
        return "site";
    }

    @PostMapping("/download")
    public String createFile(@RequestParam("file") MultipartFile multipartFile, Model model) {
         url = fileUpload.upload(multipartFile);
        model.addAttribute("linkToFile",url);
        System.out.println("Url from post" + url);
        return "redirect:/";
    }
}


