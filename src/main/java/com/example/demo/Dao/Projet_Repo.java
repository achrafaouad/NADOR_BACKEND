package com.example.demo.Dao;

import com.example.demo.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface Projet_Repo extends JpaRepository<Projet,Long> {


    @Query(nativeQuery = true,
            value = "update section set geom = ST_GeomFromGeoJSON(:geom1) where id_section = :sec_id  ")
            void saveGeom(@Param("sec_id") Long sec_id,@Param("geom1") String geom1);

    @Query(value = "SELECT ST_AsGeoJSON(s.geom) as geojson\n" +
            "FROM section s,projet_sections ps ,projet prj\n" +
            "where ps.projet_id = prj.id\n" +
            "and s.id_section = ps.sections_id_section\n" +
            "and prj.id = :projectId ", nativeQuery = true)
    List<String>getGeomProjet(@Param("projectId") Long projectId);
}
