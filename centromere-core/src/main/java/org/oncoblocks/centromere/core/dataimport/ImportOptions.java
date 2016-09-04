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

package org.oncoblocks.centromere.core.dataimport;

import java.util.Map;

/**
 * Simple interface for allowing the passing of key-value string parameters between data import 
 *   components.
 * 
 * @author woemler
 */
public interface ImportOptions {

	/**
	 * Returns the string value of the given option, if it exists.
	 * 
	 * @param name Key name of the option.
	 * @return String value of the option.
	 */
	String getOption(String name);

	/**
	 * Returns all of the key-value option pairs in the object.
	 * 
	 * @return {@link Map} of string options.
	 */
	Map<String, String> getOptions();

	/**
	 * Tests whether the option is present in the object's key-value map.
	 * 
	 * @param name Key name of the option.
	 * @return true if the option exists, false otherwise.
	 */
	boolean hasOption(String name);

	/**
	 * Returns true if null or malformed sample records should be skipped, rather than raising an
	 *   exception.
	 * 
	 * @return boolean flag
	 */
	boolean isSkipInvalidSamples();

	/**
	 * Returns true if null or malformed data set records should be skipped, rather than raising an
	 *   exception.
	 *
	 * @return boolean flag
	 */
	boolean isSkipInvalidDataSets();

	/**
	 * Returns true if null or malformed gene records should be skipped, rather than raising an
	 *   exception.
	 *
	 * @return boolean flag
	 */
	boolean isSkipInvalidGenes();

	/**
	 * Returns true if null or malformed metadata records should be skipped, rather than raising an
	 *   exception.
	 *
	 * @return boolean flag
	 */
	boolean isSkipInvalidMetadata();

	/**
	 * Returns true if null or malformed data records should be skipped, rather than raising an
	 *   exception.
	 *
	 * @return boolean flag
	 */
	boolean isSkipInvalidRecords();
	
	String SKIP_INVALID_SAMPLES = "skipInvalidSamples";
	String SKIP_INVALID_DATA_SETS = "skipInvalidDataSets";
	String SKIP_INVALID_GENES = "skipInvalidGenes";
	String SKIP_INVALID_METADATA = "skipInvalidMetadata";
	String SKIP_INVALID_RECORDS = "skipInvalidRecords";
	String TEMP_DIRECTORY_PATH = "tempDirectoryPath";
	String SKIP_EXISTING_FILES = "skipExistingFiles";
	
}
