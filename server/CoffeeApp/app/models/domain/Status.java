package models.domain;

import models.AbstractEntity;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by gmantilla on 5/16/17.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="status_type")
@Table(name="status")
public class Status extends AbstractEntity {

    @Id
    private Long idStatus;

    @Constraints.MaxLength(100)
    @Column(length = 100)
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return idStatus;
    }

    public void setId(Long id) {
        this.idStatus = id;
    }
}
