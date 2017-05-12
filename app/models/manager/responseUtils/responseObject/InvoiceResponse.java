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
        public String fullName;
        public String address;
        public String phoneNumber;
        public String email;
        public String typeProvider;
        public String contactName;
    }

    public Long idInvoice;
    public Provider provider;
    public Integer status;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public DateTime startDate;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public DateTime closedDate;
    public Double total;
}
