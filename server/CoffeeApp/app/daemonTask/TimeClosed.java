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

        Time hora = Time.valueOf(LocalTime.now()) ;
        System.out.println("*** 000000000000000000000."+ hora);

        closeInvoice();

        System.out.println("*** Cerrando facturas...");
        System.out.println("*** Cerrando facturas...");
        System.out.println("*** Cerrando facturas...");

    }

    public static void  closeInvoice(){
        List<Invoice> invoices = Invoice.findAllInvoiceActive();
        ZonedDateTime dateTime = ZonedDateTime.now();
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
