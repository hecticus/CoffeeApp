package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
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
    @Column(nullable = false)
    private Integer valueRateInvoiceDetailPurity;

    @Constraints.Required
    @Column(nullable = false)
    private Integer discountRatePurity;

    //Todo Hacer formula que lo calcule
    @Constraints.Required
    @Column(nullable = false)
    private Integer totalDiscountPurity;

    private static Finder<Long, InvoiceDetailPurity> finder = new Finder<>(InvoiceDetailPurity.class);

    public InvoiceDetailPurity() {
        totalDiscountPurity = 0;
        discountRatePurity = 0;
        valueRateInvoiceDetailPurity = 0;
    }

    public Purity getPurity() {
        return purity;
    }

    public void setPurity(Purity purity) {
        this.purity = purity;
    }

    public Integer getValueRateInvoiceDetailPurity() {
        return valueRateInvoiceDetailPurity;
    }

    public void setValueRateInvoiceDetailPurity(Integer valueRateInvoiceDetailPurity) {
        this.valueRateInvoiceDetailPurity = valueRateInvoiceDetailPurity;
    }

    public Integer getTotalDiscountPurity() {
        return totalDiscountPurity;
    }

    public void setTotalDiscountPurity(Integer totalDiscountPurity) {
        this.totalDiscountPurity = totalDiscountPurity;
    }

    public Integer getDiscountRatePurity() {
        return discountRatePurity;
    }

    public void setDiscountRatePurity(Integer discountRatePurity) {
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
