package com.miranda.gestionusuarios.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	String copy (MultipartFile file) throws IOException;
	
	boolean delete(String fileName);
	

}
