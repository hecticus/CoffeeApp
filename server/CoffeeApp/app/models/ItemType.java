package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import org.jetbrains.annotations.NotNull;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="item_types")
public class ItemType extends AbstractEntity{

    @ManyToOne
    @JsonBackReference
    @Constraints.Required
    @JoinColumn(nullable = false)
    private ProviderType providerType;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private Unit unit;

    @Constraints.Required
    @Column(nullable = false, unique = true)
    private String nameItemType;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(precision = 12, scale = 4, nullable = false)
    private BigDecimal costItemType;

    @OneToMany(mappedBy = "itemType", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;

    private static Finder<Long, ItemType> finder = new Finder<>(ItemType.class);

    public ItemType() {
        invoiceDetails = new ArrayList<>();
    }

    //GETTER AND SETTER
    public String getNameItemType() {
        return nameItemType;
    }

    public void setNameItemType(String nameItemType) {
        this.nameItemType = nameItemType;
    }

    public BigDecimal getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(BigDecimal costItemType) {
        this.costItemType = costItemType;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    @JsonIgnore
    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    //METODOS DEFINE

    public static ItemType findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(Integer pageIndex, Integer pageSize, PathProperties pathProperties,
                                              String sort, String name, Long providerType, Long unit, boolean delete ){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.startsWith("nameItemType", name);

        if(providerType != 0L )
            expressionList.eq("providerType.id", providerType);

        if(unit != 0L )
            expressionList.eq("unit.id", providerType);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if( delete )
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
