package com.stage.gestionTickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stage.gestionTickets.model.Solution;


public interface SolutionRepository extends JpaRepository<Solution,Integer> {

}
