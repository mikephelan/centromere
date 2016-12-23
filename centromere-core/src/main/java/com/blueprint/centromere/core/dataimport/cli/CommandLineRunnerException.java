/*
 * Copyright 2016 the original author or authors
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

package com.blueprint.centromere.core.dataimport.cli;

/**
 * Generic runtime exception for command line runner classes.
 * 
 * @author woemler
 */
public class CommandLineRunnerException extends RuntimeException {

	public CommandLineRunnerException() {
	}

	public CommandLineRunnerException(String message) {
		super(message);
	}

	public CommandLineRunnerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandLineRunnerException(Throwable cause) {
		super(cause);
	}

	public CommandLineRunnerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
