package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="purities")
public class Purity extends AbstractEntity{

    @Constraints.Required
    @Column(nullable = false, unique = true)
    private String namePurity;

    @Constraints.Required
    @Column(nullable = false)
    private Integer discountRatePurity;

    @OneToMany(mappedBy = "purity", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurities;

    private static Finder<Long, Purity> finder = new Finder<>(Purity.class);

    public Purity() {
        discountRatePurity = 0;
        invoiceDetailPurities = new ArrayList<>();
    }

    //Setter and Getter

    public String getNamePurity() {
        return namePurity;
    }

    public void setNamePurity(String namePurity) {
        this.namePurity = namePurity;
    }

    public Integer getDiscountRatePurity() {
        return discountRatePurity;
    }

    public void setDiscountRatePurity(Integer discountRatePurity) {
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

    public static ListPagerCollection findAll( Integer index, Integer size, PathProperties pathProperties, String sort,
                                               String name,  Integer status, boolean deleted){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(status != null)
            expressionList.eq("statusPurity",status);

        if(name != null)
            expressionList.icontains("NamePurity", name);

        if(sort != null) {
            if(sort.contains(" ")) {
                String []  aux = sort.split(" ", 2);
                expressionList.orderBy(sort( aux[0], aux[1]));
            }else {
                expressionList.orderBy(sort("idPurity", sort));
            }
        }

        if( deleted )
            expressionList.setIncludeSoftDeletes();

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.findList());

        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }

}
