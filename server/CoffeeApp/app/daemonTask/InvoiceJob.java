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

    TimerTaskInfo timerInfo;

    InvoiceJob(TimerTaskInfo  info){
        timerInfo = info;
    }

    @Override
    public void run(){

        Job job = Job.findById(new Long(1));
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        Time hora = Time.valueOf(LocalTime.now()) ;
        System.out.println("****** Starting DaemonTask..."+ hora);


        if (!job.isDeleted() || job !=null){
            if (job.getStatusJob().getId().intValue() == 60){
                int diffUpdate = job.getUpdatedAt().toLocalTime().compareTo(this.timerInfo.getTimeUpdate().toLocalTime());
                long diffHora = job.getCloseTime().getTime() - hora.getTime();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diffHora) % 60;
                long horas = TimeUnit.MILLISECONDS.toHours(diffHora) % 24;

                System.out.println(horas +"   HORAS1               sdsd");
                System.out.println(minutes +"   mINUTTTTTOS              sdsd");
                if( diffUpdate == 0 & this.timerInfo.getStatus()){
                    this.timerInfo.increment();


                    System.out.println(this.timerInfo.getTimes());
                    System.out.println("*** IGGGGGGGGGGGGGGGGGGGGGUAL...");

                    if(minutes < 0 ){
                        this.timerInfo.setStatus(false);
                        System.out.println("***++++++++++++++++++++++");
                        this.timer.schedule(new TimeClosed(this.timer), 0, job.getDelay() );

                    }

                }else if(diffUpdate > 0){
                    this.timerInfo.setStatus(true);
                    this.timerInfo.setTimeUpdate(job.getUpdatedAt());
                    System.out.println("*** mAYOOOOOORRR...");

                }

            }
        }

    }
}
