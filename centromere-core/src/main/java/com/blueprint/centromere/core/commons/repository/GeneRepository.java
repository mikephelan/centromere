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

import com.blueprint.centromere.core.commons.model.Gene;
import com.blueprint.centromere.core.repository.ModelRepository;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MapPath;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author woemler
 */
@RepositoryRestResource(path = "genes", collectionResourceRel = "genes")
public interface GeneRepository extends
		ModelRepository<Gene, String>,
		MetadataOperations<Gene>,
		AttributeOperations<Gene> {

	Optional<Gene> findByPrimaryReferenceId(@Param("refId") String refId);

	List<Gene> findByPrimaryGeneSymbol(@Param("symbol") String symbol);

	List<Gene> findByAliases(@Param("alias") String alias);

	default List<Gene> findByExternalReference(@Param("source") String source, @Param("value") String value){
		PathBuilder<Gene> pathBuilder = new PathBuilder<>(Gene.class, "gene");
		MapPath<String, String, PathBuilder<String>> mapPath
				= pathBuilder.getMap("externalReferences", String.class, String.class);
		Expression<String> constant = Expressions.constant(value);
		Predicate predicate = Expressions.predicate(Ops.EQ_IGNORE_CASE, mapPath.get(source), constant);
		return (List<Gene>) this.findAll(predicate);
	}

	@Override
	default List<Gene> guess(@Param("keyword") String keyword){

		List<Gene> genes = new ArrayList<>();

		PathBuilder<Gene> pathBuilder = new PathBuilder<>(Gene.class, "gene");
		Expression constant = Expressions.constant(keyword);

		Predicate predicate = Expressions.predicate(Ops.EQ_IGNORE_CASE,
				pathBuilder.getString("primaryReferenceId"), constant);
		genes.addAll((List<Gene>) findAll(predicate));

		predicate = Expressions.predicate(Ops.EQ_IGNORE_CASE,
				pathBuilder.getString("primaryGeneSymbol"), constant);
		genes.addAll((List<Gene>) findAll(predicate));

		predicate = Expressions.predicate(Ops.EQ_IGNORE_CASE,
				pathBuilder.getList("aliases", String.class).any(), constant);
		genes.addAll((List<Gene>) findAll(predicate));

		return genes;

	}

  @Override
  default Optional<Gene> bestGuess(String keyword){

    PathBuilder<Gene> pathBuilder = new PathBuilder<>(Gene.class, "gene");
    Expression constant = Expressions.constant(keyword);

    Predicate predicate = Expressions.predicate(Ops.EQ_IGNORE_CASE,
        pathBuilder.getString("primaryReferenceId"), constant);
    List<Gene> genes = (List<Gene>) findAll(predicate);
    if (!genes.isEmpty()){
      return Optional.of(genes.get(0));
    }

    predicate = Expressions.predicate(Ops.EQ_IGNORE_CASE,
        pathBuilder.getString("primaryGeneSymbol"), constant);
    genes = (List<Gene>) findAll(predicate);
    if (!genes.isEmpty()){
      return Optional.of(genes.get(0));
    }

    predicate = Expressions.predicate(Ops.EQ_IGNORE_CASE,
        pathBuilder.getList("aliases", String.class).any(), constant);
    genes = (List<Gene>) findAll(predicate);
    if (!genes.isEmpty()){
      return Optional.of(genes.get(0));
    }

    return Optional.empty();

  }

}
