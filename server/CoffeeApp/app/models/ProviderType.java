package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import controllers.parsers.queryStringBindable.Pager;
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
@Table(name="provider_type")
public class ProviderType  extends AbstractEntity {
    @Id
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(name = "id_ProviderType", length = 100, nullable = false)
    private Long idProviderType;

    @Constraints.Required
    @Column(nullable = false, name = "name_ProviderType", length = 100)
    private String nameProviderType;


    @Constraints.Required
    @Column(nullable = false, name = "status_ProviderType")
//    @SoftDelete
//    private boolean statusProviderType;
    private Integer statusProviderType;


    @OneToMany(mappedBy = "providerType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Provider> providers = new ArrayList<>();


    @OneToMany(mappedBy = "providerType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemType> itemTypes = new ArrayList<>();

    private static Finder<Long, ProviderType> finder = new Finder<>(ProviderType.class);


    //Setter and Getter

    public Long getIdProviderType() {
        return idProviderType;
    }

    public void setIdProviderType(Long idProviderType) {
        this.idProviderType = idProviderType;
    }

    public String getNameProviderType() {
        return nameProviderType;
    }

    public void setNameProviderType(String nameProviderType) {
        this.nameProviderType = nameProviderType;
    }

    public Integer getStatusProviderType() {
        return statusProviderType;
    }

    public void setStatusProviderType(Integer statusProviderType) {
        this.statusProviderType = statusProviderType;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }


    //Metodos Definidos
    public static ProviderType findById(Long id){
        return finder.byId(id);
    }

    public static ProviderType findByName(String name) {
        return finder
                .query()
                .where()
                .eq("name_ProviderType", name)
                .findUnique();
    }


    public static ListPagerCollection findAll(String name, Integer index, Integer size, String sort, PathProperties pathProperties){
        ExpressionList expressionList = finder.query().where();

        if(name!=null)
            expressionList.contains( "name_ProviderType", name);

        if(pathProperties != null && !pathProperties.getPathProps().isEmpty())
            expressionList.apply(pathProperties);

        if(sort != null)
            expressionList.orderBy(sort(sort));

        if(index == null || size == null)
            return new ListPagerCollection(expressionList.eq("status_ProviderType",0).findList());
        return new ListPagerCollection(
                expressionList.setFirstRow(index).setMaxRows(size).findList(),
                expressionList.setFirstRow(index).setMaxRows(size).findCount(),
                index,
                size);
    }




    public List<ProviderType> getProviderTypesByName(String name_providertype, String order)
    {
        String sql = " select t0.id_providertype, t0.status_delete, " +
                " t0.name_providertype, t0.created_at, t0.updated_at  " +
                " from provider_types t0 " +
                " where status_delete = 0 "+
                " ";

        if(!name_providertype.equals(""))     sql += "  and name_providertype like '%"+name_providertype+"%' ";

        sql += "  order by name_providertype "+order+"";


        List<SqlRow>  sqlRows = Ebean.createSqlQuery(sql)
                .findList();



        return toProviderTypes(sqlRows);


    }

    public List<ProviderType> toProviderTypes(List<SqlRow>  sqlRows)
    {
        List<ProviderType> providerTypes = new ArrayList<>();
        ProviderType providerType;

        for(int i=0; i < sqlRows.size(); ++i)
        {
            providerType = new ProviderType();

            providerType.setIdProviderType(sqlRows.get(i).getLong("id_providertype"));
            providerType.setNameProviderType(sqlRows.get(i).getString("name_providertype"));


            providerTypes.add(providerType);
        }

        return providerTypes;
    }

    public int getExist(String name_providertype)
    {
        if(finder.query().where().eq("name_providertype",name_providertype).eq("status_delete",0).findUnique()!=null) return 0;
        else{
            if(finder.query().where().eq("name_providertype",name_providertype).eq("status_delete",1).findUnique()!=null)  return 1;
            else return 2;
        }

    }
    //
//    public List<ProviderType> findAll(Integer pageIndex, Integer pageSize){
//        List<ProviderType> entities;
//        if(pageIndex != -1 && pageSize != -1)
//            //    entities = find.setFirstRow(pageIndex).setMaxRows(pageSize).findList();
//            entities = finder.query().where().eq("status_delete",0).setFirstRow(pageIndex).setMaxRows(pageSize).findList();
//        else
//            // entities =  find.all();
//            entities = finder.query().where().eq("status_delete",0).findList();
//        return entities;
//
//    }
    public static PagedList findAll(Integer pageIndex, Integer pageSize){
        ExpressionList expressionList = finder.query().where();

        if(pageIndex != -1 && pageSize != -1)
            return expressionList.eq("status_delete",0).findPagedList();
        else
            return expressionList.setFirstRow(pageIndex).setMaxRows(pageSize).findPagedList();
    }

}
