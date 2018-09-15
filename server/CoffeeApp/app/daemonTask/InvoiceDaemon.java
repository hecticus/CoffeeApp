package daemonTask;

import models.status.StatusJob;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

public class InvoiceDaemon {

    public InvoiceDaemon() {
        changesStatus();
    }

    public void changesStatus(){
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");
        System.out.println("****** Hour Starting DaemonTask..."+ Time.valueOf(LocalTime.now()));

        // Llenamos tabla Job
        Job jobAux = Job.findById(new Long(1));
        StatusJob status = new StatusJob();
        status.setId(new Long(60));
        if (jobAux == null){
            Time time = Time.valueOf("23:00:00");
            Job job = new Job();
            job.setId(new Long(1));
            job.setDescription("Time close Invoice");
            job.setCloseTime(time);
            job.setDelay(86400000);
            job.setStatusJob(status);
            job.setStop(false);
            job.save();
        }

        // Periodo de repeticion 15m
        int periodTime = 400000;
        // Tiempo de delay 5m
        int delayTime = 50000;

        Timer timer = new Timer("timerDaemon", true);
        TimerTaskInfo taskInfo = new TimerTaskInfo( Job.findById(new Long(1)).getUpdatedAt(), timer);


        timer.scheduleAtFixedRate(new InvoiceJob(taskInfo),
                                    delayTime, periodTime);
    }

}
