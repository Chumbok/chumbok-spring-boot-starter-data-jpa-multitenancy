package com.chumbok.multitenancy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelloRepository extends JpaRepository<Hello, String> {

    List<Hello> findAllByMessage(String message);

}