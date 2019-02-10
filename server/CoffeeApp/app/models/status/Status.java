package models.status;

import io.ebean.Finder;
import models.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", length = 50)
public abstract class Status extends AbstractEntity {

    @Constraints.Required
    @Constraints.MaxLength(30)
    @Column(nullable = false, length = 30)
    protected String name;

    @Transient
    protected String dtype; // discriminator

    @Column(columnDefinition = "text")
    protected String description;

    private static Finder<Long, Status> finder = new Finder<>(Status.class);

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

}