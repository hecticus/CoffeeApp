package models;

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
    @Column(nullable = false, name = "name_purity")
    private String NamePurity;

    @Constraints.Required
    @Column(nullable = false, name = "status_purity")
    private Integer statusPurity=1;

    @Constraints.Required
    @Column(nullable = false, name = "discountRate_purity")
    private Integer DiscountRatePurity=0;

    @OneToMany(mappedBy = "purity", cascade= CascadeType.ALL)
    private List<InvoiceDetailPurity> invoiceDetailPurities = new ArrayList<>();

    private static Finder<Long, Farm> finder = new Finder<>(Farm.class);

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


    public int getExist(String name_purity){
        if(finder.query().where().eq("name_purity",name_purity).eq("status_delete",0).findUnique()!=null) return 0;
        else{
            if(finder.query().where().eq("name_purity",name_purity).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;
        }
    }

    public List<Purity> getByNamePurity(String NamePurity, String order){
        String sql="select t0.id_purity c0, t0.status_delete c1, t0.name_purity c2, t0.status_purity c3," +
                " t0.discountrate_purity c4, t0.created_at c5, t0.updated_at c6 " +
                "from purities t0" +
                " where name_purity like '%"+NamePurity+"%'  and status_delete = 0"+
                " order by t0.name_purity "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toPuritys(results);
    }

    public List<Purity> getByStatusPurity(String StatusPurity, String order){
        String sql="select t0.id_purity c0, t0.status_delete c1, t0.name_purity c2, t0.status_purity c3, " +
                " t0.discountrate_purity c4, t0.created_at c5, t0.updated_at c6 " +
                " from purities t0 "+
                " where t0.status_delete=0 ";

        if(!StatusPurity.equals("-1"))  sql+=" and t0.status_purity= "+StatusPurity;

        sql+=" order by t0.name_purity "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toPuritys(results);
    }

    public List<Purity> toPuritys(List<SqlRow>  sqlRows)
    {
        List<Purity> purities = new ArrayList<>();

        Purity purity;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            purity = new Purity();

            purity.setIdPurity(sqlRows.get(i).getLong("c0"));
            purity.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            purity.setNamePurity(sqlRows.get(i).getString("c2"));
            purity.setStatusPurity(sqlRows.get(i).getInteger("c3"));
            purity.setDiscountRatePurity(sqlRows.get(i).getInteger("c4"));
            purities.add(purity);
        }

        return purities;
    }

    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = finder.query().where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name_purity", name);

        if(sort != null)
            expressionList.orderBy(AbstractEntity.sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }



}
