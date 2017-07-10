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

package com.blueprint.centromere.core.commons.processor;

import com.blueprint.centromere.core.commons.model.Mutation;
import com.blueprint.centromere.core.commons.reader.SnpEffVcfReader;
import com.blueprint.centromere.core.commons.repository.GeneRepository;
import com.blueprint.centromere.core.commons.repository.MutationRepository;
import com.blueprint.centromere.core.commons.repository.SampleRepository;
import com.blueprint.centromere.core.commons.repository.SubjectRepository;
import com.blueprint.centromere.core.commons.validator.MutationValidator;
import com.blueprint.centromere.core.dataimport.DataTypes;
import com.blueprint.centromere.core.dataimport.processor.GenericRecordProcessor;
import com.blueprint.centromere.core.dataimport.writer.RepositoryRecordWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author woemler
 */
@DataTypes({ "snpeff_vcf" })
@Component
public class SnpEffVcfProcessor extends GenericRecordProcessor<Mutation> {

  @Autowired
  public SnpEffVcfProcessor(GeneRepository geneRepository, SampleRepository sampleRepository, 
      SubjectRepository subjectRepository, MutationRepository mutationRepository) {
    this.setModel(Mutation.class);
    this.setReader(new SnpEffVcfReader(geneRepository, sampleRepository, subjectRepository));
    this.setValidator(new MutationValidator());
    this.setWriter(new RepositoryRecordWriter<>(mutationRepository));
  }
}