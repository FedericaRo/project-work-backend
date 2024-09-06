package com.generation.progetto_finale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.generation.progetto_finale.modelEntity.Communication;
import com.generation.progetto_finale.modelEntity.Frequency;
import com.generation.progetto_finale.modelEntity.StoredTask;
import com.generation.progetto_finale.modelEntity.Task;
import com.generation.progetto_finale.modelEntity.Task.TaskStatus;
import com.generation.progetto_finale.repositories.CommunicationRepository;
import com.generation.progetto_finale.repositories.StoredTaskRepository;
import com.generation.progetto_finale.repositories.TaskRepository;
import com.generation.progetto_finale.services.CommunicationDeleteService;


@Component
public class ScheduledJobs 
{
    @Autowired
    StoredTaskRepository stRepo;

    @Autowired
    TaskRepository tRepo;

    @Autowired
    CommunicationRepository cRepo;

    @Autowired
    CommunicationDeleteService comDeleteService;

    

    //secondi, minuti, ore, giorno del mese, numero del mese, giorno della settimana
    @Scheduled(cron = "0 0 1 ? * MON")
    public void newWeeklyTasks() 
    {
        setFinalStatusForWeeklyTaks();
        List<StoredTask> stt = stRepo.findAllByFrequency(Frequency.SETTIMANALE);

        createTasks(stt);
    }

    @Scheduled(cron = "0 0 1 ? * MON")
    public void newBiweeklyTasks() 
    {
        setFinalStatusForBiweeklyTaks();
        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); // Prendi banalmente il campo della settimana
        int weekNumber = currentDate.get(weekFields.weekOfWeekBasedYear()); // maniera terribile per prendere il numero della settimana 
        if( weekNumber % 2 != 0) return;

        List<StoredTask> stt = stRepo.findAllByFrequency(Frequency.BISETTIMANALE);

        createTasks(stt);
    }


    @Scheduled(cron = "0 0 1 1 * ?")
    public void newMonthlyTasks() 
    {
        setFinalStatusForMonthlyTaks();
        List<StoredTask> stt = stRepo.findAllByFrequency(Frequency.MENSILE);

        createTasks(stt);
    }

        //secondi, minuti, ore, giorno del mese, numero del mese, giorno della settimana

    @Scheduled(cron = "0 0 1 ? * MON")
    public void deleteOlderCommunications() 
    {
        System.out.println("Metodo eseguito ogni minuto: " + System.currentTimeMillis());


        LocalDateTime deletionDate = LocalDateTime.now().minusMonths(6);

        List<Communication> communicationsToDelete = cRepo.findByCreationDateBefore(deletionDate);


        for (Communication communication : communicationsToDelete)
        {
            System.out.println(communication.getId());
            comDeleteService.deleteCommunication(communication.getId());
        }

    }



    private void createTasks(List<StoredTask> tasks)
    {
        List<Task> realTasks = new ArrayList<>();

        for (StoredTask st : tasks) 
        {
            Task task = new Task();

            task.setName(st.getName());
            task.setDescription(st.getDescription());
            task.setFrequency(st.getFrequency());
            task.setStatus(TaskStatus.DAFARSI);

            realTasks.add(task);
        }

        tRepo.saveAll(realTasks);
    }

    private void setFinalStatusForWeeklyTaks()
    {
        List<Task> tasks = tRepo.findAll()
                            .stream()
                            .filter(t->t.getFrequency() == Frequency.SETTIMANALE)
                            .filter(t->t.getStatus() == TaskStatus.DAFARSI)
                            .toList();

        List<Task> taskForce = new ArrayList<>();

        for (Task t : tasks) 
        {
            t.setStatus(TaskStatus.INCOMPIUTO);
            taskForce.add(t);
        }

        tRepo.saveAll(taskForce);
    }

    private void setFinalStatusForBiweeklyTaks()
    {
        List<Task> tasks = tRepo.findAll()
                            .stream()
                            .filter(t->t.getFrequency() == Frequency.BISETTIMANALE)
                            .filter(t->t.getStatus() == TaskStatus.DAFARSI)
                            .toList();

        List<Task> taskForce = new ArrayList<>();

        for (Task t : tasks) 
        {
            t.setStatus(TaskStatus.INCOMPIUTO);
            taskForce.add(t);
        }

        tRepo.saveAll(taskForce);
    }

    private void setFinalStatusForMonthlyTaks()
    {
        List<Task> tasks = tRepo.findAll()
                            .stream()
                            .filter(t->t.getFrequency() == Frequency.MENSILE)
                            .filter(t->t.getStatus() == TaskStatus.DAFARSI)
                            .toList();

        List<Task> taskForce = new ArrayList<>();

        for (Task t : tasks) 
        {
            t.setStatus(TaskStatus.INCOMPIUTO);
            taskForce.add(t);
        }

        tRepo.saveAll(taskForce);
    }

}
