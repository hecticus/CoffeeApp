package models.responseUtils.responseObject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.responseUtils.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
public class InvoiceDetailResponse extends AbstractEntityResponse {


    public static class Invoice
    {

        public static class Provider
        {
            public Long idProvider;
            public String fullName;
            public String address;
            public String phoneNumber;
            public String email;
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
   
    public Invoice invoice;

    public static class ItemType
    {
        public Long idItemType;
        public String name;
        public Float cost;
        public Integer status;


        public static class Unit
        {
            public Long idUnit;
            public String name;
            public Integer status;
        }

        public Unit unit;
    }
    public ItemType itemType;

    public static class InvoiceDetailPurity
    {

        public static class Purity
        {
            public Long idPurity;
            public String name;
            public Integer DiscountRate;
            public Integer status;
        }
        public Long idInvoiceDetailPurity;
        public Purity purity;
        public Integer valueRateInvoiceDetailPurity;
        public Integer totalDiscountPurity;
    }

    public List<InvoiceDetailPurity> invoiceDetailPurity = new ArrayList<>();


    public class Lot
    {
        public Long idLot;
        private String name;
        private String area;
        private String farm;
        private Double heigh;
    }
    public Lot lot;

    public Long idInvoiceDetail;
    public Float cost;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public DateTime startDate;
    public Integer amount;
    public boolean freight;
    public String note;
    public String nameReceived;
    public String nameDelivered;
}
