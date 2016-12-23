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

package com.blueprint.centromere.core.dataimport.cli;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.blueprint.centromere.core.dataimport.BasicImportOptions;
import com.blueprint.centromere.core.dataimport.ImportOptions;
import com.blueprint.centromere.core.dataimport.RecordProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Command line arguments for the {@code import} command.  Includes arguments for specifying the
 *   input file, associated data type and data sets, and flags for modifying import behavior.  
 * 
 * @author woemler
 */
@Deprecated
public class ImportCommandArguments {
	
	@Parameter(names = { "-i", "--input" }, required = true, description = "Data source to be imported.  Required.")
	private String inputFilePath;
	
	@Parameter(names = { "-t", "--data-type" }, required = true, description = "Data type label for the target file.  Required.")
	private String dataType;
	
	@Parameter(names = { "-d", "--data-set" }, description = "Data set label or JSON representation.  If not provided, no data set will be associated with the file.")
	private String dataSet;
	
	@Parameter(names = { "-T", "--temp-dir" }, description = "Directory to write temporary files to.  Defaults to '/tmp'.")
	private String tempFilePath = "/tmp";

	@DynamicParameter(names = "-D", description = "Dynamic key-value parameters. eg -Dname=Joe")
	private Map<String, String> parameters = new HashMap<>();

	@Parameter(names = {"--skip-invalid-records"}, description = "When true, records that fail validation will be skipped, rather than throwing an exception.")
	private boolean skipInvalidRecords = false;

	@Parameter(names = {"--skip-invalid-genes"}, description = "When true, records that do not match a valid gene will be skipped, rather than throw an exception.")
	private boolean skipInvalidGenes = false;

	@Parameter(names = {"--skip-invalid-samples"}, description = "When true, records that do not match valid samples will be skipped, rather than throw an exception.")
	private boolean skipInvalidSamples = false;

	@Parameter(names = {"--skip-invalid-data-sets"}, description = "When true, records and files associated with invalid or existing data sets will be skipped, rather than throw an exception.")
	private boolean skipInvalidDataSets = false;

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataSet() {
		return dataSet;
	}

	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getTempFilePath() {
		return tempFilePath;
	}

	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}

	public boolean isSkipInvalidRecords() {
		return skipInvalidRecords;
	}

	public void setSkipInvalidRecords(boolean skipInvalidRecords) {
		this.skipInvalidRecords = skipInvalidRecords;
	}

	public boolean isSkipInvalidGenes() {
		return skipInvalidGenes;
	}

	public void setSkipInvalidGenes(boolean skipInvalidGenes) {
		this.skipInvalidGenes = skipInvalidGenes;
	}

	public boolean isSkipInvalidSamples() {
		return skipInvalidSamples;
	}

	public void setSkipInvalidSamples(boolean skipInvalidSamples) {
		this.skipInvalidSamples = skipInvalidSamples;
	}

	public boolean isSkipInvalidDataSets() {
		return skipInvalidDataSets;
	}

	public void setSkipInvalidDataSets(boolean skipInvalidDataSets) {
		this.skipInvalidDataSets = skipInvalidDataSets;
	}

	/**
	 * Uses user-inputted and default flag values to create a {@link ImportOptions}
	 *   instance that can be passed to {@link RecordProcessor}
	 *   instances to modify their behavior.
	 * 
	 * @return
	 */
	public BasicImportOptions getImportOptions(){
		BasicImportOptions options = new BasicImportOptions();
		options.setSkipInvalidDataSets(this.skipInvalidDataSets);
		options.setSkipInvalidGenes(this.skipInvalidGenes);
		options.setSkipInvalidRecords(this.skipInvalidRecords);
		options.setSkipInvalidSamples(this.skipInvalidSamples);
		options.setTempDirectoryPath(this.tempFilePath);
		return options;
	}

	@Override 
	public String toString() {
		return "ImportCommandArguments{" +
				"inputFilePath='" + inputFilePath + '\'' +
				", dataType='" + dataType + '\'' +
				", dataSet='" + dataSet + '\'' +
				", tempFilePath='" + tempFilePath + '\'' +
				", skipInvalidRecords=" + skipInvalidRecords +
				", skipInvalidGenes=" + skipInvalidGenes +
				", skipInvalidSamples=" + skipInvalidSamples +
				", skipInvalidDataSets=" + skipInvalidDataSets +
				'}';
	}
}
