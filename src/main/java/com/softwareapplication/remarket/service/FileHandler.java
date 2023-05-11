package com.softwareapplication.remarket.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileHandler implements ApplicationContextAware {

	@Value("/upload/")
	private String uploadDirLocal;
	
	private WebApplicationContext context;	
	private String uploadDir;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.context = (WebApplicationContext) applicationContext;
		this.uploadDir = context.getServletContext().getRealPath(this.uploadDirLocal);
		System.out.println(this.uploadDir);
	}

	// 이미지 업로드 후 파일 path 반환
	public String uploadFile(MultipartFile imageFile) {
		String filename = UUID.randomUUID().toString() 
				+ "_" + imageFile.getOriginalFilename();

		System.out.println("업로드 한 파일: "	+ filename);

		File file = new File(this.uploadDir + filename);
		try {
			imageFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return filename;
	}
}
