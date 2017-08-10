package com.mindaugas.task.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/files")
	public void saveStringDuplicateCount(@RequestBody FileDto fileDto) {
		fileService.saveStringDuplicateCount(fileDto.getLocation(), fileDto.getFileType(), fileDto.getSeparator());
	}
}
