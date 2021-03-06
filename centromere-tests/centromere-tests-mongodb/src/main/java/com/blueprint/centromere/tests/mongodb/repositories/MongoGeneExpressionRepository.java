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

package com.blueprint.centromere.tests.mongodb.repositories;

import com.blueprint.centromere.core.repository.ModelResource;
import com.blueprint.centromere.tests.core.repositories.GeneExpressionRepository;
import com.blueprint.centromere.tests.mongodb.models.MongoGeneExpression;

/**
 * @author woemler
 */
@ModelResource("geneexpression")
public interface MongoGeneExpressionRepository extends
    GeneExpressionRepository<MongoGeneExpression, String> {
  
}
