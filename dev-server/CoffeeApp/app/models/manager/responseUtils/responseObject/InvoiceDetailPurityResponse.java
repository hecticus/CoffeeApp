package models.manager.responseUtils.responseObject;

/**
 * Created by drocha on 26/04/17.
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
