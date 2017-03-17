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

package com.blueprint.centromere.core.repository;

import com.querydsl.core.types.Path;

/**
 * Simple representation of a database query evaluation that can be passed to generic 
 * {@link ModelRepository} implementations.
 * 
 * @author woemler
 */
public class QueryCriteria {

  private String key;
  private Object value;
  private Path path;
  private Evaluation evaluation;
  
  public QueryCriteria(String key, Object value, Path path, Evaluation evaluation) {
    this.key = key;
    this.value = value;
    this.path = path;
    this.evaluation = evaluation;
  }

  public QueryCriteria(String key, Object value, Path path) {
    this.key = key;
    this.value = value;
    this.path = path;
    this.evaluation = Evaluation.EQUALS;
  }

  public QueryCriteria() {
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path path) {
    this.path = path;
  }

  public Evaluation getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(Evaluation evaluation) {
    this.evaluation = evaluation;
  }

  @Override 
  public String toString() {
    return "QueryCriteria{" +
        "key='" + key + '\'' +
        ", value=" + value +
        ", path=" + path.toString() +
        ", evaluation=" + evaluation +
        '}';
  }


  }