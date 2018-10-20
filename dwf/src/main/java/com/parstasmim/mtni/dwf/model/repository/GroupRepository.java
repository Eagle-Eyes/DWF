package com.parstasmim.mtni.dwf.model.repository;

import com.parstasmim.mtni.dwf.model.entity.Group;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {


}