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
 * Created by drocha on 12/05/17.
 */
@Entity
@Table(name="stores")
public class Store extends AbstractEntity{


    @Id
    @Column(name = "id_store")
    private Long idStore;

    @Constraints.Required
    @Constraints.Max(50)
    @Column(nullable = false, name = "name_store", unique = true, length = 50)
    private String NameStore;

    @Constraints.Required
    @Column(nullable = false, name = "status_store")
    private Integer statusStore;

    @OneToMany(mappedBy = "store", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails;

    private static Finder<Long, Store> finder = new Finder<>(Store.class);

    public Store() {
        statusStore = 1;
        invoiceDetails = new ArrayList<>();
    }

    public Long getIdStore() {
        return idStore;
    }

    public void setIdStore(Long idStore) {
        this.idStore = idStore;
    }

    public String getNameStore() {
        return NameStore;
    }

    public void setNameStore(String nameStore) {
        NameStore = nameStore;
    }

    public Integer getStatusStore() {
        return statusStore;
    }

    public void setStatusStore(Integer statusStore) {
        this.statusStore = statusStore;
    }

    @JsonIgnore
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public static int getExist(String name_store){
        if(finder.query().where().eq("name_store",name_store).eq("status_delete",0).findUnique()!=null) return 0;
        else{
            if(finder.query().where().eq("name_store",name_store).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }
    }

    public static Store findById(Long id){
        return finder.byId(id);
    }

    public static ListPagerCollection findAll(String name, Integer index, Integer size, String sort,PathProperties pathProperties,  Integer status){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(status != null)
            expressionList.eq("status_store",status);

        if(name != null)
            expressionList.icontains("name_store", name);

        if(sort != null) {
            expressionList.orderBy(sort);
        }
        if(index == null || size == null)
            return new ListPagerCollection(expressionList.eq("status_delete",0).findList());
        return new ListPagerCollection(
                expressionList.eq("status_delete",0).setFirstRow(index).setMaxRows(size).findList(),
                expressionList.eq("status_delete",0).setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }


    public static List<Store> getByStatusStore(String StatusStore, String order){
        String sql="select t0.id_store c0, t0.status_delete c1, t0.name_store c2, t0.status_store c3, " +
                " t0.created_at c5, t0.updated_at c6 " +
                " from stores t0 "+
                " where t0.status_delete=0 ";

        if(!StatusStore.equals("-1"))  sql+=" and t0.status_store= "+StatusStore;

        sql+=" order by t0.name_Store "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toStores(results);
    }

    public static List<Store> toStores(List<SqlRow>  sqlRows) {
        List<Store> stores = new ArrayList<>();

        Store Store;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            Store = new Store();

            Store.setIdStore(sqlRows.get(i).getLong("c0"));
            Store.setStatusDelete(sqlRows.get(i).getInteger("c1"));
            Store.setNameStore(sqlRows.get(i).getString("c2"));
            Store.setStatusStore(sqlRows.get(i).getInteger("c3"));

            stores.add(Store);
        }

        return stores;
    }


}
