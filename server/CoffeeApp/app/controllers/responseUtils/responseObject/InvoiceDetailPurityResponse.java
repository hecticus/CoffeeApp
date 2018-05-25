package controllers.responseUtils.responseObject;


/**
 * Created by sm21 on 10/05/18.
 */
public class InvoiceDetailPurityResponse  extends AbstractEntityResponse
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
