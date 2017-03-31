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

package com.blueprint.centromere.core.dataimport.reader;

import com.blueprint.centromere.core.dataimport.DataImportComponent;
import com.blueprint.centromere.core.dataimport.DataImportException;
import com.blueprint.centromere.core.model.Model;
import com.blueprint.centromere.core.model.ModelSupport;

/**
 * Data impoer component class.  Reads from a data source and returns {@link Model} class instances.
 * 
 * @author woemler
 */
public interface RecordReader<T extends Model<?>> extends DataImportComponent, ModelSupport<T> {

	/**
	 * Generates and returns a single {@link Model} entity from the input data source.
	 * 
	 * @return a single {@link Model} record.
	 * @throws DataImportException
	 */
	T readRecord() throws DataImportException;

}
