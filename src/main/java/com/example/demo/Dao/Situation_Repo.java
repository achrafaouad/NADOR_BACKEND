package com.example.demo.Dao;

import com.example.demo.Model.Projet;
import com.example.demo.Model.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Situation_Repo extends JpaRepository<Situation,Long> {



}
