package daemonTask;

import java.sql.Time;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class InvoiceJob extends TimerTask {

    Timer timer = new Timer();

    Timer timerDb;
    ZonedDateTime updateStar;
    ZonedDateTime updateEnd;


    InvoiceJob(Timer t, ZonedDateTime s){
        timerDb = t;
        updateStar = s;
    }

    @Override
    public void run(){

        Job job = Job.findById(new Long(1));
        System.out.print("*** .");
        System.out.print("*** .");
        System.out.println();
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        Time hora = Time.valueOf(LocalTime.now()) ;
        System.out.println("****** Starting DaemonTask..."+ hora);


        if (!job.isDeleted() || job !=null){
            if (job.getStatusJob().getId().intValue() == 60){
                int diffUpdate = job.getUpdatedAt().toLocalTime().compareTo(this.updateStar.toLocalTime());
                long diffHora = hora.getTime() - job.getCloseTime().getTime();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diffHora) % 60;
                long horas = TimeUnit.MILLISECONDS.toHours(diffHora) % 24;

                System.out.println(horas +"   HORAS1               sdsd");
                System.out.println(minutes +"   mINUTTTTTOS              sdsd");
                if( diffUpdate == 0){
                    this.updateEnd = updateStar;
                    System.out.println("*** IGGGGGGGGGGGGGGGGGGGGGUAL...");
                    TimeClosed timeClo = new TimeClosed(timer);


                }else if(diffUpdate > 0){
                    System.out.println("*** mAYOOOOOORRR...");

                }

                System.out.println();

            }
        }


/*




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



            int diff = job.getUpdatedAt().toLocalTime().compareTo(this.updateStar.toLocalTime());
                if( diff == 0){
                    this.updateEnd = updateStar;
                    System.out.println("*** IGGGGGGGGGGGGGGGGGGGGGUAL...");
                    TimeClosed timeClo = new TimeClosed(timer);
                }else if(diff > 0){
                    System.out.println("*** mAYOOOOOORRR...");
                }else{
                    System.out.println("*** mEN OOOOOORRR       ...");
                }

        }*/

    }
}
