package com.yui.clearops.services;

import com.yui.clearops.model.entities.Software;
import com.yui.clearops.repositories.SoftwareRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository repository;

    public List<Software> listAll(){
        return repository.findAll();
    }

    public List<Software> buscarComFiltro(String nome, String depto, String status) {
        return repository.findAll(((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(nome != null && !nome.isBlank()){
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            if(depto != null && !depto.isBlank()){
                predicates.add(cb.equal(root.get("departamento"), depto));
            }
            if (status != null && !status.isBlank()){
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }));
    }

    public void save(Software software){

        repository.save(software);
    }
}
