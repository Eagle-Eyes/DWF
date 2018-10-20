package com.parstasmim.mtni.dwf.model.repository;

import com.parstasmim.mtni.dwf.model.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {

}