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
import com.generation.progetto_finale.dto.ProfileDTO;
import com.generation.progetto_finale.dto.mappers.ProfileService;
import com.generation.progetto_finale.modelEntity.Profile;
import com.generation.progetto_finale.repositories.ProfileRepository;

import jakarta.persistence.EntityNotFoundException;


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


    @GetMapping("/profile")
    public Profile getOne(@RequestParam String name, @RequestParam String surname) 
    {
        return pRepo.findByNameAndSurname(name, surname);
    }
    

    @PostMapping("/imgupload/{profileid}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer profileid) 
    {
        // Controlliamo che il file sia un immagine
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
                throw new RuntimeException("Immagine troppo grande"); //TODO lanciare eccezione personalizzata NON CON NOMI DI SANTO
            // Salva il file nella cartella specificata
            file.transferTo(img);
            profile.setImagePath(uploadDir);
            pRepo.save(profile);
            return uploadDir;
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed";
        }
    }

    private static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Ricorsione
                    deleteDirectory(file);
                }
            }
        }
        return directory.delete(); 
    }


    @GetMapping("/images/{profileid}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer profileid) throws IOException {

        // Prende il percorso dell'immagine salvato nel database
        String imgpath = pRepo.findById(profileid).get().getImagePath();

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
        switch (imgpath.split("\\.")[1]) {
            case "jpg", "jpeg":
                format = MediaType.IMAGE_JPEG;
                break;
            case "png":
                format = MediaType.IMAGE_PNG;
            default:
                format = null;
                // LANCIARE ECCEZIONE PER FORMATO NON VALIDO
                break;
        }
        headers.setContentType(format); 
        headers.setContentLength(imageBytes.length);

        // Ritorna l'immagine come ResponseEntity
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }


    // @GetMapping("/test")
    // public List<Profile> getTest(HttpServletRequest request)
    // {
    //     // HttpServletRequest request;
    //     String token = getJWTFromRequest(request);
    //     if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
    //         String username = tokenGenerator.getUsernameFromJWT(token);
    //         // List<String> roles = tokenGenerator.getRolesFromJWT(token);
    //         System.out.println(username);
    //         // System.out.println(roles);
    //         List<Profile> filteredProfiles = pRepo.findProfilesByUsername(username);
    //         System.out.println(filteredProfiles);
    //     }
    //     // System.out.println(request);
    //     return pRepo.findAll();
    // }

    // private String getJWTFromRequest(HttpServletRequest request) {
    //     String bearerToken = request.getHeader("Authorization");
    //     if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
    //         return bearerToken.substring(7);
    //     }
    //     return null;

    // }

    // @GetMapping("/testImage")
    // public Profile testImage(@RequestParam("image") MultipartFile file) throws IOException
    // {
    //     Profile profile = new Profile();
    //     profile.setName("Santo");
    //     profile.setSurname("Caldarella");
    //     profile.setFileName(file.getOriginalFilename());
    //     profile.setFileContent(file.getBytes());

        
    //     profile = pRepo.save(profile);
    //     System.out.println(profile);

    //     return profile;
        
    // }


    // @PostMapping("/createProfile")
    // public Profile createNewProfile(@RequestBody Profile profile)
    // {

    //     profile.image
    //     MultipartFile file = new MultipartFile() {
    //         file.setFileName(profile.getFileName());
    //     };

    //     uploadImage
    // }
    
}







