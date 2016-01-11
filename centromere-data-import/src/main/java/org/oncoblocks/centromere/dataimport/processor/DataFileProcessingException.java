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

package org.oncoblocks.centromere.dataimport.processor;

import org.oncoblocks.centromere.dataimport.config.DataImportException;

/**
 * Generic exception for data file parsing issues.
 * 
 * @author woemler
 */
public class DataFileProcessingException extends DataImportException {
	public DataFileProcessingException(String message) {
		super(message);
	}
}
