package com.generation.progetto_finale;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.generation.progetto_finale.modelEntity.Frequency;
import com.generation.progetto_finale.modelEntity.StoredTask;
import com.generation.progetto_finale.modelEntity.Task;
import com.generation.progetto_finale.modelEntity.Task.TaskStatus;
import com.generation.progetto_finale.repositories.StoredTaskRepository;
import com.generation.progetto_finale.repositories.TaskRepository;

@Component
public class ScheduledJobs 
{
    @Autowired
    StoredTaskRepository stRepo;
    @Autowired
    TaskRepository tRepo;

    //secondi, minuti, ore, giorno del mese, numero del mese, giorno della settimana
    @Scheduled(cron = "0 0 1 ? * MON")
    public void newWeeklyTasks() 
    {
        List<StoredTask> stt = stRepo.findAllByFrequency(Frequency.SETTIMANALE);

        createTasks(stt);
    }

    @Scheduled(cron = "0 0 1 ? * MON")
    public void newBiweeklyTasks() 
    {
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
        List<StoredTask> stt = stRepo.findAllByFrequency(Frequency.MENSILE);

        createTasks(stt);
    }


    // @Scheduled(cron = "30 * * * * *")
    // public void metodoDiProva() 
    // {
    //     System.out.println("Qualsiasi cosa");
    //     newWeeklyTasks();
    // }


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

}
