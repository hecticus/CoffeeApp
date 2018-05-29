package security.authorization;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import security.models.AuthUser;
import security.models.Group;
import security.models.Permission;
import security.models.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 26/10/17.
 *
 * reference: https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection
 *
 */
@Singleton
public class OnLoadRBAC {

    @Inject
    public OnLoadRBAC(Config config) {
        System.out.println("*** Loading control access tables...");
        Group.deleteAll();
        Role.deleteAll();
        AuthUser.deleteAll();
        Permission.deleteAll();
        buildRolesManyPermission(config);
        buildGroupsManyRoles(config);
//        buildGroupsManyUser(config);
        System.out.println("*** Complete control access tables...");
    }

    private void buildGroupsManyRoles(Config config){
        config.getObject("play.rbac.groups").forEach((k,v)->{
            //System.out.println(k);
            Group group = Group.findByName(k);
            if(group == null){
                group = new Group();
                group.setName(k);
                group.setRoles(buildRoles(config.getStringList("play.rbac.groups." + k)));
                group.insert();
            }else{
                group.setRoles(buildRoles(config.getStringList("play.rbac.groups." + k)));
                group.update();
            }
        });
    }

    private List<Role> buildRoles(List<String> roleKeys){
        List<Role> roles = new ArrayList<>();
        for(Object roleKey : roleKeys) {
            Role role = Role.findByName(roleKey.toString());
            if(role == null) {
                role = new Role();
                role.setName(roleKey.toString());
                role.insert();
            }
            roles.add(role);
        }
        return roles;
    }

    private void buildRolesManyPermission(Config config){
        config.getObject("play.rbac.roles").forEach((k,v)->{
            //System.out.println("key : " + k + " value : " + v);
            System.out.println(k);
            Role role = Role.findByName(k);
            if(role == null){
                role = new Role();
                role.setName(k);
                role.setPermissions(buildPermissions(config.getStringList("play.rbac.roles." + k)));
                role.insert();
            }else{
                role.setPermissions(buildPermissions(config.getStringList("play.rbac.roles." + k)));
                role.update();
            }
        });
    }

    private List<Permission> buildPermissions(List<String> permissionKeys){
        List<Permission> permissions = new ArrayList<>();
        for(Object permissionKey : permissionKeys) {
            //System.out.println(permissionKey);
            Permission permission = Permission.findByName(permissionKey.toString());
            if(permission == null){
                permission = new Permission();
                permission.setName(permissionKey.toString());
                permission.insert();
            }
            permissions.add(permission);
        }
        return permissions;
    }

    private void buildGroupsManyUser(Config config){
        config.getObject("play.rbac.groupUser").forEach((k,v)->{
            Group group = Group.findByName(k);
            if(group == null){
                group = new Group();
                group.setName(k);
                group.setAuthUsers(buildAuthUser(config.getStringList("play.rbac.groupUser." + k)));
                group.insert();
            }else{
                group.setAuthUsers(buildAuthUser(config.getStringList("play.rbac.groupUser." + k)));
                group.update();
            }
        });
    }



    private List<AuthUser> buildAuthUser(List<String> authUserKeys){
        List<AuthUser> users = new ArrayList<>();
        for(Object authUserKey : authUserKeys) {
            AuthUser user = AuthUser.findByName(authUserKey.toString());
            users.add(user);
        }
        return users;
    }
}