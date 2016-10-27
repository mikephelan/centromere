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

package com.blueprint.centromere.core.commons.repositories;

import com.blueprint.centromere.core.commons.models.Sample;
import com.blueprint.centromere.core.repository.BaseRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author woemler
 */
@RepositoryRestResource(path = "samples", collectionResourceRel = "samples")
public interface SampleRepository extends 
		BaseRepository<Sample, Long>, 
		MetadataOperations<Sample, Long>,
		AttributeOperations<Sample> {
	
	List<Sample> findByName(@Param("name") String name);
	List<Sample> findBySampleType(@Param("type") String sampleType);
	List<Sample> findByTissue(@Param("tissue") String tissue);
	List<Sample> findByHistology(@Param("histology") String histology);
	List<Sample> findBySubjectId(@Param("subjectId") Long subjectId);
	
	@Override
	default List<Sample> guess(@Param("keyword") String keyword){
		List<Sample> samples = new ArrayList<>();
		samples.addAll(findByName(keyword));
		samples.addAll(findByTissue(keyword));
		samples.addAll(findByHistology(keyword));
		samples.addAll(findBySampleType(keyword));
		return samples;
	}
	
}
