package security.authorization;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Configuration;
import security.models.AuthGroup;
import security.models.AuthRole;
import security.models.AuthUser;
import security.models.AuthPermission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nisa on 26/10/17.
 *
 * reference: https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection
 *
 */
@Singleton
public class OnLoadRBAC {

    private final Configuration config;

    @Inject
    public OnLoadRBAC(Configuration config) {
        this.config = config;

        System.out.println("*** Loading control access tables...");

        System.out.println("*** Loading control access tables... - truncate group, role and permission table");
        AuthGroup.deleteAll();
        AuthRole.deleteAll();
        AuthPermission.deleteAll();

        System.out.println("*** Loading control access tables... - permissions");
        loadPermissions(config.getConfig("rbac.roles_permissions"));

        System.out.println("*** Loading control access tables... - authRoles");
        loadRoles(config.getConfig("rbac.roles_permissions"));

        System.out.println("*** Loading control access tables... - authGroups");
        loadGroups(config.getConfig("rbac.groups_roles"));

        System.out.println("*** Loading control access tables... - users");
        loadUsers(config.getConfig("rbac.groups_users"));

        System.out.println("*** Complete control access tables...");
    }

    private void loadPermissions(Configuration config) {
        Set<String> permissionKeys = new HashSet<>();

        for(String roleKey : config.subKeys()) {
            permissionKeys.addAll(config.getStringList(roleKey));
        }

        for(String permissionKey : permissionKeys) {
            AuthPermission authPermission = AuthPermission.findByName(permissionKey);
            if(authPermission == null){
                authPermission = new AuthPermission();
                authPermission.setName(permissionKey);
                authPermission.insert();
            }
        }
    }

    private void loadRoles(Configuration config){
        for(String roleKey : config.subKeys()) {
            AuthRole authRole = AuthRole.findByName(roleKey);

            if(authRole == null){
                authRole = new AuthRole();
                authRole.setName(roleKey);
                List<AuthPermission> authPermissions = AuthPermission.findAllByName(config.getStringList(roleKey));
                authRole.setAuthPermissions(authPermissions);
                authRole.insert();

            }else{
                List<AuthPermission> authPermissions = authRole.getAuthPermissions();
                int size = authPermissions.size();

                for(String permissionKey : config.getStringList(roleKey)) {
                    AuthPermission authPermission = AuthPermission.findByNameRoleId(permissionKey, authRole.getId());
                    if(authPermission == null){
                        authPermission = new AuthPermission();
                        authPermission.setName(permissionKey);
                        authPermissions.add(authPermission);
                    }
                }

                if(size < authPermissions.size()) {
                    authRole.setAuthPermissions(authPermissions);
                    authRole.update();
                }
            }
        }
    }

    private void loadGroups(Configuration config){
        for(String groupKey : config.subKeys()) {
            AuthGroup authGroup = AuthGroup.findByName(groupKey);

            if(authGroup == null){
                authGroup = new AuthGroup();
                authGroup.setName(groupKey);
                List<AuthRole> authRoles = AuthRole.findAllByName(config.getStringList(groupKey));
                authGroup.setAuthRoles(authRoles);
                authGroup.insert();

            }else{
                List<AuthRole> authRoles = authGroup.getAuthRoles();
                int size = authRoles.size();

                for(String roleKey : config.getStringList(groupKey)) {
                    AuthRole authRole = AuthRole.findByNameGroupId(roleKey, authGroup.getId());
                    if(authRole == null){
                        authRole = new AuthRole();
                        authRole.setName(roleKey);
                        authRoles.add(authRole);
                    }
                }

                if(size < authRoles.size()) {
                    authGroup.setAuthRoles(authRoles);
                    authGroup.update();
                }
            }
        }
    }

    private void loadUsers(Configuration config){
        for(String groupKey : config.subKeys()) {

            for(String userKey : config.getStringList(groupKey)) {
                AuthUser authUser = AuthUser.findByEmail(userKey);

                if(authUser != null && authUser.getAuthGroup() == null) {
                    AuthGroup authGroup = AuthGroup.findByName(groupKey);
                    authUser.setAuthGroup(authGroup);
                    authUser.update();
                }
            }
        }
    }
}