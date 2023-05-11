package com.example.demo.Dao;

import com.example.demo.Dtos.GeoJsonResultDTO;
import com.example.demo.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface Projet_Repo extends JpaRepository<Projet,Long>  {


    @Query(nativeQuery = true,
            value = "update section set geom = ST_GeomFromGeoJSON(:geom1) where id_section = :sec_id  ")
            void saveGeom(@Param("sec_id") Long sec_id,@Param("geom1") String geom1);

    @Query(value = "SELECT CAST(jsonb_build_object(\n" +
            "    'type', 'Feature',\n" +
            "    'properties', jsonb_build_object('intitule', l.intitule),\n" +
            "    'geometry', ST_AsGeoJSON(s.geom)\n" +
            ") AS TEXT) AS json_result\n" +
            "FROM section s\n" +
            "JOIN lot_sections ps ON s.id_section = ps.sections_id_section\n" +
            "JOIN projet_lots pl ON ps.lot_id = pl.lots_id\n" +
            "JOIN lot l ON l.id = pl.lots_id\n" +
            "WHERE projet_id = :projectId ", nativeQuery = true)
    List<String>getGeomProjet(@Param("projectId") Long projectId);
}
