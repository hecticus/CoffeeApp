package daemonTask;

import models.Invoice;
import models.status.StatusInvoice;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.TimerTask;

public class TimeClosed extends TimerTask {

    @Override
    public void run() {
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

        /* replace with the actual task */
        try {
            Thread.sleep(15 * 1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        /* end task processing */


        List<Invoice> invoices = Invoice.findAllInvoiceActive();
        StatusInvoice status = new StatusInvoice();
        status.setId(new Long(12));
        if(invoices != null){
            for (Invoice inv : invoices){
                inv.setStatusInvoice(status);
                inv.update();
            }
        }
        System.out.println("00000000000");
        System.out.println("00000000000");
        System.out.println("00000000000");
        System.out.println("00000000000");
        System.out.println("00000000000");
        System.out.println("00000000000");
    }
}
