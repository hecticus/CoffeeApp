package daemonTask;

import models.Invoice;
import models.status.StatusInvoice;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeClosed extends TimerTask {

    private Timer timer;

    public TimeClosed(Timer t) {
        this.timer = t;
    }

    @Override
    public void run() {
        System.out.println("*** Cerrando facturas...");
        System.out.println("*** Cerrando facturas...");
        System.out.println("*** Cerrando facturas...");
        System.out.println("*** Hour Close Invoice"+ Time.valueOf(LocalTime.now()));
        closeInvoice();

    }

    public static void  closeInvoice(){
        List<Invoice> invoices = Invoice.findAllInvoiceActive();
        ZonedDateTime dateTime = ZonedDateTime.now();
        Job job = Job.findById(new Long(1));
        job.setClosedDate(dateTime);
        job.update();
        StatusInvoice status = new StatusInvoice();
        status.setId(new Long(12));
        if(!invoices.isEmpty()){
            for (Invoice inv : invoices){
                inv.setStatusInvoice(status);
                inv.setClosedDate(dateTime);
                inv.update();
            }
        }
    }
}
