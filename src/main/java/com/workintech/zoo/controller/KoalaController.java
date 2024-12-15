package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.validation.ZooKangarooValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init(){

        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAll(){

        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala get(@PathVariable int id){
        ZooKangarooValidation.isValid(id);
        return koalas.get(id);
    }

    @PostMapping
    public Koala save(@RequestBody Koala koala){

        ZooKangarooValidation.checkKangarooWeight(koala.getWeight());

        koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala){
        ZooKangarooValidation.isValid(id);
        ZooKangarooValidation.checkKangarooWeight(koala.getWeight());

        koala.setId(id);
        if(koalas.containsKey(id)){
            koalas.put(id,koala);
            return koalas.get(id);
        }
        else{
            return save(koala);
        }
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable int id){
        ZooKangarooValidation.isValid(id);

        return koalas.remove(id);
    }
}
