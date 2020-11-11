package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.StorageProperties;
import com.example.exceptions.FileNotFoundException;
import com.example.exceptions.StorageException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	 private final Path rootLocation;

	    @Autowired
	    public FilesStorageServiceImpl(StorageProperties properties) {
	        this.rootLocation = Paths.get(properties.getLocation());
	    }

	    @Override
	    @PostConstruct
	    public void init() {
	        try {
	            Files.createDirectories(rootLocation);
	        } catch (IOException e) {
	            throw new StorageException("Could not initialize storage location", e);
	        }
	    }

	    @Override
	    public String store(MultipartFile file) {
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        try {
	            if (file.isEmpty()) {
	                throw new StorageException("Failed to store empty file " + filename);
	            }
	            if (filename.contains("..")) {
	                // This is a security check
	                throw new StorageException(
	                        "Cannot store file with relative path outside current directory "
	                                + filename);
	            }
	            try (InputStream inputStream = file.getInputStream()) {
	                Files.copy(inputStream, this.rootLocation.resolve(filename),
	                        StandardCopyOption.REPLACE_EXISTING);
	            }
	        }
	        catch (IOException e) {
	            throw new StorageException("Failed to store file " + filename, e);
	        }

	        return filename;
	    }

	    @Override
	    public Stream<Path> loadAll() {
	        try {
	            return Files.walk(this.rootLocation, 1)
	                    .filter(path -> !path.equals(this.rootLocation))
	                    .map(this.rootLocation::relativize);
	        }
	        catch (IOException e) {
	            throw new StorageException("Failed to read stored files", e);
	        }

	    }

	    @Override
	    public Path load(String filename) {
	        return rootLocation.resolve(filename);
	    }
	    
	    public String getPath(String filename) {
	    	 String uri ="http://localhost:5555/api/images/" + filename;				
			return uri;
	    }

	    
	    @Override
	    public Resource loadAsResource(String filename) {
	        try {
	            Path file = load(filename);
	            System.out.println( file.toAbsolutePath());
	            Resource resource = new UrlResource(file.toUri());
	            if (resource.exists() || resource.isReadable()) {
	                return resource;
	            }
	            else {
	                throw new FileNotFoundException(
	                        "Could not read file: " + filename);
	            }
	        }
	        catch (MalformedURLException e) {
	            throw new FileNotFoundException("Could not read file: " + filename, e);
	        }
	    }

	    @Override
	    public void deleteAll() {
	        FileSystemUtils.deleteRecursively(rootLocation.toFile());
	    }
}