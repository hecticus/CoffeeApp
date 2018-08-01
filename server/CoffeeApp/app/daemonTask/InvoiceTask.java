package daemonTask;
import com.google.inject.AbstractModule;


public class InvoiceTask extends AbstractModule {
    @Override
    protected void configure() {
        bind(InvoiceDaemon.class).asEagerSingleton();
    }
}
