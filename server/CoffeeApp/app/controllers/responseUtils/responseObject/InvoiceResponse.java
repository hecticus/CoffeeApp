package controllers.responseUtils.responseObject;

/**
 * Created by sm21 on 10/05/18.
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import controllers.responseUtils.CustomDateTimeSerializer;
import org.joda.time.DateTime;

public class InvoiceResponse extends AbstractEntityResponse
{
  
    public static class Provider
    {
        public Long idProvider;
        public String fullNameProvider;
        public String addressProvider;
        public String phoneNumberProvider;
        public String emailProvider;
        public String contactNameProvider;
    }

    public Long idInvoice;
    public Provider provider;
    public Integer statusInvoice;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public DateTime startDateInvoice;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public DateTime closedDateInvoice;
    public Double totalInvoice;
}
