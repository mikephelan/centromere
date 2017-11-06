/*
 * Copyright 2016 the original author or authors
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

package com.blueprint.centromere.tests.core.test.repository;

import com.blueprint.centromere.core.commons.model.DataSet;
import com.blueprint.centromere.core.commons.model.Gene;
import com.blueprint.centromere.core.commons.model.Sample;
import com.blueprint.centromere.core.commons.repository.DataFileRepository;
import com.blueprint.centromere.core.commons.repository.DataSetRepository;
import com.blueprint.centromere.core.commons.repository.GeneRepository;
import com.blueprint.centromere.core.commons.repository.SampleRepository;
import com.blueprint.centromere.core.config.CoreConfiguration;
import com.blueprint.centromere.tests.core.AbstractRepositoryTests;
import com.blueprint.centromere.tests.core.MongoDataSourceConfig;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author woemler
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
    MongoDataSourceConfig.class,
    CoreConfiguration.CommonConfiguration.class,
    CoreConfiguration.DefaultModelConfiguration.class
})
public class CommonsModelRepositoryTests extends AbstractRepositoryTests {

  @Autowired private GeneRepository geneRepository;
  @Autowired private SampleRepository sampleRepository;
  @Autowired private DataSetRepository dataSetRepository;
  @Autowired private DataFileRepository dataFileRepository;
  
  @Test
  public void findUniqueGeneTest(){
    
    Optional<Gene> optional = geneRepository.findByPrimaryReferenceId("1");
    Assert.notNull(optional, "Optional must not be null");
    Assert.isTrue(optional.isPresent(), "Object must be present");
    Gene gene = optional.get();
    Assert.notNull(gene, "Object must not be null");
    Assert.isTrue("1".equals(gene.getPrimaryReferenceId()), "Primary ID must be '1'");
    
    optional = geneRepository.findByPrimaryReferenceId("1000");
    Assert.notNull(optional, "Optional must not be null");
    Assert.isTrue(!optional.isPresent(), "Object must not be present");
    
  }

  @Test
  public void findUniqueDataSetTest(){

    Optional<DataSet> optional = dataSetRepository.findBySlug("DataSetA");
    Assert.notNull(optional, "Optional must not be null");
    Assert.isTrue(optional.isPresent(), "Object must be present");
    DataSet dataSet = optional.get();
    Assert.notNull(dataSet, "Object must not be null");
    Assert.isTrue("DataSetA".equals(dataSet.getSlug()), "Primary ID must be 'DataSetA'");

    optional = dataSetRepository.findBySlug("DataSetZ");
    Assert.notNull(optional, "Optional must not be null");
    Assert.isTrue(!optional.isPresent(), "Object must not be present");

  }

  @Test
  public void findUniqueSubjectTest(){

    Optional<Sample> optional = sampleRepository.findByName("SampleA");
    Assert.notNull(optional, "Optional must not be null");
    Assert.isTrue(optional.isPresent(), "Object must be present");
    Sample sample = optional.get();
    Assert.notNull(sample, "Object must not be null");
    Assert.isTrue("SampleA".equals(sample.getName()), "Primary ID must be 'SampleA'");

    optional = sampleRepository.findByName("SampleZ");
    Assert.notNull(optional, "Optional must not be null");
    Assert.isTrue(!optional.isPresent(), "Object must not be present");

  }

}
