package daemonTask;

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
        System.out.println("****** Hour Starting DaemonTask..."+ Time.valueOf(LocalTime.now()));

        Job job =  Job.findById(new Long(1));
        // Periodo de repeticion 15m = 900000
        int periodTime = 400000;
        // Tiempo de delay 5m = 300000
        int delayTime = 50000;

        Timer timer = new Timer("timerDaemon", true);
        TimerTaskInfo taskInfo = new TimerTaskInfo(job.getUpdatedAt(), timer);


        timer.scheduleAtFixedRate(new InvoiceJob(taskInfo), delayTime, periodTime);
    }

}
