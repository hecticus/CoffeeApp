package daemonTask;

import models.Invoice;
import models.status.StatusInvoice;

import java.util.List;
import java.util.TimerTask;

public class TimeClosed extends TimerTask {

    @Override
    public void run() {


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
        System.out.println("jsjsjsjjjsjsjsjsjsjsj");
        System.out.println("jsjsjsjjjsjsjsjsjsjsj");
        System.out.println("jsjsjsjjjsjsjsjsjsjsj");
        System.out.println("jsjsjsjjjsjsjsjsjsjsj");
    }
}
