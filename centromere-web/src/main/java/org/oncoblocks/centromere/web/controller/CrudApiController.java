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

package org.oncoblocks.centromere.web.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.blueprint.centromere.core.model.Model;
import com.blueprint.centromere.core.repository.RepositoryOperations;
import org.oncoblocks.centromere.web.exceptions.RequestFailureException;
import org.oncoblocks.centromere.web.exceptions.ResourceNotFoundException;
import org.oncoblocks.centromere.web.exceptions.RestError;
import org.oncoblocks.centromere.web.util.ApiMediaTypes;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Extension of {@link org.oncoblocks.centromere.web.controller.AbstractApiController} that allows for 
 *   PUT, POST, and DELETE operations.
 * 
 * @author woemler
 */
public class CrudApiController<T extends Model<ID>, ID extends Serializable> 
		extends AbstractApiController<T, ID> {
	
	public CrudApiController(
			RepositoryOperations<T, ID> repository, 
			Class<T> model,
			ResourceAssemblerSupport<T, FilterableResource> assembler
	) {
		super(repository, model, assembler);
	}

	public CrudApiController(
			RepositoryOperations<T, ID> repository, 
			EntityLinks entityLinks
	) {
		super(repository, entityLinks);
	}

	/**
	 * {@code POST /}
	 * Attempts to create a new record using the submitted entity. Throws an exception if the
	 *   entity already exists.
	 *
	 * @param entity entity representation to be persisted
	 * @return updated representation of the submitted entity
	 */
	@ApiResponses({
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 401, message = "Unauthorized", response = RestError.class),
			@ApiResponse(code = 406, message = "Malformed entity", response = RestError.class)
	})
	@RequestMapping(
			value = "", 
			method = RequestMethod.POST, 
			produces = { MediaType.APPLICATION_JSON_VALUE, ApiMediaTypes.APPLICATION_HAL_JSON_VALUE,
					ApiMediaTypes.APPLICATION_HAL_XML_VALUE, MediaType.APPLICATION_XML_VALUE,
					MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> create(
			@ApiParam(name = "entity", value = "Model record entity.") @RequestBody T entity, 
			HttpServletRequest request
	) {
		T created = getRepository().insert(entity);
		if (created == null) throw new RequestFailureException(40003, "There was a problem creating the record.", "", "");
		if (ApiMediaTypes.isHalMediaType(request.getHeader("Accept"))){
			FilterableResource resource = getAssembler().toResource(created);
			return new ResponseEntity<>(resource, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(created, HttpStatus.CREATED);
		}
	}

	/**
	 * {@code PUT /{id}}
	 * Attempts to update an existing entity record, replacing it with the submitted entity. Throws
	 *   an exception if the target entity does not exist.
	 *
	 * @param entity entity representation to update.
	 * @param id primary ID of the target entity
	 * @return updated representation of the submitted entity.
	 */
	@ApiResponses({
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 401, message = "Unauthorized", response = RestError.class),
			@ApiResponse(code	= 404, message = "Record not found", response = RestError.class),
			@ApiResponse(code = 406, message = "Malformed entity", response = RestError.class)
	})
	@RequestMapping(
			value = "/{id}", 
			method = RequestMethod.PUT,
			produces = { MediaType.APPLICATION_JSON_VALUE, ApiMediaTypes.APPLICATION_HAL_JSON_VALUE,
					ApiMediaTypes.APPLICATION_HAL_XML_VALUE, MediaType.APPLICATION_XML_VALUE,
					MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> update(
			@ApiParam(name = "entity", value = "Model record entity.") @RequestBody T entity,
			@ApiParam(name = "id", value = "Model record primary id.") @PathVariable ID id, 
			HttpServletRequest request) {
		if (!getRepository().exists(id)) throw new ResourceNotFoundException();
		T updated = getRepository().update(entity);
		if (updated == null) throw new RequestFailureException(40004, "There was a problem updating the record.", "", "");
		if (ApiMediaTypes.isHalMediaType(request.getHeader("Accept"))){
			FilterableResource resource = getAssembler().toResource(updated);
			return new ResponseEntity<>(resource, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(updated, HttpStatus.CREATED);
		}
	}

	/**
	 * {@code DELETE /{id}}
	 * Attempts to delete the an entity identified by the submitted primary ID.
	 *
	 * @param id primary ID of the target record.
	 * @return {@link HttpStatus} indicating success or failure.
	 */
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 401, message = "Unauthorized", response = RestError.class),
			@ApiResponse(code	= 404, message = "Record not found", response = RestError.class)
	})
	@RequestMapping(
			value = "/{id}", 
			method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(
			@ApiParam(name = "id", value = "Model record primary id.") @PathVariable ID id, 
			HttpServletRequest request) {
		getRepository().delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
