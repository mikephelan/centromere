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

package org.oncoblocks.centromere.mongodb.test.springdata;

import org.oncoblocks.centromere.mongodb.test.EntrezGene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author woemler
 */
public class CentromereEntrezGeneRepositoryImpl implements ExtendedGeneRepository {
	
	private final MongoOperations mongoOperations;

	@Autowired
	public CentromereEntrezGeneRepositoryImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public List<EntrezGene> guessGene(String keyword) {
		Query query = new Query(Criteria.where("primaryGeneSymbol").is(keyword));
		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "entrezGeneId"));
		List<EntrezGene> genes = mongoOperations.find(query.with(sort), EntrezGene.class);
		if (genes != null && genes.size() > 0) return genes;
		query = new Query(Criteria.where("aliases").is(keyword));
		genes = mongoOperations.find(query.with(sort), EntrezGene.class);
		if (genes != null && genes.size() > 0) return genes;
		return null;
	}
	
}
