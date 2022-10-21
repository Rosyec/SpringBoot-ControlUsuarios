package com.example.miproyectowebspringboot.models.entity.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    public Resource load(String filename);
    public String copy(MultipartFile file);
    public boolean delete(String filename);
    public void crearDirectorio();
    public void borrarDirectorio();
}
