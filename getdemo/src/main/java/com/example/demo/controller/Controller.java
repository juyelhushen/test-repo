package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.demo.model.ModelClass;
import com.example.demo.repository.GetRepository;
import com.example.demo.springbootrediscache.ResouceNotFoundException;


@RestController
@Transactional
@RequestMapping(path="/api")
public class Controller 
{
	@Autowired
	GetRepository repo;
	
	@Autowired
	CacheManager cacheManager;
	
	@GetMapping(path="/model")
	@Cacheable(key = "'Smsalert'",value = "allModelClassCache")
	public List<ModelClass> getAll()
	{
		System.out.println("Getting the record Database");
		 return repo.findAll();
	}	
	
	@GetMapping(path="/model/{id}")
	@Cacheable(key = "'ModelClass'",value = "allModelClassCache")
	public Optional<ModelClass> getById(@PathVariable Integer id)
	{
		System.out.println("Fetching from the Database"+id);
		return repo.findById(id);
	}
	
	@PostMapping(path="/post")
	@CachePut(key = "'Smsalert'",value = "allModelClassCache")
	public List<ModelClass> addDetail(@RequestBody ModelClass model)
	{
		System.out.println("posting");
		repo.save(model);
		return repo.findAll();
		
	}
	
	@PutMapping(path="/model")
	@CachePut(key = "'ModelClass'",value = "allModelClassCache")
	public ModelClass updateModelClass(@PathVariable Integer id, @RequestBody ModelClass modelDetails)
	{
		ModelClass model = repo.findById(id).orElseThrow(() -> new ResouceNotFoundException("ModelClass not found for this id :: " + id));
	    model.setName(modelDetails.getName());
		final ModelClass updatedModelClass = repo.save(model);
		return updatedModelClass;
		
	}
	
	/*
	public String update(@RequestBody ModelClass model)
	{
		System.out.println("MODEL----"+model.getName());
		System.out.println("updated in database");
		Optional<ModelClass> modelDB = repo.findById(model.getId());
		
		if(modelDB!=null)
		{
			repo.save(model);
			return "success";
		}
		else 
		{
			return "Failure";
		}
		
	}*/
	@DeleteMapping(path="/model/{id}")
	@CacheEvict(key = "'Smsalert'",value = "allModelClassCache")
	public void delete(@PathVariable Integer id)
	{
		System.out.println("deleted from db as well as cache");
	     repo.deleteById(id);
		
		
	}
	
}
