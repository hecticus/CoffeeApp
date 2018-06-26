package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controllers.utils.ListPagerCollection;
import io.ebean.*;
import io.ebean.text.PathProperties;
import models.status.StatusLot;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sm21 on 10/05/18.
 */
@Entity
@Table(name="lots")
public class Lot extends AbstractEntity{

    @ManyToOne
    @Constraints.Required
    @JoinColumn(nullable = false)
    private Farm farm;

    @Constraints.Required
    @Constraints.MaxLength(50)
    @Column(nullable = false, length = 50)
    private String nameLot;

    @Constraints.Required
    @Constraints.MaxLength(200)
    @Column(nullable = false, length = 200)
    private String areaLot;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false)
    private Double heighLot;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false)
    private BigDecimal priceLot;

    @ManyToOne
    private StatusLot statusLot;

    @OneToMany(mappedBy = "lot", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;

    public static Finder<Long, Lot> finder = new Finder<>(Lot.class);

    public Lot() {
        invoiceDetails = new ArrayList<>();
    }

    //GETTER AND SETTER
    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public String getNameLot() {
        return nameLot;
    }

    public void setNameLot(String nameLot) {
        this.nameLot = nameLot;
    }

    public String getAreaLot() {
        return areaLot;
    }

    public void setAreaLot(String areaLot) {
        this.areaLot = areaLot;
    }

    public Double getHeighLot() {
        return heighLot;
    }

    public void setHeighLot(Double heighLot) {
        this.heighLot = heighLot;
    }

    public BigDecimal getPrice_lot() {
        return priceLot;
    }

    public void setPrice_lot(BigDecimal priceLot) {
        this.priceLot = priceLot;
    }

    public BigDecimal getPriceLot() {
        return priceLot;
    }

    public void setPriceLot(BigDecimal priceLot) {
        this.priceLot = priceLot;
    }

    public StatusLot getStatusLot() {
        return statusLot;
    }

    public void setStatusLot(StatusLot statusLot) {
        this.statusLot = statusLot;
    }

    //METODOS DEFINE
    public static Lot findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll( Integer pageIndex, Integer pageSize, PathProperties pathProperties, String sort,
                                        String name, Long idFarm, Long status, boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(idFarm != 0L)
            expressionList.eq("farm.id", idFarm);

        if(name != null )
            expressionList.startsWith("nameLot", name);

        if(status != 0L)
            expressionList.eq("statusLot.id", status);

        if (deleted)
            expressionList.setIncludeSoftDeletes();

        if(sort != null)
            expressionList.orderBy(sort( sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }

}
