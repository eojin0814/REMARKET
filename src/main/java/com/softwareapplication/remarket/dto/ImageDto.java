package com.softwareapplication.remarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class ImageDto {

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class Request {
		private MultipartFile imageFile;	
	}
}
