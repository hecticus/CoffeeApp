package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="invoicesdetails_purities")
public class InvoiceDetailPurity extends AbstractEntity{

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private Purity purity;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Constraints.Required
    private InvoiceDetail invoiceDetail;

    @Constraints.Required
    @Column(precision = 12, scale = 2)
    private BigDecimal valueRateInvoiceDetailPurity;

    @Constraints.Required
    @Column(precision = 12, scale = 2)
    private BigDecimal discountRatePurity;

    //TODO Hacer formula que lo calcule
    @Constraints.Required
    @Column(precision = 12, scale = 2)
    private BigDecimal totalDiscountPurity;

    private static Finder<Long, InvoiceDetailPurity> finder = new Finder<>(InvoiceDetailPurity.class);

    public InvoiceDetailPurity() {
        totalDiscountPurity = BigDecimal.valueOf(0);
        discountRatePurity = BigDecimal.valueOf(0);
        valueRateInvoiceDetailPurity = BigDecimal.valueOf(0);
    }

    public Purity getPurity() {
        return purity;
    }

    public void setPurity(Purity purity) {
        this.purity = purity;
    }

    public BigDecimal getValueRateInvoiceDetailPurity() {
        return valueRateInvoiceDetailPurity;
    }

    public void setValueRateInvoiceDetailPurity(BigDecimal valueRateInvoiceDetailPurity) {
        this.valueRateInvoiceDetailPurity = valueRateInvoiceDetailPurity;
    }

    public BigDecimal getTotalDiscountPurity() {
        return totalDiscountPurity;
    }

    public void setTotalDiscountPurity(BigDecimal totalDiscountPurity) {
        this.totalDiscountPurity = totalDiscountPurity;
    }

    public BigDecimal getDiscountRatePurity() {
        return discountRatePurity;
    }

    public void setDiscountRatePurity(BigDecimal discountRatePurity) {
        this.discountRatePurity = discountRatePurity;
    }

    @JsonIgnore
    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public static  InvoiceDetailPurity findById(Long id){
        return finder.byId(id);
    }

    public static List<Integer> getByIdInvopiceDetails(Long IdInvopiceDetail) {
        return finder.query().where()
                .eq("invoiceDetail.id",IdInvopiceDetail)
                .findIds();
    }

    public static InvoiceDetailPurity getByIdInvopiceDetailsByIdPurity(Long IdInvopiceDetail, Long IdPurity) {
        return finder.query().where()
                .eq("invoiceDetail.id",IdInvopiceDetail)
                .eq("purity.id",IdPurity)
                .findUnique();
    }
    public static ListPagerCollection findAll(Integer pageIndex, Integer pageSize, PathProperties pathProperties,
                                              String sort, Long purity, Long invoiceDetail, boolean delete){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(purity != 0L)
            expressionList.eq("purity.id", purity );

        if(invoiceDetail != 0L)
            expressionList.eq("invoiceDetail.id", invoiceDetail );

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(delete)
            expressionList.setIncludeSoftDeletes();

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }


}
