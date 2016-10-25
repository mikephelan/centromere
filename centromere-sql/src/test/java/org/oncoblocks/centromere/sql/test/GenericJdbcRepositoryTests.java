/*
 * Copyright 2015 William Oemler, Blueprint Medicines
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

package org.oncoblocks.centromere.sql.test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.blueprint.centromere.core.repository.Evaluation;
import com.blueprint.centromere.core.repository.QueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author woemler
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestJdbcDataSourceConfig.class, JdbcRepositoryConfig.class  })
@FixMethodOrder
public class GenericJdbcRepositoryTests {
	
	@Autowired private SubjectRepository subjectRepository;
	
	private static boolean isConfigured = false;
	
	@Before
	public void setup(){
		if (isConfigured) return;
		subjectRepository.deleteAll();
		for (Subject subject: Subject.createDummyData()){
			subjectRepository.insert(subject);
		}
		isConfigured = true;
	}

	@Test
	public void findOneTest(){
		Subject subject = subjectRepository.findOne(1L);
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(1L));
		Assert.isTrue(subject.getName().equals("PersonA"));
	}

	@Test
	public void findAllTest(){
		List<Subject> subjects = subjectRepository.findAll();
		Assert.notNull(subjects);
		Assert.notEmpty(subjects);
		Assert.isTrue(subjects.size() == 5);
		Subject subject = subjects.get(0);
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(1L));
		Assert.isTrue(subject.getName().equals("PersonA"));
	}

	@Test
	public void findAllSorted(){
		List<Subject> subjects = subjectRepository.findAll(
				new Sort(new Sort.Order(Sort.Direction.DESC, "subjects.subject_id")));
		Assert.notNull(subjects);
		Assert.notEmpty(subjects);
		Subject subject = subjects.get(0);
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(5L));
		Assert.isTrue(subject.getName().equals("A375"));
	}

	@Test
	public void findAllPaged(){
		PageRequest pageRequest = new PageRequest(1, 3);
		Page<Subject> page = subjectRepository.findAll(pageRequest);
		Assert.notNull(page);
		Assert.isTrue(page.getTotalPages() == 2);
		Assert.isTrue(page.getTotalElements() == 5);
		List<Subject> subjects = page.getContent();
		Assert.isTrue(subjects.size() == 2);
		Subject subject = subjects.get(0);
		Assert.isTrue(subject.getId().equals(4L));
	}

	@Test
	public void countTest(){
		Long count = subjectRepository.count();
		Assert.notNull(count);
		Assert.isTrue(count.equals(5L));
	}

	@Test
	public void queryCriteriaTest(){
		List<QueryCriteria> queryCriterias = new ArrayList<>();
		queryCriterias.add(new QueryCriteria("subjects.name", "PersonB", Evaluation.EQUALS));
		List<Subject> subjects = subjectRepository.find(queryCriterias);
		Assert.notNull(subjects);
		Assert.notEmpty(subjects);
		Assert.isTrue(subjects.size() == 1);
		Subject subject = subjects.get(0);
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(2L));
		Assert.isTrue(subject.getName().equals("PersonB"));
	}

	@Test
	public void sortedCriteriaTest(){
		List<QueryCriteria> queryCriterias = new ArrayList<>();
		queryCriterias.add(new QueryCriteria("subjects.gender", "F", Evaluation.EQUALS));
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "subjects.subject_id"));

		List<Subject> subjects = subjectRepository.find(queryCriterias, sort);
		Assert.notNull(subjects);
		Assert.notEmpty(subjects);
		Assert.isTrue(subjects.size() == 2);

		Subject subject = subjects.get(0);
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(4L));
	}

	@Test
	public void pagedCriteriaTest(){
		List<QueryCriteria> queryCriterias = new ArrayList<>();
		queryCriterias.add(new QueryCriteria("subjects.species", "Homo sapiens", Evaluation.EQUALS));
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "subjects.subject_id"));
		PageRequest pageRequest = new PageRequest(1, 3, sort);

		Page<Subject> page = subjectRepository.find(queryCriterias, pageRequest);
		Assert.notNull(page);
		Assert.isTrue(page.getTotalPages() == 2);
		Assert.isTrue(page.getTotalElements() == 5);

		List<Subject> subjects = page.getContent();
		Assert.isTrue(subjects.size() == 2);

		Subject subject = subjects.get(0);
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(2L));
	}

	@Test
	public void findAllPagedCriteriaTest(){
		List<QueryCriteria> queryCriterias = new ArrayList<>();
		PageRequest pageRequest = new PageRequest(1, 3);

		Page<Subject> page = subjectRepository.find(queryCriterias, pageRequest);
		Assert.notNull(page);
		Assert.isTrue(page.getTotalElements() == 5);
		Assert.isTrue(page.getTotalPages() == 2);

		List<Subject> subjects = page.getContent();
		Assert.notNull(subjects);
		Assert.notEmpty(subjects);
		Assert.isTrue(subjects.size() == 2);

		Subject subject = subjects.get(0);
		Assert.isTrue(subject.getId().equals(4L));

	}

	@Test
	public void insertTest(){

		Subject subject = subjectRepository.insert(
				new Subject(6L, "PersonD", "Homo sapiens", "F", "patient", null, null, null));
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(6L));

		subject = subjectRepository.findOne(6L);
		Assert.notNull(subject);
		Assert.isTrue(subject.getId().equals(6L));
		
		subjectRepository.delete(6L);

	}

	@Test
	public void updateTest(){

		Subject subject = subjectRepository.insert(
				new Subject(6L, "PersonD", "Homo sapiens", "F", "patient", null, null, null));

		subject.setName("TEST_NAME");

		Subject updated = subjectRepository.update(subject);
		Assert.notNull(updated);
		Assert.isTrue(updated.getName().equals("TEST_NAME"));

		subject = subjectRepository.findOne(6L);
		Assert.notNull(subject);
		Assert.isTrue(subject.getName().equals("TEST_NAME"));
		
		subjectRepository.delete(6L);
		
	}

	@Test
	public void deleteTest(){

		Subject subject = subjectRepository.insert(
				new Subject(6L, "PersonD", "Homo sapiens", "F", "patient",null, null, null));
		Assert.isTrue(subject.getId().equals(6L));
		Assert.notNull(subject);
		subjectRepository.delete(6L);
		subject = subjectRepository.findOne(6L);
		Assert.isNull(subject);

	}
	
	
	
}
