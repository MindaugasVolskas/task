package com.mindaugas.task.duplicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DuplicateService {

	public List<Duplicate> countDuplicates(List<String> strings) {
		List<Duplicate> duplicates = new ArrayList<>();
		strings.stream().distinct().forEach(s -> {
			duplicates.add(new Duplicate(s, Collections.frequency(strings, s)));
		});
		
		return duplicates;
	}
}
