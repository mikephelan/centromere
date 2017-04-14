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

package com.blueprint.centromere.core.dataimport;

/**
 * Generic exception thrown when a data import component has an unresolvable problem.
 * 
 * @author woemler
 */
public class DataImportException extends RuntimeException {

	public DataImportException() {
	}

	public DataImportException(String message) {
		super(message);
	}

	public DataImportException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataImportException(Throwable cause) {
		super(cause);
	}

	public DataImportException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
