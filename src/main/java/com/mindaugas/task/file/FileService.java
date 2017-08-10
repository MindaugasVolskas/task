package com.mindaugas.task.file;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindaugas.task.duplicate.Duplicate;
import com.mindaugas.task.duplicate.DuplicateService;

@Service
public class FileService {

	@Autowired
	private DuplicateService duplicateService;

	public void saveStringDuplicateCount(String fileLocation, String fileType, String separator) {
		List<String> strings = readFromFile(fileLocation, fileType, separator);
		saveDuplicates(strings, fileLocation);
	}

	private List<String> readFromFile(String fileLocation, String fileType, String separator) {
		File folder = new File(fileLocation);
		List<String> strings = new ArrayList<>();
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(fileType)) {
				try {
					AsynchronousFileChannel fileChannel = AsynchronousFileChannel
							.open(Paths.get(file.getAbsolutePath()), StandardOpenOption.READ);
					ByteBuffer buffer = ByteBuffer.allocate(Math.toIntExact(file.length()));
					fileChannel.read(buffer, 0).get();
					strings.addAll(
							Arrays.asList((new String(buffer.array()).trim()).replaceAll("\r", "").split(separator)));
					buffer.clear();
				} catch (IOException | InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

		return strings;
	}

	private void saveDuplicates(List<String> strings, String fileLocation) {
		List<Duplicate> duplicates = duplicateService.countDuplicates(strings);
		List<Duplicate> aGDuplicates = new ArrayList<>();
		List<Duplicate> hNDuplicates = new ArrayList<>();
		List<Duplicate> oUDuplicates = new ArrayList<>();
		List<Duplicate> vZDuplicates = new ArrayList<>();
		for (Duplicate duplicate : duplicates) {
			char c = duplicate.getWord().toUpperCase().charAt(0);
			if (c >= 'A' && c <= 'G') {
				aGDuplicates.add(duplicate);
			} else if (c >= 'H' && c <= 'N') {
				hNDuplicates.add(duplicate);
			} else if (c >= 'O' && c <= 'U') {
				oUDuplicates.add(duplicate);
			} else if (c >= 'V' && c <= 'Z') {
				vZDuplicates.add(duplicate);
			}

		}

		saveToFile(aGDuplicates, fileLocation, "aGDuplicates.txt");
		saveToFile(hNDuplicates, fileLocation, "hNDuplicates.txt");
		saveToFile(oUDuplicates, fileLocation, "oUDuplicates.txt");
		saveToFile(vZDuplicates, fileLocation, "vZDuplicates.txt");
	}

	private void saveToFile(List<Duplicate> duplicates, String fileLocation, String fileName) {
		try {
			String toBeSaved = "";
			for (Duplicate duplicate : duplicates) {
				toBeSaved += duplicate.toString();
			}

			AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get(fileName),
					StandardOpenOption.WRITE, StandardOpenOption.CREATE);
			ByteBuffer buffer = ByteBuffer.allocate(toBeSaved.length());
			buffer.put(toBeSaved.getBytes());
			buffer.flip();

			fileChannel.write(buffer, 0);
			buffer.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
