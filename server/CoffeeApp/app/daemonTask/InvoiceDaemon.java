package daemonTask;

import models.status.StatusJob;

import java.sql.Time;
import java.util.*;

public class InvoiceDaemon {

    public InvoiceDaemon() {
        changesStatus();
    }


    public void changesStatus(){
        Boolean value = true;

        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");

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

//        Programamos para que cierre las facturas a la media noche
        Timer temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new InvoiceJob(), 3000000, 5000000);
    }

}
