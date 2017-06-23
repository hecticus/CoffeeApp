package models.dao;

import com.fasterxml.jackson.databind.JsonNode;
import models.domain.User;

/**
 * Created by yenny on 9/30/16.
 */
public interface UserDao extends AbstractDao<Long, User> {

    User findByEmail(String email);

    User findByTokenAndID(Long id, String token);

    User findByToken(String token);

    User findUniqueByEmail(String email);

    /*drocha*/
    JsonNode uploadPhoto(JsonNode request);
    /*drocha*/
}
