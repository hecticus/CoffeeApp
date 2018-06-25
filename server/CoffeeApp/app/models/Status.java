package models;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.Formula;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", length = 50)
public abstract class Status extends Model{

    @Id
    protected Long id;

    @Constraints.Required
    @Constraints.MaxLength(20)
    @Column(nullable = false, length = 20, unique = true)
    protected String name;

    @Transient
    protected String dtype; // discriminator

    @Formula(select = "(SELECT id FROM status s WHERE s.id = ${ta}.id)")
    protected String nameDis;

    @Column(columnDefinition = "text")
    protected String description;

    private static Finder<String, Status> finder = new Finder<>(Status.class);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameDis() {
        return nameDis;
    }

    public void setNameDis(String nameDis) {
        this.nameDis = nameDis;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected static String sort(String sort){
        if(sort == null)
            return "";
        if(sort.startsWith("-"))
            return sort.substring(1) + " desc";
        return sort + " asc";
    }
}