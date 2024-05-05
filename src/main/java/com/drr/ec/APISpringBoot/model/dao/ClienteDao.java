package com.drr.ec.APISpringBoot.model.dao;

import com.drr.ec.APISpringBoot.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, Integer> {
}


/* CrudRepository , JpaRepository , PagingAndSortingRepository */