package models;

import com.avaje.ebean.validation.Range;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.utils.ListPagerCollection;
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
@Table(name="lots")
public class Lot extends AbstractEntity{

    @Id
    @Column(name = "id_lot")
    private Long idLot;

    @ManyToOne
    @Constraints.Required
    @JoinColumn(name = "id_farm", nullable = false)
    private Farm farm;

    @Constraints.Required
    @Constraints.MaxLength(255)
    @Column(nullable = false, name = "name_lot", length = 255)
    private String nameLot;

    @Constraints.Required
    @Constraints.MaxLength(200)
    @Column(nullable = false, name = "area_lot", length = 200)
    private String areaLot;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false, name = "heigh_lot")
    private Double heighLot;

    @Range(min = 0, max = 1)
    @Column(columnDefinition = "integer default 1" , name = "status_lot")
    private Integer statusLot;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false, name = "price_lot")
    private BigDecimal priceLot;

    @OneToMany(mappedBy = "lot", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;

    public static Finder<Long, Lot> finder = new Finder<>(Lot.class);

    public Lot() {
        statusLot = 1;
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

    public Long getIdLot() {
        return idLot;
    }

    public void setIdLot(Long idLot) {
        this.idLot = idLot;
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

    public Integer getStatusLot() {
        return statusLot;
    }

    public void setStatusLot(Integer statusLot) {
        this.statusLot = statusLot;
    }

    public BigDecimal getPrice_lot() {
        return priceLot;
    }

    public void setPrice_lot(BigDecimal priceLot) {
        this.priceLot = priceLot;
    }


    //METODOS DEFINE

    public static Lot findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll( Integer pageIndex, Integer pageSize, PathProperties pathProperties, String sort,
                                        String name, Long idFarm, Integer status, boolean deleted){

        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(idFarm != 0L)
            expressionList.eq("farm.idFarm", idFarm);

        if(name != null )
            expressionList.eq("nameLot", name);

        if(status != null)
            expressionList.eq("statusLot", status);

        if (deleted)
            expressionList.setIncludeSoftDeletes();

        if(sort != null) {
            if(sort.contains(" ")) {
                String []  aux = sort.split(" ", 2);
                expressionList.orderBy(sort( aux[0], aux[1]));
            }else {
                expressionList.orderBy(sort("idLot", sort));
            }
        }

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }

    public static List<Lot> getByNameLot(String NameLot, String order) {
        String sql="select t0.id_lot c0, t0.status_delete c1, t0.name_lot c2," +
                " t0.area_lot c3, t0.id_farm c4, t0.heigh_lot c5, t0.created_at c6, " +
                "t0.updated_at c7 from lots t0 " +
                "where t0.status_delete= 0 and t0.name_lot like '%"+NameLot+"%' "+
                " order by t0.name_lot "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toLots(results);
    }

    public static List<Lot> getByStatusLot(String StatusLot, String order) {
        String sql="select t0.id_lot c0, t0.status_delete c1, t0.name_lot c2," +
                " t0.area_lot c3, t0.id_farm c4, t0.heigh_lot c5, t0.created_at c6, " +
                "t0.updated_at c7 from lots t0 ";

        if(!StatusLot.equals("-1"))  sql+="where t0.status_delete= "+StatusLot;

        sql+=" order by t0.name_lot "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toLots(results);
    }

    public static List<Lot> toLots(List<SqlRow>  sqlRows)
    {
        List<Lot> lots = new ArrayList<>();

        Lot lot;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            lot = new Lot();

            lot.setIdLot(sqlRows.get(i).getLong("c0"));
//            lot.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            lot.setNameLot(sqlRows.get(i).getString("c2"));
            lot.setAreaLot(sqlRows.get(i).getString("c3"));
            lot.setFarm(Farm.findById(sqlRows.get(i).getLong("c4")));
            lot.setHeighLot(sqlRows.get(i).getDouble("c5"));
            lots.add(lot);
        }

        return lots;
    }




}
