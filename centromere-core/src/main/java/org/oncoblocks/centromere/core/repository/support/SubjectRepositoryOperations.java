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

package org.oncoblocks.centromere.core.repository.support;

import org.oncoblocks.centromere.core.model.Model;
import org.oncoblocks.centromere.core.repository.RepositoryOperations;

import java.io.Serializable;
import java.util.List;

/**
 * Required operations for Subject repositories.
 * 
 * @author woemler
 */
public interface SubjectRepositoryOperations<T extends Model<ID>, ID extends Serializable>
		extends RepositoryOperations<T, ID> {
	List<T> findByName(String name);
	List<T> findByDiseaseId(ID diseaseId);
	List<T> findByAlias(String alias);
	List<T> guessSubject(String keyword);
}
