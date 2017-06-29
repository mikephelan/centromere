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

package com.blueprint.centromere.core.dataimport;

/**
 * Ensures that data import components have access to {@link ImportOptions} objects and their embedded
 *   {@link com.blueprint.centromere.core.config.ApplicationOptions}.
 * 
 * @author woemler
 * @since 0.x
 */
public interface ImportOptionsAware {
  ImportOptions getImportOptions();
  void setImportOptions(ImportOptions importOptions);
}
