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

        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);

        c.set(Calendar.HOUR_OF_DAY, 20);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 0);

        horaDespertar = c.getTime();
        System.out.println(horaDespertar);
//        se cierran las facturas cada 24h (una vez al dia)
        int tiempoRepeticion = 86400000;
//        int tiempoRepeticion = 1800;

        // Time houer= c.getTime().getHours();
        Job jobAux = Job.findById(new Long(1));
        if (jobAux == null){
            Time time = Time.valueOf("23:00:00");
            Job job = new Job();
            job.setId(new Long(1));
            job.setDescription("Time close Invoice");
            job.setCloseTime(time);
            job.setDelay(tiempoRepeticion);
            job.setStatus(true);
            job.save();
        }

//        Programamos para que cierre las facturas a la media noche
        Timer temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimeClosed(), horaDespertar, tiempoRepeticion);
    }

}
