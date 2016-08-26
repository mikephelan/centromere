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

package org.oncoblocks.centromere.core.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oncoblocks.centromere.core.dataimport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * @author woemler
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class DataImportTests {
	
	@Autowired private Validator validator;
	private final String geneInfoPath = ClassLoader.getSystemClassLoader().getResource("Homo_sapiens.gene_info").getPath();
	@Autowired private GeneInfoProcessor processor;
	@Autowired private TestRepository testRepository;
	@Autowired private BasicImportOptions defaultImportOptions;

	@Before
	public void setup() throws Exception{
		testRepository.deleteAll();
		testRepository.insert(EntrezGene.createDummyData());
	}

	@Test
	public void geneInfoReaderTest() throws Exception{
		
		GeneInfoReader reader = new GeneInfoReader();
		List<EntrezGene> genes = new ArrayList<>();
		
		Exception exception = null;
		try {
			reader.open("bad_file.txt");	
		} catch (Exception e){
			exception = e;
		} finally {
			reader.close();
		}
		Assert.notNull(exception);
		Assert.isTrue(exception instanceof DataImportException);
		
		try {
			reader.open(geneInfoPath);
			EntrezGene gene = reader.readRecord();
			while (gene != null) {
				genes.add(gene);
				gene = reader.readRecord();
			}
		} finally {
			reader.close();
		}
		Assert.notEmpty(genes);
		Assert.isTrue(genes.size() == 5);
		Assert.isTrue(genes.get(4).getEntrezGeneId().equals(10L));
	}
	
	@Test
	public void validationTest() throws Exception {
		EntrezGene gene = new EntrezGene();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(gene, gene.getClass().getName());
		validator.validate(gene, bindingResult);
		if (bindingResult.hasErrors()){
			for (ObjectError error: bindingResult.getAllErrors()){
				System.out.println(error.toString());
			}
		} else {
			fail("Validation did not catch missing field.");
		}
	}
	
	@Test
	public void recordWriterTest() throws Exception {
		testRepository.deleteAll();
		Assert.isTrue(testRepository.count() == 0);
		RepositoryRecordWriter<EntrezGene> writer = new RepositoryRecordWriter<>(testRepository);
		writer.doBefore("");
		for (EntrezGene gene: EntrezGene.createDummyData()){
			writer.writeRecord(gene);
		}
		writer.doAfter();
		Assert.isTrue(testRepository.count() == 5);
	}
	
	@Test
	public void recordUpdaterTest() throws Exception {
		RepositoryRecordWriter<EntrezGene> updater = new RepositoryRecordWriter<>(testRepository,
				RepositoryRecordWriter.WriteMode.UPDATE);
		EntrezGene gene = testRepository.findOne(1L);
		Assert.isTrue("GeneA".equals(gene.getPrimaryGeneSymbol()));
		gene.setPrimaryGeneSymbol("GeneX");
		updater.doBefore("");
		updater.writeRecord(gene);
		updater.doAfter();
		gene = testRepository.findOne(1L);
		Assert.isTrue("GeneX".equals(gene.getPrimaryGeneSymbol()));
	}
	
	@Test
	public void recordProcessorTest() throws Exception {
		testRepository.deleteAll();
		System.out.println(String.format("There are %d records in the test repository.", testRepository.count()));
		Assert.isTrue(testRepository.count() == 0);
		processor.setImportOptions(defaultImportOptions);
		processor.doBefore();
		processor.run(geneInfoPath);
		processor.doAfter();
		Assert.isTrue(testRepository.count() == 5);
		EntrezGene gene = testRepository.findOne(1L);
		Assert.notNull(gene);
		Assert.isTrue(gene.getId() == 1L);
		System.out.println(String.format("There are %d records in the test repository.", testRepository.count()));
		System.out.println(gene.toString());
		Assert.notNull(processor.getModel());
		Assert.isTrue(EntrezGene.class.equals(processor.getModel()));
	}
	
	@Test
	public void recordCollectionReaderTest() throws Exception {
		RecordCollectionReader<EntrezGene> reader = new RecordCollectionReader<>(EntrezGene.createDummyData().subList(0, 1));
		EntrezGene gene = reader.readRecord();
		Assert.notNull(gene);
		gene = reader.readRecord();
		Assert.isNull(gene);
	}
	
	@Test
	public void recordProcessorConfigurationTest() throws Exception {
		Assert.notNull(processor);
		Assert.notNull(processor.getModel());
		Assert.isTrue(EntrezGene.class.equals(processor.getModel()));
		Assert.notNull(processor.getReader());
		Assert.isTrue(processor.getReader() instanceof GeneInfoReader);
		Assert.notNull(processor.getValidator());
		Assert.isTrue(processor.getValidator() instanceof EntrezGeneValidator);
		Assert.notNull(processor.getWriter());
		Assert.isTrue(processor.getWriter() instanceof RepositoryRecordWriter);
		Assert.isNull(processor.getImporter());
		Assert.notNull(processor.getImportOptions());
		Assert.isTrue(processor.getImportOptions() instanceof BasicImportOptions);
		Assert.notNull(processor.getSupportedDataTypes());
		Assert.isTrue(processor.isSupportedDataType("gene_info"));
	}
	
	
	
}
