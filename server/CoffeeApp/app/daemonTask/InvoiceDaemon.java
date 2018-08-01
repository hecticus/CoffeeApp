package daemonTask;

import models.Invoice;
import models.status.StatusInvoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class InvoiceDaemon extends TimerTask {

    public InvoiceDaemon() {
        changesStatus();
    }

    @Override
    public void run() {

//        closeInvoice();
    }


    public void changesStatus(){
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");
        System.out.println("*** Loading control access tables...");

        LocalDateTime todayAt6 = LocalDate.now().atTime(6, 29);
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY, 24);
        System.out.println("hora de calender"+ calender);
        Date startTime = calender.getTime();

        startTime.setHours(24);
        System.out.println("hora de starttime"+ startTime);
//        Date date2pm = new java.util.Date();
//        date2pm.set.setHour(14);
//        date2pm.setMinutes(0);

//        Timer timer = new Timer();
//
//        timer.schedule( run(), todayAt6, 86400000);
    }




//    public void closeInvoice() {
//        List<Invoice> invoices = Invoice.findAllInvoiceActive();
//        StatusInvoice status = StatusInvoice.findById(new Long(12));
//        for (Invoice inv : invoices){
//            inv.setStatusInvoice(status);
//            inv.update();
//        }
//        System.out.println("jsjsjsjjjsjsjsjsjsjsj");
//    }

    private static Date getTomorrowMorning2AM(){

        Date date12am = new java.util.Date();
        date12am.setHours(24);
        date12am.setMinutes(0);

        return date12am;
    }

}
