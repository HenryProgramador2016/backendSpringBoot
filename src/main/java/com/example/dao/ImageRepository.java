package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.ImageModel;
import com.example.util.Plantilla;

import java.util.*;
@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {

    //Optional<ImageModel> findByName(String name);
    public ImageModel findByName(String name);
    
  //Optional<ImageModel> findByName(String name);
    public ImageModel findByCodeImage(String code);
    //new ImageModel( ) 
    //,  nativeQuery = true
    @Query(
    		  value = "SELECT new ImageModel(name,descripcion,imageBinario) FROM ImageModel where tipo=:tipoGallery ")
    public List<ImageModel> listImages(@Param("tipoGallery") String tipo);
  
    
    @Query(
  		  value = "SELECT new ImageModel(name,descripcion,URL,tipo) FROM ImageModel where tipo=:tipoGallery ")
  public List<ImageModel> listImagesMejorado(@Param("tipoGallery") String tipo);

}