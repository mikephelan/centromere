/*
 * Copyright 2017 the original author or authors
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

package com.blueprint.centromere.tests.cli.test;

import com.blueprint.centromere.cli.CommandLineInputExecutor;
import com.blueprint.centromere.cli.ModelProcessorBeanRegistry;
import com.blueprint.centromere.core.commons.model.Gene;
import com.blueprint.centromere.core.commons.model.Sample;
import com.blueprint.centromere.core.commons.repository.DataFileRepository;
import com.blueprint.centromere.core.commons.repository.GeneExpressionRepository;
import com.blueprint.centromere.core.commons.repository.GeneRepository;
import com.blueprint.centromere.core.config.Profiles;
import com.blueprint.centromere.tests.cli.CommandLineTestInitializer;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author woemler
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommandLineTestInitializer.class, webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ Profiles.CLI_PROFILE })
@FixMethodOrder
public class CommandLineExecutorTests {
	
	@Autowired private ModelProcessorBeanRegistry registry;
	@Autowired private CommandLineInputExecutor executor;
	@Autowired private GeneRepository geneRepository;
	@Autowired private GeneExpressionRepository geneExpressionRepository;
	@Autowired private DataFileRepository dataFileRepository;
	
	@Before
	public void setup(){
	  dataFileRepository.deleteAll();
		geneExpressionRepository.deleteAll();
		geneRepository.deleteAll();
	}
	
	@Test
	public void modelProcessorRegistryTest(){
		Assert.notNull(registry);
		Assert.isTrue(registry.isSupportedDataType("entrez_gene"));
		Assert.isTrue(registry.isSupportedModel(Gene.class));
		Assert.isTrue(!registry.isSupportedDataType("samples"));
    Assert.isTrue(registry.isSupportedDataType("generic_samples"));
		Assert.isTrue(registry.isSupportedModel(Sample.class));
	}
	
	@Test
	public void fileImportTest() throws Exception {
		Assert.isTrue(geneRepository.count() == 0);
		Resource file = new ClassPathResource("samples/Homo_sapiens.gene_info");
		String[] args = { "import", "file", "-f", file.getFile().getAbsolutePath(), "-t", "entrez_gene", "--centromere.import.dataset.short-name=test" };
		executor.run(new DefaultApplicationArguments(args));
		Assert.isTrue(geneRepository.count() == 5, String.format("Expected 5 records, found %d", geneRepository.count()));
	}

	@Test
	public void badDataTypeImportTest() throws Exception {
		Assert.isTrue(geneRepository.count() == 0);
		Resource file = new ClassPathResource("samples/Homo_sapiens.gene_info");
		String[] args = { "import", "file", "-f", file.getFile().getAbsolutePath(), "-t", "genes" };
		Exception exception = null;
		try {
      executor.run(new DefaultApplicationArguments(args));
    } catch (Exception e){
		  exception = e;
    }
    Assert.notNull(exception, "Exception not thrown.");
		Assert.isTrue(geneRepository.count() == 0, "No records should be present.");
	}

  @Test
  public void missingFileTest() throws Exception {
    Assert.isTrue(geneRepository.count() == 0);
    String[] args = { "import", "file", "-f", "/path/to/bad/file", "-t", "genes" };
    Exception exception = null;
    try {
      executor.run(new DefaultApplicationArguments(args));
    } catch (Exception e){
      exception = e;
    }
    Assert.notNull(exception, "Exception not thrown.");
    Assert.isTrue(geneRepository.count() == 0, "No records should be present.");
  }

  @Test
  public void missingCommandTest() throws Exception {
    Assert.isTrue(geneRepository.count() == 0);
    String[] args = { "import", "-f", "/path/to/bad/file", "-t", "genes" };
    Exception exception = null;
    try {
      executor.run(new DefaultApplicationArguments(args));
    } catch (Exception e){
      exception = e;
    }
    Assert.isTrue(exception == null, "Exception should still be null");
    Assert.isTrue(geneRepository.count() == 0, "No records should be present.");
  }
  
  @Test
  public void fileOverwritingTest() throws Exception {
    Resource file = new ClassPathResource("samples/Homo_sapiens.gene_info");
    HashCode hash = Files.hash(file.getFile(), Hashing.md5());
    System.out.println(hash.toString());
  }


}
