package daemonTask;

import controllers.parsers.queryStringBindable.DateTime;
import org.jclouds.location.Zone;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class InvoiceJob extends TimerTask {

    @Override
    public void run(){

        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");

        Job job = Job.findById(new Long(1));

        if (!job.isDeleted() || job !=null){

            Time timeClose = job.getCloseTime();
            System.out.println(timeClose);

            Time horaActual = new Time(System.currentTimeMillis());


            System.out.println("Hora actual"+ horaActual);

            if (job.getStatus()){

            }





        }


        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 0);

        horaDespertar = c.getTime();
        System.out.println(horaDespertar);
//        se cierran las facturas cada 24h (una vez al dia)
        int tiempoRepeticion = 86400000;
//        int tiempoRepeticion = 1800;
        int halfHouer = 1800000;



        if (c.get(Calendar.HOUR_OF_DAY) >= 23) {
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        }


        //        Programamos para que cierre las facturas a la media noche
        Timer temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimeClosed(), horaDespertar, tiempoRepeticion);
    }
}
