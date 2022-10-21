package com.example.miproyectowebspringboot.models.entity.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("uploadService")
public class UploadServiceImpl implements IUploadService {
    private static final Logger LOG = LoggerFactory.getLogger(UploadServiceImpl.class);
    private static final String UPLOADS_FOLDER = "uploads";

    @Override
    public Resource load(String filename) {
        Resource recurso = null;
        try {
            recurso = new UrlResource(getPath(filename).toUri());
            if (!recurso.exists() || !recurso.isReadable()) {
                throw new RuntimeException("Error: No se puede cargar la foto: " + getPath(filename).toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) {
        String nombreUnico = UUID.randomUUID().toString().concat("_").concat(file.getOriginalFilename()).trim();
        Path rootPath = getPath(nombreUnico);
        LOG.info("rootPath: " + rootPath);

        try {
            Files.copy(file.getInputStream(), rootPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nombreUnico;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
            File archivo = rootPath.toFile();
            if (archivo.exists() && archivo.canRead()) {
                if (archivo.delete()) {
                    return true;
                }
            }
        return false;
    }

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    @Override
    public void crearDirectorio() {
        try {
            Files.createDirectory(Paths.get(UPLOADS_FOLDER));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }

    @Override
    public void borrarDirectorio() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile()); 
    }

}
