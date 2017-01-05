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

package com.blueprint.centromere.core.test.cli;

import com.blueprint.centromere.core.commons.models.Gene;
import com.blueprint.centromere.core.commons.models.Sample;
import com.blueprint.centromere.core.commons.repositories.GeneExpressionRepository;
import com.blueprint.centromere.core.commons.repositories.GeneRepository;
import com.blueprint.centromere.core.config.Profiles;
import com.blueprint.centromere.core.dataimport.cli.CommandLineInputConfiguration;
import com.blueprint.centromere.core.dataimport.cli.CommandLineInputExecutor;
import com.blueprint.centromere.core.dataimport.cli.ModelProcessorBeanRegistry;
import com.blueprint.centromere.core.test.jpa.EmbeddedH2DataSourceConfig;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author woemler
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { 
		EmbeddedH2DataSourceConfig.class,
		CommandLineInputConfiguration.class,
		CommandLineTestConfig.class
})
@ActiveProfiles({Profiles.CLI_PROFILE})
@FixMethodOrder
public class CommandLineExecutorTests {
	
	@Autowired private ModelProcessorBeanRegistry registry;
	@Autowired private CommandLineInputExecutor executor;
	@Autowired private GeneRepository geneRepository;
	@Autowired private GeneExpressionRepository geneExpressionRepository;
	
	@Before
	public void setup(){
		geneExpressionRepository.deleteAll();
		geneRepository.deleteAll();
	}
	
	@Test
	public void modelProcessorRegistryTest(){
		Assert.notNull(registry);
		Assert.isTrue(registry.isSupportedDataType("gene_info"));
		Assert.isTrue(registry.isSupportedModel(Gene.class));
		Assert.isTrue(!registry.isSupportedDataType("samples"));
		Assert.isTrue(!registry.isSupportedModel(Sample.class));
	}
	
	@Test
	public void fileImportTest() throws Exception {
		Assert.isTrue(geneRepository.count() == 0);
		Resource file = new ClassPathResource("Homo_sapiens.gene_info");
		String[] args = { "import", "file", "-f", file.getFile().getAbsolutePath(), "-t", "gene_info" };
		executor.run(args);
		Assert.isTrue(geneRepository.count() == 5);
	}

	@Test
	public void badDataTypeImportTest() throws Exception {
		Assert.isTrue(geneRepository.count() == 0);
		Resource file = new ClassPathResource("Homo_sapiens.gene_info");
		String[] args = { "import", "file", "-f", file.getFile().getAbsolutePath(), "-t", "genes" };
		executor.run(args);
		Assert.isTrue(geneRepository.count() == 0);
	}
	
}
