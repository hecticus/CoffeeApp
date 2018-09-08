package daemonTask;

import models.Invoice;
import models.status.StatusInvoice;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);

        // Si la hora es posterior a las 8am se programa la alarma para el dia siguiente
//        if (c.get(Calendar.HOUR_OF_DAY) >= 22) {
//            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
//        }

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 0);

        horaDespertar = c.getTime();
        System.out.println(horaDespertar);
        // El despertador suena cada 24h (una vez al dia)
        int tiempoRepeticion = 86400000;

        int val = 60000;

        // Programamos el despertador para que "suene" a las 8am todos los dias
        Timer temporizador = new Timer();
        temporizador.schedule(new TimeClosed(), horaDespertar, val);


    }


}
