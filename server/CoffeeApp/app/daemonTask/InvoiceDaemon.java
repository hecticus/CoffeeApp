package daemonTask;

import java.util.*;

public class InvoiceDaemon {

    public InvoiceDaemon() {
        changesStatus();
    }


    public void changesStatus(){
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");
        System.out.println("*** Starting DaemonTask...");

        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 0);

        horaDespertar = c.getTime();
        System.out.println(horaDespertar);
//        se cierran las facturas cada 24h (una vez al dia)
//        int tiempoRepeticion = 86400000;
        int tiempoRepeticion = 1800000;

//        Programamos para que cierre las facturas a la media noche
        Timer temporizador = new Timer();
        temporizador.schedule(new TimeClosed(), horaDespertar, tiempoRepeticion);
    }

}
