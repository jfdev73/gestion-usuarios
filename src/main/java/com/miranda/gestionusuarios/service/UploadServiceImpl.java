package com.miranda.gestionusuarios.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements IUploadFileService{
	
	private static String UPLOADS_FOLDER = "uploads";
	

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFileName = UUID.randomUUID().toString() + "_"+file.getOriginalFilename();
		Path rootPath = getPath(uniqueFileName);
		/*
		 * Path rootPath = Paths.get("uploads").resolve(uniqueFileName); Path rootAbPath
		 * = rootPath.toAbsolutePath();
		 */
			
			InputStream archivoInputStream = file.getInputStream();
			Files.copy(archivoInputStream, rootPath);
			archivoInputStream.close();
			return uniqueFileName;
			/*
			 * byte [] bytes = foto.getBytes(); Path rutaCompleta =
			 * Paths.get(rootPath+"//"+foto.getOriginalFilename());
			 * Files.write(rutaCompleta, bytes);
			 */
			
		
	}

	@Override
	public boolean delete(String fileName) {
		Path rootPath = getPath(fileName);
		File archivo = rootPath.toFile();
		
		return archivo.delete();
		
		/*
		 * if(archivo.exists() && archivo.canRead()) { return archivo.delete(); }
		 * 
		 * return false;
		 */
	}
	
	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

}
