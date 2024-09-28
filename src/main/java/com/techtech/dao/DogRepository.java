package com.techtech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techtech.entity.DogEntity;

//public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {
//CrudRepository
@Repository
public interface DogRepository extends JpaRepository<DogEntity, Integer>{

}
