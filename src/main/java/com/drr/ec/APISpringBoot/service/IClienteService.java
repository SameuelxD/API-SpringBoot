package com.drr.ec.APISpringBoot.service;

import com.drr.ec.APISpringBoot.model.dto.ClienteDto;
import com.drr.ec.APISpringBoot.model.entity.Cliente;

public interface IClienteService {
    Cliente save(ClienteDto cliente);
    Cliente findById(Integer id);
    void delete(Cliente cliente);
    boolean existsById(Integer id);
}
