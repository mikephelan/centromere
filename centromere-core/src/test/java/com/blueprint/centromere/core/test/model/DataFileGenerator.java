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

package com.blueprint.centromere.core.test.model;

import com.blueprint.centromere.core.commons.models.DataFile;
import com.blueprint.centromere.core.commons.models.DataSet;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author woemler
 */
public class DataFileGenerator {

	public static List<DataFile> generateData(List<DataSet> dataSets) throws Exception {

		Assert.isTrue(dataSets.size() > 1);

		List<DataFile> dataFiles = new ArrayList<>();
		
		DataFile dataFile = new DataFile();
		dataFile.setFilePath("/path/to/fileA");
		dataFile.setDataType("GCT RNA-Seq gene expression");
		dataFile.setDateCreated(new Date());
		dataFile.setDateUpdated(new Date());
		dataFile.setDataSet(dataSets.get(0));
		dataFiles.add(dataFile);

		dataFile = new DataFile();
		dataFile.setFilePath("/path/to/fileB");
		dataFile.setDataType("GCT RNA-Seq transcript expression");
		dataFile.setDateCreated(new Date());
		dataFile.setDateUpdated(new Date());
		dataFile.setDataSet(dataSets.get(0));
		dataFiles.add(dataFile);

		dataFile = new DataFile();
		dataFile.setFilePath("/path/to/fileC");
		dataFile.setDataType("MAF mutations");
		dataFile.setDateCreated(new Date());
		dataFile.setDateUpdated(new Date());
		dataFile.setDataSet(dataSets.get(0));
		dataFiles.add(dataFile);

		dataFile = new DataFile();
		dataFile.setFilePath("/path/to/fileD");
		dataFile.setDataType("Gene copy number");
		dataFile.setDateCreated(new Date());
		dataFile.setDateUpdated(new Date());
		dataFile.setDataSet(dataSets.get(1));
		dataFiles.add(dataFile);

		dataFile = new DataFile();
		dataFile.setFilePath("/path/to/fileE");
		dataFile.setDataType("Segment copy number");
		dataFile.setDateCreated(new Date());
		dataFile.setDateUpdated(new Date());
		dataFile.setDataSet(dataSets.get(1));
		dataFiles.add(dataFile);
		
		return dataFiles;
	}
}
