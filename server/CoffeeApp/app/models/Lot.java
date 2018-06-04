package models;

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

    @Constraints.Required
    @Constraints.MaxLength(255)
    @Column(nullable = false, name = "name_lot", length = 255)
    private String nameLot;

    @Constraints.Required
    @Column(nullable = false, name = "area_lot")
    private String areaLot;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false, name = "heigh_lot")
    private Double heighLot;

    @Constraints.Required
    @Column(nullable = false, name = "status_lot")
    private Integer statusLot;


    @ManyToOne
    @Constraints.Required
    @JoinColumn(name = "id_farm", nullable = false)
    private Farm farm;

    @Constraints.Required
    @Constraints.Min(0)
    @Column(nullable = false, name = "price_lot")
    private BigDecimal priceLot;

    @OneToMany(mappedBy = "lot", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    private static Finder<Long, Lot> finder = new Finder<>(Lot.class);

    public Lot() {
        statusLot = 1;
    }

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

    @JsonProperty("priceLot")
    public BigDecimal getPrice_lot() {
        return priceLot;
    }

    @JsonProperty("price_lot")
    public void setPrice_lot(BigDecimal priceLot) {
        this.priceLot = priceLot;
    }

    private static Farm farmDao = new Farm();

    public static Lot findById(Long id){
        return finder.byId(id);
    }

    public static boolean existId(Long id) {
        if(InvoiceDetail.findById(id) != null ) return true;
        return false;
    }

    public static boolean existName(String name_itemtype){
        if(finder.query().where().eq("name_itemtype",name_itemtype ).findUnique() != null ) return true;
        return false;
    }

    public ListPagerCollection findAll(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties, Integer all, Long idFarm, Integer statusLot){
        ExpressionList expressionList = finder.query().where();


        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(idFarm != 0L)
            expressionList.eq("id_farm", idFarm);

        if(name != null )
            expressionList.eq("name_lot", name);

        if(statusLot != null)
            expressionList.eq("status_lot", statusLot);

        if (all != null)
            expressionList.eq("status_delete", all);

        if(sort != null)
            expressionList.orderBy(sort(sort, nameLot, idLot));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }

    public static List<Integer> getExist(String name_lot, int id_farm){
        List<Integer> aux = new ArrayList<Integer>();
        Lot lot = finder.query().where().eq("name_lot",name_lot).eq("id_farm",id_farm).setMaxRows(1).findUnique(); //findUnique();
        if(lot==null) aux.add(0,-1);
        else{
            aux.add(0,lot.getStatusDelete());
            aux.add(1,Integer.parseInt(lot.getIdLot().toString()));
        }
        return aux;
    }

    public static List<Lot> getByNameLot(String NameLot, String order)
    {
        String sql="select t0.id_lot c0, t0.status_delete c1, t0.name_lot c2," +
                " t0.area_lot c3, t0.id_farm c4, t0.heigh_lot c5, t0.created_at c6, " +
                "t0.updated_at c7 from lots t0 " +
                "where t0.status_delete= 0 and t0.name_lot like '%"+NameLot+"%' "+
                " order by t0.name_lot "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toLots(results);
    }

    public static List<Lot> getByStatusLot(String StatusLot, String order)
    {
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
            lot.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            lot.setNameLot(sqlRows.get(i).getString("c2"));
            lot.setAreaLot(sqlRows.get(i).getString("c3"));
            lot.setFarm(Farm.findById(sqlRows.get(i).getLong("c4")));
            lot.setHeighLot(sqlRows.get(i).getDouble("c5"));
            lots.add(lot);
        }

        return lots;
    }

    public  static ListPagerCollection findAll(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties, Integer all, Integer idFarm) {
        String strOrder = "ASC";
        ExpressionList expressionList = finder.query().where();

//        if(idFarm.equals(-1)) {
//            if (all.equals(1)) expressionList = finder.query().where().eq("status_delete", 0);
//            else expressionList = finder.query().where().eq("status_delete", 0).eq("status_lot", 1);
//        }
//        else
//        {
//            if (all.equals(1)) expressionList = finder.query().where().eq("status_delete", 0).eq("id_farm",idFarm);
//            else expressionList = finder.query().where().eq("status_delete", 0).eq("status_lot", 1).eq("id_farm",idFarm);
//        }

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(idFarm != 0L)
            expressionList.eq("status_delete", 0);

        if(name != null)
            expressionList.icontains("name_lot", name);

//        if(sort != null)
//            expressionList.orderBy(sort(sort, getNameLot(), getIdLot()));

        expressionList.eq("status_delete", all);

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }

    public static ListPagerCollection  getByIdFarm(Long idFarm, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties)
    {
        ExpressionList expressionList =  finder.query().where().eq("status_delete",0).eq("id_farm",idFarm).eq("status_lot",1);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(AbstractEntity.sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }
}
