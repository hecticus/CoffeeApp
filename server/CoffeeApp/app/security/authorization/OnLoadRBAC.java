
package security.authorization;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import play.Configuration;
import scala.collection.immutable.List;
import scala.math.Ordering;
import security.models.Group;
import security.models.Permission;
import security.models.Role;

import java.util.*;


/**
 * Created by nisa on 26/10/17.
 *
 * reference: https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection
 *
 */

@Singleton
public class OnLoadRBAC {

    @Inject
    public OnLoadRBAC(Configuration config) {
        System.out.println("*** Loading control access tables...");
        Group.deleteAll();
        Role.deleteAll();
        Permission.deleteAll();
        //buildRolesManyPermission(config.getConfig("play.rbac.roles"));
        buildGroupsManyRoles(config.getConfig("play.rbac.groups"));
        System.out.println("*** Complete control access tables...");
    }

    private void buildGroupsManyRoles(Configuration config){
        for(String groupKey : config.subKeys()) {
            //System.out.println(groupKey);
            Group group = Group.findById(groupKey);
            if(group == null){
                group = new Group();
                group.setId(groupKey);
               // group.setRoles(buildRoles(config.getList(groupKey)));
                group.insert();
            }else{
                //group.setRoles(buildRoles(config.getList(groupKey)));
                group.update();
            }
        }
    }

/*    private List<Role> buildRoles(List<Object> roleKeys){
        List<Role> roles = new ArrayList<>();
        for(Object roleKey : roleKeys) {
            Role role = Role.findById(roleKey.toString());
            if(role == null) {
                role = new Role();
                role.setId(roleKey.toString());
                role.insert();
            }
            roles.add(role);
        }
        return roles;
    }

    private void buildRolesManyPermission(Configuration config){
        for(Ordering.String roleKey : config.subKeys()) {
            //System.out.println(roleKey);
            Role role = Role.findById(roleKey);
            if(role == null){
                role = new Role();
                role.setId(roleKey);
                role.setPermissions(buildPermissions(config.getList(roleKey)));
                role.insert();
            }else{
                role.setPermissions(buildPermissions(config.getList(roleKey)));
                role.update();
            }
        }
    }

    private List<Permission> buildPermissions(List<Object> permissionKeys){
        List<Permission> permissions = new ArrayList<>();
        for(Object permissionKey : permissionKeys) {
            //System.out.println(permissionKey);
            Permission permission = Permission.findByRoute(permissionKey.toString());
            if(permission == null){
                permission = new Permission();
                permission.setRoute(permissionKey.toString());
                permission.insert();
            }
            permissions.add(permission);
        }
        return permissions;
    }*/
}
