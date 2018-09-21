/*
 * Copyright 2018 the original author or authors
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

package com.blueprint.centromere.tests.core.repositories;

import com.blueprint.centromere.core.repository.ModelRepository;
import com.blueprint.centromere.tests.core.models.DataSet;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author woemler
 */
@NoRepositoryBean
public interface DataSetRepository<T extends DataSet<ID>, ID extends Serializable> extends ModelRepository<T, ID> {
	Optional<T> findByName(String name);
	List<T> findBySource(String source);
	List<T> findBySampleIds(String sampleId);
  List<T> findByDataFileIds(String dataFileId);
}
