package daemonTask;

import java.sql.Time;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class InvoiceJob extends TimerTask {

    TimerTaskInfo timerInfo;

    InvoiceJob(TimerTaskInfo  info){
        timerInfo = info;
    }

    @Override
    public void run(){

        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        System.out.println("*** Starting job...");
        Time hora = Time.valueOf(LocalTime.now()) ;
        System.out.println("****** Hour Starting job..."+ Time.valueOf(LocalTime.now()));
        Job job = Job.findById(new Long(1));

        if (!job.isDeleted() || job !=null){
            if (job.getStatusJob().getId().intValue() == 60){
                int diffUpdate = job.getUpdatedAt().toLocalTime().compareTo(this.timerInfo.getTimeUpdate().toLocalTime());
                long diffHora = job.getCloseTime().getTime() - hora.getTime();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diffHora) % 60;
                long horas = TimeUnit.MILLISECONDS.toHours(diffHora) % 24;

                System.out.println("diffHours = "+ horas );
                System.out.println("diffmenutes = "+ minutes );
                if( diffUpdate == 0 & this.timerInfo.isStatus()){
                    this.timerInfo.increment();

                    if(minutes < 0){
                        this.timerInfo.setStatus(false);
                        System.out.println("++++++++++++++++++++++");
                        this.timerInfo.getJobTimer().schedule(new TimeClosed(this.timerInfo.getJobTimer()),
                                                        0, job.getDelay() );
                    }

                }else if(diffUpdate > 0){
                    this.timerInfo.setStatus(true);
                    this.timerInfo.setTimeUpdate(job.getUpdatedAt());
                    System.out.println("**Update Job table**");
                    if(job.getStop()){
                        this.timerInfo.stopTimerJob();
                    }

                }

            }
        }

    }
}
