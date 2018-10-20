package com.parstasmim.mtni.dwf.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    T findByDisplayName(String displayName);

}