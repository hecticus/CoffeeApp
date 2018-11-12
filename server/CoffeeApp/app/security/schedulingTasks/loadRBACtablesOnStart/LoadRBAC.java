package security.schedulingTasks.loadRBACtablesOnStart;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.typesafe.config.Config;
import security.models.AuthGroup;
import security.models.AuthRole;
import security.models.AuthPermission;
import security.models.AuthUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisa on 26/10/17.
 *
 * reference: https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection
 *
 */
@Singleton
public class LoadRBAC {

    private final Config config;

    @Inject
    public LoadRBAC(Config config) {
        this.config = config;

        System.out.println("*** Loading control access tables...");

        System.out.println("*** Loading control access tables... - truncate group, role and permission table");
        AuthGroup.deleteAll();
        AuthRole.deleteAll();
        AuthPermission.deleteAll();

        System.out.println("*** Loading control access tables... - permissions");
        loadData(config);

        System.out.println("*** Complete control access tables...");
    }


    private void loadData(Config config){
        // Load roles and permission
        config.getObject("play.rbac.roles_permissions").forEach((k,v) -> {
            System.out.println("key : " + k + " value : " + v);
            AuthRole authRole = AuthRole.findByName(k);
            if(authRole == null){
                authRole = new AuthRole();
                authRole.setName(k);
                authRole.setAuthPermissions( loadPermissions(config.getStringList("play.rbac.roles_permissions." + k)));
                authRole.insert();
            }else {
                authRole.setAuthPermissions( loadPermissions(config.getStringList("play.rbac.roles_permissions." + k)));
                authRole.save();
            }
        });

        config.getObject("play.rbac.groups_roles").forEach((k,v) -> {
            System.out.println("key : " + k + " value : " + v);
            AuthGroup authGroup = AuthGroup.findByName(k);
            if(authGroup == null){
                authGroup = new AuthGroup();
                authGroup.setName(k);
                authGroup.setAuthRoles( loadRoles(config.getStringList("play.rbac.groups_roles." + k)));
                authGroup.insert();
            }else {
                authGroup.setAuthRoles( loadRoles(config.getStringList("play.rbac.groups_roles." + k)));
                authGroup.save();
            }
        });


        config.getObject("play.rbac.groups_users").forEach( (k,v) -> {
            System.out.println("key : " + k + " value : " + v);
            AuthGroup authGroup = AuthGroup.findByName(k);
            authGroup.setAuthUsers(loadUsers(config.getStringList("play.rbac.groups_users." + k), k));
            System.out.println("#################################################");
            authGroup.save();

        });
    }


    private List<AuthPermission> loadPermissions(List<String> permissions) {
        List<AuthPermission> listpermission = new ArrayList<>();

        permissions.forEach( permission -> {
            AuthPermission authPermission = AuthPermission.findByName(permission);

            if(authPermission == null){
                authPermission = new AuthPermission();
                authPermission.setName(permission);
                authPermission.insert();
                listpermission.add(authPermission);
            }
        });

        return listpermission;
    }


    private List<AuthRole> loadRoles(List<String> roles) {
        List<AuthRole> listRoles = new ArrayList<>();

        roles.forEach( role -> {
            AuthRole authRole = AuthRole.findByName(role);

            if(authRole == null){
                authRole = new AuthRole();
                authRole.setName(role);
                authRole.insert();
                listRoles.add(authRole);
            }
        });

        return listRoles;
    }

    private List<AuthUser> loadUsers(List<String> users, String group) {
        List<AuthUser> listUsers = new ArrayList<>();

        users.forEach( user -> {
            AuthUser authUser = AuthUser.findByEmail(user);
            if(authUser != null && authUser.getAuthGroup() == null) {
                System.out.println("sSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSs");
                AuthGroup authGroup = AuthGroup.findByName(group);
                authUser.setAuthGroup(authGroup);
                authUser.update();
            }
        });

        return listUsers;
    }

}