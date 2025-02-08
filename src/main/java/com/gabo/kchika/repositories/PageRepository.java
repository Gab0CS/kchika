package com.gabo.kchika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabo.kchika.entities.PageEntity;

//No es necesario mapear con @Repository porque automáticamente
//se hace al extenderlo de JpaRepository
//Usaríamos la anotación si tuviéramos JDBC o EntityManager
public interface PageRepository extends JpaRepository<PageEntity, Long> {

}
