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
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");


        Time hora = Time.valueOf(LocalTime.now()) ;

        System.out.println("*** 000000000000000000000."+ hora);

//        LocalDateTime todayAt6 = LocalDate.now().atTime(6, 29);
//        Calendar calender = Calendar.getInstance();
//        calender.set(Calendar.HOUR_OF_DAY, 24);
//        System.out.println("hora de calender"+ calender);
//        Date startTime = calender.getTime();
//
//        startTime.setHours(24);
//        System.out.println("hora de starttime"+ startTime);
//        Date date2pm = new java.util.Date();
//        date2pm.setHours(14);
//        date2pm.setMinutes(0);
//
//        Timer timer = new Timer();
//        timer.schedule( new TimeClosed(), todayAt6, new Long(86400000));

        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        // Si la hora es posterior a las 8am se programa la alarma para el dia siguiente
//        if (c.get(Calendar.HOUR_OF_DAY) >= 22) {
//            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
//        }

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 0);

        horaDespertar = c.getTime();
        System.out.println(horaDespertar);
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        // El despertador suena cada 24h (una vez al dia)
        int tiempoRepeticion = 86400000;

        int val = 60000;

        // Programamos el despertador para que "suene" a las 8am todos los dias
        Timer temporizador = new Timer();
        temporizador.schedule(new TimeClosed(), horaDespertar, val);


    }


}
