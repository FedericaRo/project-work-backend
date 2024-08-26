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

import com.generation.progetto_finale.dto.CommunicationDTO;
import com.generation.progetto_finale.dto.mappers.CommunicationService;
import com.generation.progetto_finale.modelEntity.Communication;
import com.generation.progetto_finale.repositories.CommunicationRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/communications")
public class CommunicationController 
{
    @Autowired
    CommunicationRepository cRepo;
    @Autowired
    CommunicationService cServ;

    @GetMapping
    public List<Communication> getAll() 
    {
        return cRepo.findAll();
    }

    @PostMapping("/newCommunication")
    public CommunicationDTO addNewCommunication(@RequestBody CommunicationDTO dto)
    {
        System.out.println(dto);
        /**
         * Da ReqBody ottengo un DTO e tramite il service lo trasformo in entità
         */
        System.out.println(dto);
        Communication c = cServ.toEntity(dto);

        /**
         * Salvataggio della entità
         */
        c = cRepo.save(c);

        /**
         * Processo inverso rispetto a prima, restituisco l'entità che viene poi salvata come DTO 
         * e infine Jsonizzata
         */
        return cServ.toDTO(c);
    }


    @DeleteMapping("/{id}")
    public Communication deleteCommunication(@PathVariable Integer id)
    {
        Optional<Communication> c = cRepo.findById(id);

        if (c.isEmpty())
            throw new EntityNotFoundException("Comunicazione non esistente");

        cRepo.deleteById(id); 
        
        return c.get();
    }


    @PostMapping("/pdfupload/{communicationid}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Integer communicationid) 
    {
        // Controlliamo che il file sia un pdf
        if (!file.getOriginalFilename().endsWith("pdf"))
            throw new RuntimeException("Formato non valido");
        
        Optional<Communication> communicationOptional = cRepo.findById(communicationid);

        if (communicationOptional.isEmpty())
            throw new EntityNotFoundException("La comunicazione non esiste");

        Communication communication = communicationOptional.get();

         // Impostiamo il path della cartella
         String basePath = System.getProperty("user.home")+"\\project-work-backend\\pdfs\\";
         String userPath = basePath+communication.getCommunicationName()+"-"+communication.getId();

         // Distruggiamo e ricreiamo la cartella per svuotarla
        File directory = new File(userPath);
        deleteDirectory(directory);
        directory.mkdirs();

        // Mettiamo il percorso del file (cartella + nome del file)
        String uploadDir = userPath+"\\"+file.getOriginalFilename();

        try 
        {

            File pdf = new File(uploadDir);
            System.out.println("pdf length " + pdf.length());
            if (pdf.length()/1000000 > 5) //! Non funziona la length è sempre 0
                throw new RuntimeException("File pdf troppo grande"); //TODO lanciare eccezione personalizzata NON CON NOMI DI SANTO
            // Salva il file nella cartella specificata
            file.transferTo(pdf);
            communication.setPdfFilePath(uploadDir);
            cRepo.save(communication);
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

    @GetMapping("/pdf/{communicationid}")
    public ResponseEntity<byte[]> getPdf(@PathVariable Integer communicationid) throws IOException {


        Optional<Communication> communicationOptional = cRepo.findById(communicationid);

        if (communicationOptional.isEmpty())
            throw new EntityNotFoundException("La comunicazione non esiste");
        
        
        // Prende il percorso dell'immagine salvato nel database
        String pdfpath = communicationOptional.get().getPdfFilePath();

        // Check if the image path is null or empty
        if (pdfpath == null || pdfpath.isEmpty()) {
            // Return a 204 No Content status if no image is found
                return ResponseEntity.noContent().build();
            }

        System.out.println(pdfpath);
        // Legge l'immagine e la trasforma in un array di bytes
        File pdfFile = new File(pdfpath);
        InputStream in = new FileInputStream(pdfFile);
        byte[] pdfBytes = StreamUtils.copyToByteArray(in);
        in.close();



        // Impostiamo l'header della response per dire che tipo di immagine è e quanto è grande
        HttpHeaders headers = new HttpHeaders();
        //facciamo uno switch

        MediaType format;
        switch (pdfpath.split("\\.")[1]) {
            case "pdf":
                format = MediaType.APPLICATION_PDF;
                break;
            default:
                format = null;
                // LANCIARE ECCEZIONE PER FORMATO NON VALIDO
                break;
        }
        headers.setContentType(format); 
        headers.setContentLength(pdfBytes.length);

        // Ritorna l'immagine come ResponseEntity
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    

}