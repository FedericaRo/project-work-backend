package com.generation.progetto_finale.services;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.generation.progetto_finale.modelEntity.Communication;
import com.generation.progetto_finale.repositories.CommunicationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommunicationDeleteService 
{

    @Autowired
    CommunicationRepository cRepo;

    public Communication deleteCommunication(Integer id)
    {

    Optional<Communication> c = cRepo.findById(id);
    
    if (c.isEmpty())
    throw new EntityNotFoundException("Comunicazione non esistente");
    
            String pdfpath = c.get().getPdfFilePath();
            
        if (pdfpath != null)
        {
            try {
                getPdfToDelete(pdfpath);
                
            } catch (Exception e) {
                throw new RuntimeException("Errore nell'eliminazione del pdf");
            }
        }
        
        cRepo.deleteById(id); 
        
        return c.get();
    }
    


    public String getPdfToDelete(String pdfpath) throws IOException 
    {

        System.out.println(pdfpath);
        deletepfd(pdfpath);
        // Ritorna l'immagine come ResponseEntity
        return pdfpath;
    }

    private void deletepfd(String userPath) { 
    File fileOrDirectory = new File(userPath);
    if (fileOrDirectory.exists()) {
        if (fileOrDirectory.isDirectory()) {
            deleteDirectory(fileOrDirectory);
        } else if (fileOrDirectory.isFile()) {
            fileOrDirectory.delete(); // Elimina il file
            File parentDirectory = fileOrDirectory.getParentFile();
            if (parentDirectory != null && parentDirectory.list().length == 0) {
                parentDirectory.delete(); // Elimina la directory se vuota
            }
        }
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
        System.out.println("directoryy " + directory);
        return directory.delete(); 
    }
}
