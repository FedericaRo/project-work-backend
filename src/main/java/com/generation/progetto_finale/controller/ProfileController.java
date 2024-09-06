package com.generation.progetto_finale.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.generation.progetto_finale.auth.model.UserEntity;
import com.generation.progetto_finale.auth.repository.UserRepository;
import com.generation.progetto_finale.controller.exceptions.DoYouWantAFootballTeamException;
import com.generation.progetto_finale.dto.ProfileDTO;
import com.generation.progetto_finale.dto.mappers.ProfileService;
import com.generation.progetto_finale.modelEntity.Profile;
import com.generation.progetto_finale.repositories.ProfileRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/profiles")
public class ProfileController 
{

    @Autowired
    ProfileRepository pRepo;

    @Autowired
    UserRepository uRepo;

    @Autowired
    ProfileService pServ;


    @GetMapping
    public List<Profile> getAll()
    {
        return pRepo.findAll();
    }


    @GetMapping("/{username}")
    public List<ProfileDTO> getAllByUser(@PathVariable String username)
    {
        return pServ.toDTO(pRepo.findProfilesByUsername(username));
    }
    

    @PostMapping("/newProfile")
    public ProfileDTO addProfile(@RequestBody ProfileDTO profile) 
    {
        List<Profile> profiles = pRepo.findProfilesByUsername(profile.getUser());

        if(profiles.size() >= 6)
        {
            throw new DoYouWantAFootballTeamException("hai già raggiunto il numero massimo di profili per questo utente");
        }
        else
        {
            System.out.println("Adding new profile");
    
            String username = profile.getUser();
    
            Optional<UserEntity> userOptional = uRepo.findByUsername(username);
    
            if (userOptional.isEmpty())
                throw new EntityNotFoundException("Username non esiste");
    
            UserEntity user = userOptional.get();
    
            Profile p = pServ.toEntity(profile);
            p.setUser(user);
    
            return pServ.toDTO(pRepo.save(p));
        }
    }


    @GetMapping("/profile")
    public Profile getOne(@RequestParam String name, @RequestParam String surname) 
    {
        return pRepo.findByNameAndSurname(name, surname);
    }


    @DeleteMapping("{profileId}")
    public ProfileDTO deleteProfile(@PathVariable Integer profileId)
    {
        Optional<Profile> profileToDelete = pRepo.findById(profileId);
        if (profileToDelete.isEmpty()) 
            throw new EntityNotFoundException("profilo non trovato");
        
        pRepo.delete(profileToDelete.get());

        return pServ.toDTO(profileToDelete.get());
    }
    

    @PostMapping("/imgupload/{profileid}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer profileid) 
    {
        if (!file.getOriginalFilename().endsWith("jpg") && !file.getOriginalFilename().endsWith("jpeg") && !file.getOriginalFilename().endsWith("png"))
            throw new RuntimeException("Formato non valido");

        
        Profile profile = pRepo.findById(profileid).get();

        // Impostiamo il path della cartella
        String basePath = System.getProperty("user.home")+"\\project-work-backend\\images\\";
        String userPath = basePath+profile.getName()+"-"+profile.getSurname();

        // Distruggiamo e ricreiamo la cartella per svuotarla
        File directory = new File(userPath);
        deleteDirectory(directory);
        directory.mkdirs();
        
        // Mettiamo il percorso del file (cartella + nome del file)
        String uploadDir = userPath+"\\"+file.getOriginalFilename();

        try 
        {
            File img = new File(uploadDir);
            if (img.length()/1000000 > 3)
                throw new RuntimeException("Immagine troppo grande"); 

            // Salva il file nella cartella specificata
            file.transferTo(img);
            profile.setImagePath(uploadDir);
            pRepo.save(profile);
            return uploadDir;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return "File upload failed";
        }
    }

    private static boolean deleteDirectory(File directory) 
    {
        if (directory.isDirectory()) 
        {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) 
                {
                    // Ricorsione
                    deleteDirectory(file);
                }
            }
        }
        return directory.delete(); 
    }


    @GetMapping("/images/{profileid}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer profileid) throws IOException 
    {

        // Prende il percorso dell'immagine salvato nel database
        String imgpath = pRepo.findById(profileid).get().getImagePath();

        if (imgpath == null || imgpath.isEmpty()) 
        {
            return ResponseEntity.noContent().build();
        }


        System.out.println(imgpath);

        // Legge l'immagine e la trasforma in un array di bytes
        File imgFile = new File(imgpath);
        InputStream in = new FileInputStream(imgFile);
        byte[] imageBytes = StreamUtils.copyToByteArray(in);
        in.close();

        // Impostiamo l'header della response per dire che tipo di immagine è e quanto è grande
        HttpHeaders headers = new HttpHeaders();
        //facciamo uno switch

        MediaType format;
        switch (imgpath.split("\\.")[1]) 
        {
            case "jpg", "jpeg":
                format = MediaType.IMAGE_JPEG;
                break;
            case "png":
                format = MediaType.IMAGE_PNG;
            default:
                format = null;
                break;
        }

        headers.setContentType(format); 
        headers.setContentLength(imageBytes.length);

        // Ritorna l'immagine come ResponseEntity
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ProfileDTO putMethodName(@PathVariable Integer id, @RequestBody ProfileDTO entity) 
    {
        
        Profile profile = pRepo.findById(id).get();

        profile.setName(entity.getName());
        profile.setSurname(entity.getSurname());

        entity = pServ.toDTO(pRepo.save(profile));

        return entity;
    }
}







