package com.generation.progetto_finale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.progetto_finale.repositories.ProfileRepository;

@Service
public class ImageService 
{

    @Autowired
   private ProfileRepository pRepo;
   
    // public String uploadImage(MultipartFile imageFile, Profile profile) throws IOException {
    //     // Profile profile = new Profile();

    //     profile.setFileName(imageFile.getOriginalFilename());
    //     profile.setFileContent(imageFile.getBytes());

    //     pRepo.save(profile);
    //     return "file uploaded successfully : " + imageFile.getOriginalFilename();
 

    //     // Profile imageToSave = Profile.builder()
    //     //         .name(imageFile.getOriginalFilename())
    //     //         .type(imageFile.getContentType())
    //     //         .imageData(ImageUtils.compressImage(imageFile.getBytes()))
    //     //         .build();
    //     //         pRepo.save(imageToSave);
    //     // return "file uploaded successfully : " + imageFile.getOriginalFilename();
    // }

}
