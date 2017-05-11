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

package com.blueprint.centromere.cli.arguments;

import com.beust.jcommander.Parameter;

/**
 * @author woemler
 * @since 0.5.0
 */
public class ImportFileCommandArguments {
	
	@Parameter(names = { "-f", "--file" }, required = true, description = "Input file path.  Required")
	private String filePath;

	@Parameter(names = { "-t", "--type" }, required = true, description = "Data type label for the target file.  Required.")
	private String dataType;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	@Override 
	public String toString() {
		return "ImportFileCommandArguments{" +
				"filePath='" + filePath + '\'' +
				", dataType='" + dataType + '\'' +
				'}';
	}
}
