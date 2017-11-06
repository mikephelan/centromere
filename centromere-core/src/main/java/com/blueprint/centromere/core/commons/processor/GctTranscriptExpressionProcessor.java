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

import com.blueprint.centromere.core.commons.model.TranscriptExpression;
import com.blueprint.centromere.core.commons.reader.GctTranscriptExpressionFileReader;
import com.blueprint.centromere.core.commons.repository.GeneRepository;
import com.blueprint.centromere.core.commons.repository.SampleRepository;
import com.blueprint.centromere.core.commons.repository.TranscriptExpressionRepository;
import com.blueprint.centromere.core.commons.validator.TranscriptExpressionValidator;
import com.blueprint.centromere.core.config.DataImportProperties;
import com.blueprint.centromere.core.dataimport.DataTypes;
import com.blueprint.centromere.core.dataimport.processor.GenericRecordProcessor;
import com.blueprint.centromere.core.dataimport.writer.RepositoryRecordWriter;
import com.blueprint.centromere.core.dataimport.writer.RepositoryRecordWriter.WriteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author woemler
 */
@DataTypes(value = { "gct_transcript_expression" }, description = "Transcript expression data from GCT files")
@Component
public class GctTranscriptExpressionProcessor extends GenericRecordProcessor<TranscriptExpression> {

  @SuppressWarnings("SpringJavaAutowiringInspection")
  @Autowired
  public GctTranscriptExpressionProcessor(
      GeneRepository geneRepository, 
      TranscriptExpressionRepository repository,
      SampleRepository sampleRepository,
      DataImportProperties dataImportProperties
  ) {
    this.setModel(TranscriptExpression.class);
    this.setReader(new GctTranscriptExpressionFileReader(geneRepository, sampleRepository, dataImportProperties));
    this.setValidator(new TranscriptExpressionValidator());
    this.setWriter(new RepositoryRecordWriter<>(repository, WriteMode.INSERT, 200));
  }
}
