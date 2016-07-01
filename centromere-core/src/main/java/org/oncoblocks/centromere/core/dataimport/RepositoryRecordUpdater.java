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

import org.oncoblocks.centromere.core.model.Model;
import org.oncoblocks.centromere.core.repository.RepositoryOperations;

import java.io.Serializable;

/**
 * Simple implementation of {@link RecordWriter}, that updates existing database records
 *   using a {@link RepositoryOperations} implementation.
 *   
 * 
 * @author woemler
 */
@Deprecated
public class RepositoryRecordUpdater<T extends Model<ID>, ID extends Serializable> implements RecordWriter<T> {
	
	private final RepositoryOperations<T, ID> repository;

	public RepositoryRecordUpdater(RepositoryOperations<T, ID> repository) {
		this.repository = repository;
	}

	/**
	 * Writes the input {@link Model} record to the target {@link RepositoryOperations} implementation,
	 *   using an update operation.
 	 * @param entity
	 */
	public void writeRecord(T entity) {
		repository.update(entity);	
	}

	public RepositoryOperations<T, ID> getRepository() {
		return repository;
	}

	/**
	 * To be executed before the main component method is first called.  Can be configured to handle
	 * a variety of tasks using flexible input parameters.
	 *
	 * @param args an array of objects of any type.
	 * @throws DataImportException
	 */
	@Override 
	public void doBefore(Object... args) throws DataImportException {
		
	}

	/**
	 * To be executed after the main component method is called for the last time.  Can be configured
	 * to handle a variety of tasks using flexible input parameters.
	 *
	 * @param args an array of objects of any type.
	 * @throws DataImportException
	 */
	@Override 
	public void doAfter(Object... args) throws DataImportException {

	}
}
