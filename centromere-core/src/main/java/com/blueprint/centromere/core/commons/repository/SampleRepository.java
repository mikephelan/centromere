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

package com.blueprint.centromere.core.commons.repository;

import com.blueprint.centromere.core.commons.model.Sample;
import com.blueprint.centromere.core.repository.ModelRepository;
import com.blueprint.centromere.core.repository.ModelResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

/**
 * @author woemler
 */
@ModelResource("samples")
public interface SampleRepository extends
		ModelRepository<Sample, String>,
		MetadataOperations<Sample>,
		AttributeOperations<Sample> {

	Optional<Sample> findByName(@Param("name") String name);
	List<Sample> findByAliases(@Param("alias") String alias);
	List<Sample> findBySampleType(@Param("type") String sampleType);
	List<Sample> findByTissue(@Param("tissue") String tissue);
	List<Sample> findByHistology(@Param("histology") String histology);
	List<Sample> findBySpecies(@Param("species") String species);
	
	@Override
	default List<Sample> guess(@Param("keyword") String keyword){
		List<Sample> samples = new ArrayList<>();
		Optional<Sample> optional = this.findByName(keyword);
		if (optional.isPresent()) samples.add(optional.get());
		samples.addAll(this.findByAliases(keyword));
		samples.addAll(findByTissue(keyword));
		samples.addAll(findByHistology(keyword));
		samples.addAll(findBySampleType(keyword));
		return samples;
	}

  @Override
  default Optional<Sample> bestGuess(String keyword){
    Optional<Sample> optional = findByName(keyword);
    if (optional.isPresent()) return optional;
    List<Sample> samples = this.findByAliases(keyword);
    return samples.isEmpty() ? Optional.empty() : Optional.of(samples.get(0));
  }
	
}
