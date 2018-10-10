package com.chumbok.multitenancy;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HelloRepository extends JpaRepository<Hello, String> {

    List<Hello> findAllByMessage(String message);

    @Override
    //@Query("SELECT h from Hello h WHERE id = ?1 and org = 'YYYY' and tenant = 'YYYY'")
    Optional<Hello> findById(String s);
}