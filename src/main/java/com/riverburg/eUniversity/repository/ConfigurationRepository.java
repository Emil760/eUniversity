package com.riverburg.eUniversity.repository;

import com.riverburg.eUniversity.model.entity.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Integer> {

    @Query(value = "select value from ConfigurationEntity where name = :name")
    String getValueByName(@Param("name") String name);

    ConfigurationEntity findByName(String name);
}
