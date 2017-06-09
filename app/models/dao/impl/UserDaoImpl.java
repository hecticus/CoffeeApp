package models.dao.impl;

import com.fasterxml.jackson.databind.JsonNode;
import models.dao.UserDao;
import models.domain.User;
import models.manager.multimediaUtils.Multimedia;

import java.util.List;

/**
 * Created by yenny on 9/30/16.
 */
public class UserDaoImpl extends AbstractDaoImpl<Long, User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email){
        List<User> users = find
                .where()
                .eq("email", email)
                .findList();

        if(!users.isEmpty())
            return users.get(0);
        return null;
    }

    @Override
    public User findByTokenAndID(Long id, String token){
        List<User> users = find
                .where()
                .eq("token", token)
                .eq("id", id)
                .findList(); //por que no findunique?
        if(!users.isEmpty())
            return users.get(0);
        return null;
    }

    @Override
    public User findByToken(String token){
        List<User> users = find
                .where()
                .eq("token", token)
                .findList(); //por que no findunique?
        if(!users.isEmpty())
            return users.get(0);
        return null;
    }

    public User findUniqueByEmail(String email){
        return find
                .where()
                .eq("email", email)
                .findUnique();
    }

    public User findUniqueByEmail(String email, Long id){
        return find
                .where()
                .ne("id", id)
                .eq("email", email)
                .findUnique();
    }

    @Override
    public JsonNode uploadPhoto(JsonNode request)
    {
        Multimedia multimedia = new Multimedia();

        return multimedia.uploadPhoto(request);

    }
}
