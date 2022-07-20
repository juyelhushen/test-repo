package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ModelClass;

@Repository
public interface GetRepository extends JpaRepository<ModelClass, Integer> 
{

}
