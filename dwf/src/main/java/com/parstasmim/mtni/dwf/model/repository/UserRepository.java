package com.parstasmim.mtni.dwf.model.repository;

import com.parstasmim.mtni.dwf.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    int countByUserName(String userName);

    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    int countAllByUserNameAndPassword(String userName, String password);

//    User findUserAndAccessesByUserNameAndPassword(String userName, String password);

}