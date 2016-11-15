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

import com.google.common.reflect.TypeToken;

import com.blueprint.centromere.core.commons.models.DataFile;
import com.blueprint.centromere.core.commons.models.Sample;
import com.blueprint.centromere.core.model.Model;
import com.blueprint.centromere.core.model.ModelSupport;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author woemler
 * @since 0.5.0
 */
@SuppressWarnings("unchecked")
public interface DataOperations<T extends Model<UUID>> {

	default List<T> findByDataFileId(UUID dataFileId){
		TypeToken<T> type = new TypeToken<T>(getClass()) {};
		Class<T> model = (Class<T>) type.getRawType();
		PathBuilder<T> pathBuilder = new PathBuilder<>(model, model.getSimpleName().toLowerCase());
		Path path = pathBuilder.get("dataFileId");
		Expression constant = Expressions.constant(dataFileId);
		Predicate predicate = Expressions.predicate(Ops.EQ, path, constant);
		return (List<T>) ((QueryDslPredicateExecutor) this).findAll(predicate);
	}

	default List<T> findBySampleId(UUID sampleId){
		TypeToken<T> type = new TypeToken<T>(getClass()) {};
		Class<T> model = (Class<T>) type.getRawType();
		PathBuilder<T> pathBuilder = new PathBuilder<>(model, model.getSimpleName().toLowerCase());
		Path path = pathBuilder.get("sampleId");
		Expression constant = Expressions.constant(sampleId);
		Predicate predicate = Expressions.predicate(Ops.EQ, path, constant);
		return (List<T>) ((QueryDslPredicateExecutor) this).findAll(predicate);
	}

	default List<T> findByGeneId(UUID geneId){
		TypeToken<T> type = new TypeToken<T>(getClass()) {};
		Class<T> model = (Class<T>) type.getRawType();
		PathBuilder<T> pathBuilder = new PathBuilder<>(model, model.getSimpleName().toLowerCase());
		Path path = pathBuilder.get("geneId");
		Expression constant = Expressions.constant(geneId);
		Predicate predicate = Expressions.predicate(Ops.EQ, path, constant);
		return (List<T>) ((QueryDslPredicateExecutor) this).findAll(predicate);
	}
}
