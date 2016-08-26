/*
 * Copyright 2016 William Oemler, Blueprint Medicines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.oncoblocks.centromere.core.commons.testing;

import org.oncoblocks.centromere.core.commons.models.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author woemler
 */
public class SubjectDataGenerator<T extends Subject<?>> implements DummyDataGenerator<T> {

	public List<T> generateData(Class<T> type) throws Exception {
		
		List<T> subjects = new ArrayList<>();
		
		T subject = type.newInstance();
		subject.setName("SubjectA");
		subject.setSpecies("Human");
		subject.setGender("M");
		subject.setNotes("This is an example subject");
		subject.addAlias("subject_a");
		subject.addAttribute("tag", "tagA");
		subjects.add(subject);

		subject = type.newInstance();
		subject.setName("SubjectB");
		subject.setSpecies("Human");
		subject.setGender("F");
		subject.setNotes("This is an example subject");
		subject.addAlias("subject_b");
		subject.addAttribute("tag", "tagA");
		subjects.add(subject);

		subject = type.newInstance();
		subject.setName("SubjectC");
		subject.setSpecies("Mouse");
		subject.setGender("M");
		subject.setNotes("This is an example subject");
		subject.addAlias("subject_c");
		subject.addAttribute("tag", "tagB");
		subjects.add(subject);

		subject = type.newInstance();
		subject.setName("SubjectD");
		subject.setSpecies("Human");
		subject.setGender("U");
		subject.setNotes("This is an example subject");
		subject.addAlias("subject_d");
		subject.addAttribute("tag", "tagB");
		subjects.add(subject);

		subject = type.newInstance();
		subject.setName("SubjectE");
		subject.setSpecies("Mouse");
		subject.setGender("F");
		subject.setNotes("This is an example subject");
		subject.addAlias("subject_e");
		subject.addAttribute("tag", "tagA");
		subjects.add(subject);
		
		return subjects;
		
	}
	
}