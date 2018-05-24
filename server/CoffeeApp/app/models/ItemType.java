package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="item_types")
public class ItemType extends AbstractEntity
{

    @Id
    @Column(name = "id_itemType")
    private Long idItemType;

    @Constraints.Required
    @Column(nullable = false, name = "name_itemType")
    private String nameItemType;

    @Constraints.Required
    @Column(nullable = false, columnDefinition = "Decimal(10,2)",name = "cost_itemType")
    private Float costItemType;

    @Constraints.Required
    @Column(nullable = false, name = "status_itemType")
    private Integer statusItemType=1;

    @ManyToOne
    @JoinColumn(name = "id_providerType", nullable = false)
    @JsonBackReference
    private ProviderType providerType;

    @ManyToOne
    @JoinColumn(name = "id_unit", nullable = false)
    private Unit unit;

    @OneToMany(mappedBy = "itemType", cascade= CascadeType.ALL)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();



    private static Finder<Long, ItemType> finder = new Finder<>(ItemType.class);

    //GETTER AND SETTER
    public Long getIdItemType() {
        return idItemType;
    }

    public void setIdItemType(Long idItemType) {
        this.idItemType = idItemType;
    }

    public String getNameItemType() {
        return nameItemType;
    }

    public void setNameItemType(String nameItemType) {
        this.nameItemType = nameItemType;
    }

    public Float getCostItemType() {
        return costItemType;
    }

    public void setCostItemType(Float costItemType) {
        this.costItemType = costItemType;
    }

    public Integer getStatusItemType() {
        return statusItemType;
    }

    public void setStatusItemType(Integer statusItemType) {
        this.statusItemType = statusItemType;
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
    private static Unit unitDao = new Unit();

    public static ItemType findById(Long id){
        return finder.byId(id);
    }


    public static int getExist(String name_itemtype)
    {
        if(finder.query().where().eq("name_itemtype",name_itemtype).eq("status_delete",0).findUnique()!=null) return 0;
        else
        {
            if(finder.query().where().eq("name_itemtype",name_itemtype).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;

        }

    }

    public List<ItemType> getOpenByUnitId(Long idUnit)
    {
        return finder.query().where().eq("id_unit",idUnit).eq("status_delete",0).findList();
    }

    public List<ItemType> getOpenByProviderTypeId(Long idProviderType)
    {
        return finder.query().where().eq("id_providertype",idProviderType).eq("status_delete",0).findList();
    }

    public List<ItemType> getByProviderTypeId(Long idProviderType, Integer status)
    {
        String sql="SELECT item.id_itemtype  c0, item.status_delete c1, item.name_itemtype   c2, " +
                " item.cost_itemtype c3, item.status_itemtype c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providertype c7, item.id_unit c8  " +
                " FROM item_types item "+
                " inner join provider_type protype on protype.id_providertype=item.id_providertype " +
                " where item.status_delete=0  and item.id_providertype= :idprovidertype ";

        if(status!=-1) sql+= " and protype.status_delete=:status ";

        SqlQuery query = Ebean.createSqlQuery(sql)
                .setParameter("idprovidertype", idProviderType )
                .setParameter("status",status);

        List<SqlRow>   results = query.findList();

        return toItemTypes(results);
    }

    public List<ItemType> getByNameItemType(String NameItemType, String order)
    {
        String sql="SELECT item.id_itemtype  c0, item.status_delete c1, item.name_itemtype   c2, " +
                " item.cost_itemtype c3, item.status_itemtype c4, item.created_at c5, " +
                " item.updated_at c6, item.id_providertype c7, item.id_unit c8  " +
                " FROM item_types item "+
                " where item.status_delete=0  and item.name_itemtype like '%"+NameItemType+"%' "+
                " order by item.id_itemtype   "+order;

        SqlQuery query = Ebean.createSqlQuery(sql);

        List<SqlRow>   results = query.findList();

        return toItemTypes(results);
    }

    public List<ItemType> toItemTypes(List<SqlRow>  sqlRows)
    {
        List<ItemType> itemTypes = new ArrayList<>();

        ItemType itemType;
        for(int i=0; i < sqlRows.size(); ++i)
        {
            itemType = new ItemType();

            itemType.setIdItemType(sqlRows.get(i).getLong("c0"));
            itemType.setNameItemType(sqlRows.get(i).getString("c2"));
            itemType.setCostItemType(sqlRows.get(i).getFloat("c3"));
            itemType.setUnit(unitDao.findById(sqlRows.get(i).getLong("c8")));
            itemType.setStatusItemType(sqlRows.get(i).getInteger("c4"));
            itemTypes.add(itemType);
        }

        return itemTypes;
    }

/*    public ListPagerCollection findAllSearch(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties) {
        ExpressionList expressionList = finder.query().where().eq("status_delete",0);

        if(pathProperties != null)
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.icontains("name_itemtype", name);

        if(sort != null)
            expressionList.orderBy(AbstractDaoImpl.Sort(sort));

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.findList());
        return new ListPagerCollection(expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findList(), expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findCount(), pageIndex, pageSize);
    }*/




    public static ListPagerCollection findAll(String name, Integer pageIndex, Integer pageSize, String sort, PathProperties pathProperties, Long id_ProviderType, Integer status){
        ExpressionList expressionList = finder.query().where();

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(name != null)
            expressionList.contains("name_itemType", name);

        if(status !=null)
            expressionList.eq("status_itemType", status);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(id_ProviderType == null || id_ProviderType != 0L)
            expressionList.eq("id_providerType", id_ProviderType);

        if(pageIndex == null || pageSize == null)
            return new ListPagerCollection(expressionList.eq("status_delete",0).findList());
        return new ListPagerCollection(
                expressionList.eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList(),
                expressionList.eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findCount(),
                pageIndex,
                pageSize);
    }



}
