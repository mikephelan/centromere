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

package org.oncoblocks.centromere.core.dataimport;

import java.lang.annotation.*;

/**
 * Identifies an annotated {@link RecordProcessor} as the primary handler of the specified data types.
 * @author woemler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataTypes {

	/**
	 * Specifies which data types the annotated processor class supports.
	 * 
	 * @return array of supported data types.
	 */
	String[] value();

	/**
	 * When true, will default the annotated processor to handling record processing for the 
	 *   appropriate {@link org.oncoblocks.centromere.core.model.Model}, when a data type is not
	 *   supplied.
	 * 
	 * @return
	 */
	boolean defaultForModel() default false;
}
