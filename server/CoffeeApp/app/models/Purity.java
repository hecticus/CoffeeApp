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

    @Id
    @Column(name = "id_purity")
    private Long idPurity;

    @Constraints.Required
    @Column(nullable = false, name = "name_purity", unique = true)
    private String NamePurity;

    @Constraints.Required
    @Column(nullable = false, name = "discountRate_purity")
    private Integer DiscountRatePurity;

    @Constraints.Required
    @Range(min = 0, max = 1)
    @Column(nullable = false, name = "status_purity")
    private Integer statusPurity;

    @OneToMany(mappedBy = "purity", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurities;

    private static Finder<Long, Purity> finder = new Finder<>(Purity.class);

    public Purity() {
        statusPurity = 1;
        DiscountRatePurity = 0;
        invoiceDetailPurities = new ArrayList<>();
    }

    //Setter and Getter
    public Long getIdPurity() {
        return idPurity;
    }

    public void setIdPurity(Long idPurity) {
        this.idPurity = idPurity;
    }

    public String getNamePurity() {
        return NamePurity;
    }

    public void setNamePurity(String namePurity) {
        NamePurity = namePurity;
    }

    public Integer getStatusPurity() {
        return statusPurity;
    }

    public void setStatusPurity(Integer statusPurity) {
        this.statusPurity = statusPurity;
    }

    public Integer getDiscountRatePurity() {
        return DiscountRatePurity;
    }

    public void setDiscountRatePurity(Integer discountRatePurity) {
        DiscountRatePurity = discountRatePurity;
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
