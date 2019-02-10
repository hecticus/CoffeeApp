package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.ebean.*;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="purities")
public class Purity extends AbstractEntity{

    @Constraints.Required
    @Constraints.MaxLength(20)
    @Column(nullable = false, length = 20, unique = true)
    private String namePurity;

    @Constraints.Required
    @Column(precision = 12, scale = 4, nullable = false)
    private BigDecimal discountRatePurity;

    @OneToMany(mappedBy = "purity", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurities;

    private static Finder<Long, Purity> finder = new Finder<>(Purity.class);

    public Purity() {
        discountRatePurity = BigDecimal.ZERO;
        invoiceDetailPurities = new ArrayList<>();
    }

    //Setter and Getter
    public String getNamePurity() {
        return namePurity;
    }

    public void setNamePurity(String namePurity) {
        this.namePurity = namePurity;
    }

    public BigDecimal getDiscountRatePurity() {
        return discountRatePurity;
    }

    public void setDiscountRatePurity(BigDecimal discountRatePurity) {
        discountRatePurity = discountRatePurity;
    }

    @JsonIgnore
    public List<InvoiceDetailPurity> getInvoiceDetailPurities() {
        return invoiceDetailPurities;
    }

    public void setInvoiceDetailPurities(List<InvoiceDetailPurity> invoiceDetailPurities) {
        this.invoiceDetailPurities = invoiceDetailPurities;
    }

    //Metodos
    public static Purity findById(Long id){
        return finder.byId(id);
    }

    public static PagedList findAll( Integer index, Integer size, PathProperties pathProperties, String sort,
                                               String name, boolean delete){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("NamePurity", name);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if( delete )
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null){
            return expressionList
                    .setFirstRow(0)
                    .setMaxRows(expressionList.findCount()).findPagedList();
        }

        return expressionList.setFirstRow(index).setMaxRows(size).findPagedList();
    }


}
