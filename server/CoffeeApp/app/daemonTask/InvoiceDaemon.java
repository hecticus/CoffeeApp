package daemonTask;

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



        Job jobAux = Job.findById(new Long(1));
        if (jobAux == null){
            Time time = Time.valueOf("23:00:00");
            Job job = new Job();
            job.setId(new Long(1));
            job.setDescription("Time close Invoice");
            job.setCloseTime(time);
            job.setDelay(86400000);
            job.setStatus(true);
            job.save();
        }

//        Programamos para que cierre las facturas a la media noche
        Timer temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new InvoiceJob(), 0, 1800000);
    }

}
