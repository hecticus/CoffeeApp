package models.domain;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by palenge.
 */
@Entity
@Table(name="tag")
public class SecurityTag extends AbstractEntity {

    @Id
    private Long idSecurityTag;

    @Constraints.MaxLength(50)
    @Column(length = 50, nullable = false)
    protected String name;

    @Constraints.MaxLength(255)
    @Column(length = 255, nullable = false)
    protected String description;

    @ManyToMany(mappedBy = "securityTag")
    private List<SecurityRoute> securityRoutes = new ArrayList<>();

    @ManyToMany
    private List<Role> Roles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdSecurityTag() {
        return idSecurityTag;
    }

    public void setIdSecurityTag(Long idSecurityTag) {
        this.idSecurityTag = idSecurityTag;
    }
}
