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


import com.blueprint.centromere.core.model.Model;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

/**
 * @author woemler
 * @since 0.5.0
 */
public interface MetadataOperations<T extends Model<?>> {

  /**
   * Returns all records that could potentially match the input keyword.
   * 
   * @param keyword
   * @return
   */
  List<T> guess(@Param("keyword") String keyword);

  /**
   * Returns at most one record that best matches the input keyword.
   * 
   * @param keyword
   * @return
   */
	Optional<T> bestGuess(String keyword);
	
}
