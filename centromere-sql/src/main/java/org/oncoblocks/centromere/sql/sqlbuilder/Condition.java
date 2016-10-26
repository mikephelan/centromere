/*
 * Copyright 2015 William Oemler, Blueprint Medicines
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

package org.oncoblocks.centromere.sql.sqlbuilder;

import com.blueprint.centromere.core.repository.Evaluation;
import com.blueprint.centromere.core.repository.QueryCriteria;

/**
 * Maps a {@link QueryCriteria} to SQL operations.
 * 
 * @author woemler 
 */
public class Condition {
	private final String clause;
	private final Object value;

	public Condition(String column, Object value, Evaluation evalutation){
		StringBuilder builder = new StringBuilder(" " + column);
		switch (evalutation) {
			case EQUALS:
				builder.append(" = ? ");
				break;
			case NOT_EQUALS:
				builder.append(" != ? ");
				break;
			case IS_NULL:
				builder.append(" is null ");
				break;
			case NOT_NULL:
				builder.append(" is not null ");
				break;
			case IN:
				builder.append(" in (?) ");
				break;
			case NOT_IN:
				builder.append(" not in (?) ");
				break;
			default:
				builder.append(" = ? ");
		}
		this.clause = builder.toString();
		this.value = value;
	}

	public String getClause() {
		return clause;
	}

	public Object getValue() {
		return value;
	}

}
