package models.dao;

import models.domain.SecurityTag;

/**
 * Created by yenny on 9/30/16.
 */
public interface SecurityTagDao extends AbstractDao<Long, SecurityTag> {

    SecurityTag GetTagByName(String name);
    SecurityTag CheckAndInsert(String name);
}
