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

package com.blueprint.centromere.core.commons.reader;

import com.blueprint.centromere.core.commons.model.GeneExpression;
import com.blueprint.centromere.core.commons.repository.GeneRepository;
import com.blueprint.centromere.core.commons.support.DataSetSupport;
import com.blueprint.centromere.core.config.DataImportProperties;

/**
 * Reads normalized gene expression data from GCT files
 *   (http://software.broadinstitute.org/cancer/software/genepattern/file-formats-guide#GCT).
 * 
 * @author woemler
 * @since 0.5.0
 */
public class GctGeneExpressionFileReader extends GctFileReader<GeneExpression>  {

  public GctGeneExpressionFileReader(
      GeneRepository geneRepository,
      DataSetSupport dataSetSupport,
      DataImportProperties dataImportProperties) {
    super(GeneExpression.class, geneRepository, dataSetSupport, dataImportProperties);
  }

}
