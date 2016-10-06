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

package org.oncoblocks.centromere.core.commons.readers;

import org.oncoblocks.centromere.core.commons.models.DataFile;
import org.oncoblocks.centromere.core.commons.models.Gene;
import org.oncoblocks.centromere.core.commons.models.GeneExpression;
import org.oncoblocks.centromere.core.commons.models.Sample;
import org.oncoblocks.centromere.core.commons.repositories.GeneRepository;
import org.oncoblocks.centromere.core.commons.repositories.SampleRepository;
import org.oncoblocks.centromere.core.commons.support.DataFileAware;
import org.oncoblocks.centromere.core.commons.support.SampleAware;
import org.oncoblocks.centromere.core.dataimport.*;
import org.oncoblocks.centromere.core.model.ModelSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads normalized gene expression data from GCT files (http://software.broadinstitute.org/cancer/software/genepattern/file-formats-guide#GCT).
 * 
 * @author woemler
 * @since 0.4.3
 */
public class GctGeneExpressionFileReader<T extends GeneExpression<?>> 
		extends MultiRecordLineFileReader<T> 
		implements InitializingBean, ImportOptionsAware, ModelSupport<T>, DataFileAware, SampleAware {

	private SampleRepository sampleRepository;
	private GeneRepository geneRepository;
	private BasicImportOptions options;
	private DataFile dataFile;
	private Map<String, Sample> sampleMap;
	private Class<T> model;
	
	private static final Logger logger = LoggerFactory.getLogger(GctGeneExpressionFileReader.class);
	
	@PostConstruct
	public void afterPropertiesSet(){
		Assert.notNull(sampleRepository, "SampleRepository must not be null.");
		Assert.notNull(geneRepository, "GeneRepository must not be null.");
		Assert.notNull(dataFile, "DataFile cannot be null.");
		Assert.notNull(dataFile.getId(), "DataFile ID cannot be null.");
		sampleMap = new HashMap<>();
	}

	@Override 
	public void doBefore(Object... args) throws DataImportException {
		super.doBefore(args);
		afterPropertiesSet();
	}

	@Override 
	protected List<T> getRecordsFromLine(String line) throws DataImportException {
		List<T> records = new ArrayList<>();
		String[] bits = line.trim().split(this.getDelimiter());
		if (bits.length > 1){
			Gene gene = getGene(line);
			if (gene == null){
				if (options.isSkipInvalidGenes()){
					logger.warn(String.format("Skipping line due to invalid gene: %s", line));
					return new ArrayList<>();
				} else {
					throw new DataImportException(String.format("Invalid gene in line: %s", line));
				}
			}
			for (int i = 2; i < bits.length; i++){
				T record;
				try {
					record = this.getModel().newInstance();
				} catch (Exception e){
					throw new DataImportException(String.format("Unable to create instance of model object: %s"
							, model.getName()));
				}
				Sample sample = null;
				if (sampleMap.containsKey(this.getHeaders().get(i))){
					sample = sampleMap.get(this.getHeaders().get(i));
				} else {
					List<Sample> samples = sampleRepository.guessSample(this.getHeaders().get(i));
					if (samples != null && !samples.isEmpty()){
						sample = samples.get(0);
						sampleMap.put(this.getHeaders().get(i), sample);
					}
				}
				if (sample == null){
					if (options.isSkipInvalidSamples()){
						logger.warn(String.format("Skipping record due to invalid sample: %s", 
								this.getHeaders().get(i)));
						continue;
					} else {
						throw new DataImportException(String.format("Invalid sample: %s", this.getHeaders().get(i)));
					}
				}
				try {
					record.setValue(Double.parseDouble(bits[i]));
				} catch (NumberFormatException e){
					if (options.isSkipInvalidRecords()){
						logger.warn(String.format("Invalid record, cannot parse value: %s", bits[i]));
						continue;
					} else {
						throw new DataImportException(String.format("Cannot parse value: %s", bits[i]));
					}
				}
				record.setDataFileMetadata(dataFile);
				record.setGeneMetadata(gene);
				record.setSampleMetadata(sample);
				records.add(record);
			}
			
		}
		return records;
	}

	private Gene getGene(String line){
		Gene gene = null;
		String[] b = line.split(getDelimiter());
		if (b.length > 1){
			List<Gene> genes = null;
			if (!b[0].equals("")){
				genes = geneRepository.guessGene(b[0]);
			}
			if (genes == null || genes.isEmpty()){
				genes = geneRepository.guessGene(b[1]);
			}
			if (genes.size() > 0){
				gene = genes.get(0);
			}
		}
		return gene;
	}

	@Override 
	protected boolean isSkippableLine(String line) {
		return false;
	}

	@Autowired
	public void setSampleRepository(SampleRepository sampleRepository) {
		this.sampleRepository = sampleRepository;
	}

	@Autowired
	public void setGeneRepository(GeneRepository geneRepository) {
		this.geneRepository = geneRepository;
	}

	public BasicImportOptions getImportOptions() {
		return options;
	}

	public void setImportOptions(ImportOptions options) {
		this.options = (BasicImportOptions) options;
	}

	public DataFile getDataFile() {
		return dataFile;
	}

	public void setDataFile(DataFile dataFile) {
		this.dataFile = dataFile;
	}

	@Override 
	public Class<T> getModel() {
		return model;
	}

	@Override 
	public void setModel(Class<T> model) {
		this.model = model;
	}

	@Override 
	public List<Sample> getSamples() {
		return new ArrayList<>(sampleMap.values());
	}

}