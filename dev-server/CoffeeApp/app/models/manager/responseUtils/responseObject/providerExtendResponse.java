package models.manager.responseUtils.responseObject;

import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 02/05/17.
 */
public class providerExtendResponse {

    public Long idprovider;
    public Long docId;
    public String fullName;
    public String address;
    public String phoneNumber;
    public String email;
    public String photo;
    public String contactName;

    public static class Invoice
    {
        public Long idInvoice;
        public Integer status;
        public DateTime startDate;
        public DateTime closedDate;
        public Double total;
    }

   public List<Invoice> invoices = new ArrayList<>();

}

