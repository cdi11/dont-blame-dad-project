package org.charlie.dontblamedadproject.models.data;


import org.charlie.dontblamedadproject.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer>{
    User findByUsername(String username);
}
