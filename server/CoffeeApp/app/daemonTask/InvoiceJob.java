package daemonTask;

import controllers.parsers.queryStringBindable.DateTime;
import org.jclouds.location.Zone;
import org.joda.time.Minutes;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class InvoiceJob extends TimerTask {



    @Override
    public void run(){

        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");

        Job job = Job.findById(new Long(1));

        Timer timer = new Timer();
        TimeClosed timeClo = new TimeClosed(timer);

        if (!job.isDeleted() || job !=null){

            if (job.getStatusJob().getId().intValue() == 60){

                Time timeClose = job.getCloseTime();
                System.out.println(timeClose);

                Time horaActual = new Time(System.currentTimeMillis());

                System.out.println("Hora actual"+ horaActual);

                    if(horaActual.getTime() >= job.getCloseTime().getTime()){
                        Long diff = job.getCloseTime().getTime() - horaActual.getTime();
                        long hours = TimeUnit.MILLISECONDS.toHours(diff);
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                        System.out.println("*** hour..."+ hours);
                        System.out.println("*** toSeconds..."+ minutes);

                        //        Programamos para que cierre las facturas a la media noche

                        timer.scheduleAtFixedRate(timeClo, job.getCloseTime(), job.getDelay());
                    }
            }

        }else if(job.getStop()){
            System.out.println("*** else...");

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

            timer.scheduleAtFixedRate(timeClo, horaDespertar, tiempoRepeticion);

        }

    }
}
