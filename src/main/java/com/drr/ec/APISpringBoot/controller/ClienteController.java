package com.drr.ec.APISpringBoot.controller;

import com.drr.ec.APISpringBoot.model.dto.ClienteDto;
import com.drr.ec.APISpringBoot.model.entity.Cliente;
import com.drr.ec.APISpringBoot.model.payload.MessageResponse;
import com.drr.ec.APISpringBoot.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto) {
        Cliente clienteSave = null;
        try{
            clienteSave = clienteService.save(clienteDto);
            clienteDto = ClienteDto.builder()
                    .idCliente(clienteSave.getIdCliente())
                    .nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .correo(clienteSave.getCorreo())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .build();

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Saved Sucessfully").object(clienteDto).build(),HttpStatus.CREATED);
        }catch (DataAccessException err){
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(err.getMessage())
                            .object(null)
                            .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @PutMapping("cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id) {
        // Validación para asegurarse de que el ID del cuerpo coincida con el ID del parámetro de ruta
        if (!id.equals(clienteDto.getIdCliente())) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("ID in the path and ID in the body do not match")
                            .object(null)
                            .build(), HttpStatus.BAD_REQUEST);
        }

        Cliente clienteUpdate = null;
        try {
            if (clienteService.existsById(id)) {
                clienteDto.setIdCliente(id);
                clienteUpdate = clienteService.save(clienteDto);
                clienteDto = ClienteDto.builder()
                        .idCliente(clienteUpdate.getIdCliente())
                        .nombre(clienteUpdate.getNombre())
                        .apellido(clienteUpdate.getApellido())
                        .correo(clienteUpdate.getCorreo())
                        .fechaRegistro(clienteUpdate.getFechaRegistro())
                        .build();

                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Updated Successfully")
                        .object(clienteDto)
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .message("Client not found")
                                .object(null)
                                .build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException err) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message("Database error: " + err.getMessage())
                            .object(null)
                            .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete,HttpStatus.NO_CONTENT);
        } catch (DataAccessException err) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(err.getMessage())
                            .object(null)
                            .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }




    @GetMapping("cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        if(cliente == null) {
            return  new ResponseEntity<>(MessageResponse.builder()
                    .message("Client not found").object(null).build(),HttpStatus.NOT_FOUND);

        }
        ClienteDto clienteDto = ClienteDto.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .correo(cliente.getCorreo())
                .fechaRegistro(cliente.getFechaRegistro())
                .build();
        return new ResponseEntity<>(MessageResponse.builder()
                .message("")
                .object(clienteDto)
                .build(),HttpStatus.OK);
    }

}
