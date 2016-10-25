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

package org.oncoblocks.centromere.mongodb.test.dataimport;

import com.mongodb.Mongo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import com.blueprint.centromere.core.dataimport.RepositoryRecordWriter;
import org.oncoblocks.centromere.mongodb.MongoCredentials;
import org.oncoblocks.centromere.mongodb.MongoImportTempFileImporter;
import org.oncoblocks.centromere.mongodb.MongoImportTempFileWriter;
import org.oncoblocks.centromere.mongodb.test.EntrezGene;
import org.oncoblocks.centromere.mongodb.test.config.TestMongoConfig;
import org.oncoblocks.centromere.mongodb.test.basic.EntrezGeneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author woemler
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestMongoConfig.class})
public class MongoImportTests {
	
	@Autowired private EntrezGeneRepository repository;
	@Autowired private MongoTemplate mongoTemplate;
	@Autowired private Mongo mongo;
	@Autowired private Environment env;
	@Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
	private final String geneInfoPath = ClassLoader.getSystemClassLoader()
			.getResource("Homo_sapiens.gene_info").getPath();
	private File tempFile;
	private List<EntrezGene> genes;
	
	@Before
	public void setup() throws Exception {
		tempFile = temporaryFolder.newFile();
		genes = new ArrayList<>();
		GeneInfoReader geneInfoReader = new GeneInfoReader();
		try {
			geneInfoReader.open(geneInfoPath);
			EntrezGene gene = geneInfoReader.readRecord();
			while (gene != null){
				genes.add(gene);
				gene = geneInfoReader.readRecord();
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			geneInfoReader.close();
		}
	}
	
	@Test
	public void repositoryWriterTest() throws Exception {
		repository.deleteAll();
		RepositoryRecordWriter<EntrezGene> writer = new RepositoryRecordWriter<>(repository);
		for (EntrezGene gene: genes){
			writer.writeRecord(gene);
		}
		List<EntrezGene> geneList = repository.findAll();
		Assert.notNull(geneList);
		Assert.notEmpty(geneList);
		Assert.isTrue(geneList.size() == 5);
	}
	
	@Test
	public void tempWriterTest() throws Exception {
		MongoImportTempFileWriter<EntrezGene> geneWriter = new MongoImportTempFileWriter<>(mongoTemplate);
		try {
			geneWriter.open(tempFile.getAbsolutePath());
			for (EntrezGene gene: genes){
				geneWriter.writeRecord(gene);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (geneWriter != null) geneWriter.close();
		}
		Assert.isTrue(tempFile.canRead());
		boolean flag = false;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(tempFile));
			String line = reader.readLine();
			while (line != null) {
				Assert.notNull(line);
				System.out.println(line);
				line = reader.readLine();
			}
			flag = true;
		} finally {
			if (reader != null) reader.close();
		}
		Assert.isTrue(flag);
	}
	
	@Test
	public void mongoImportTest() throws Exception {
		this.tempWriterTest();
		repository.deleteAll();
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(tempFile));
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = bufferedReader.readLine();
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (bufferedReader != null){
				bufferedReader.close();
			}
		}
		String content = stringBuilder.toString();
		Assert.notNull(content);
		System.out.print(content);

		MongoCredentials credentials = new MongoCredentials();
		credentials.setDatabase(env.getRequiredProperty("mongo.name"));
		credentials.setHost(mongo.getAddress().getHost());
		credentials.setPort(String.valueOf(mongo.getAddress().getPort()));
		MongoImportTempFileImporter importer = new MongoImportTempFileImporter(credentials, "genes");
		importer.importFile(tempFile.getAbsolutePath());
		List<EntrezGene> genes = repository.findAll();
		Assert.notNull(genes);
		Assert.notEmpty(genes);
	}
	
}
