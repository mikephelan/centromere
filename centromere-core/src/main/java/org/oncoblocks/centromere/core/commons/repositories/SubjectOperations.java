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

package org.oncoblocks.centromere.core.commons.repositories;

import org.oncoblocks.centromere.core.commons.models.Sample;
import org.oncoblocks.centromere.core.commons.models.Subject;

import java.io.Serializable;
import java.util.List;

/**
 * @author woemler
 */
public interface SubjectOperations<T extends Subject<ID>, ID extends Serializable>
		extends SimpleAliasOperations<T>, AttributeOperations<T> {
	List<T> guessSubject(String keyword);
	<S extends Sample<I>, I extends Serializable> T findBySampleId(I sampleId); 
}