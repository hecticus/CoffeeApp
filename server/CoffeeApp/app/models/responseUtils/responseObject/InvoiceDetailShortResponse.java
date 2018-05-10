package models.responseUtils.responseObject;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.responseUtils.CustomDateTimeSerializer;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drocha on 05/05/17.
 */
public class InvoiceDetailShortResponse  extends AbstractEntityResponse
{

    public Long idInvoiceDetail;
    public static class Invoice
    {

        public static class Provider
        {
            public Long idProvider;
        }
        public Long idInvoice;
        public Provider provider;

    }

    public Invoice invoice;

    public static class ItemType
    {

        public Long idItemType;
        public static class Unit
        {
            public Long idUnit;
        }

        public Unit unit;
    }
    public ItemType itemType;

    public static class InvoiceDetailPurity
    {

        public static class Purity
        {
            public Long idPurity;
        }
        public Long idInvoiceDetailPurity;
        public Purity purity;

    }

    public List<InvoiceDetailPurity> invoiceDetailPurity = new ArrayList<>();


    public static class Lot
    {
        public Long idLot;
    }
    public Lot lot;


    public Float cost;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public DateTime startDate;
    public Integer amount;
    public boolean freight;
    public String note;
    public String nameReceived;
    public String nameDelivered;
}
