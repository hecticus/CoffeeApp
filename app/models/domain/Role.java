package models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmantilla on 5/17/17.
 */
@Entity
@Table(name="role")
public class Role extends AbstractEntity {

        @Id
        private Long idRole;

    @Constraints.MaxLength(100)
    @Column(length = 100, nullable = false)
    protected String name;

    @Constraints.MaxLength(255)
    @Column(length = 255, nullable = false)
    protected String description;

    @ManyToOne
    protected StatusRole statusRole;

    @ManyToMany(mappedBy = "Roles", cascade = CascadeType.ALL)
    private List<SecurityTag> securityTags = new ArrayList<>();

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

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

    public StatusRole getStatusRole() {
        return statusRole;
    }

    public void setStatusRole(StatusRole statusRole) {
        this.statusRole = statusRole;
    }

    public List<SecurityTag> getSecurityTags() {
        return securityTags;
    }

    public void setSecurityTags(List<SecurityTag> securityTags) {
        this.securityTags = securityTags;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


        public Long getIdRole() {
            return idRole;
        }

        public void setIdRole(Long idRole) {
            this.idRole = idRole;
        }
}
