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

package org.oncoblocks.centromere.core.input.pipeline;

import java.util.List;

/**
 * @author woemler
 */
public class ImportJob {
	
	private String name;
	private String notes;
	private List<Object> dataSets;
	private List<DataType> dataTypes;
	private List<InputFile> files;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Object> getDataSets() {
		return dataSets;
	}

	public void setDataSets(List<Object> dataSets) {
		this.dataSets = dataSets;
	}

	public List<DataType> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(
			List<DataType> dataTypes) {
		this.dataTypes = dataTypes;
	}

	public List<InputFile> getFiles() {
		return files;
	}

	public void setFiles(
			List<InputFile> files) {
		this.files = files;
	}

}
