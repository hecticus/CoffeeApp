package models.manager.responseUtils.responseObject;

/**
 * Created by drocha on 25/04/17.
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.manager.responseUtils.CustomDateTimeSerializer;
import org.joda.time.DateTime;

import javax.persistence.Column;

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
