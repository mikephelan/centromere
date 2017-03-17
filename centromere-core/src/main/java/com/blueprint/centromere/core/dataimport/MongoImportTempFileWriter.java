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

import com.blueprint.centromere.core.model.Model;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * @author woemler
 */
public class MongoImportTempFileWriter<T extends Model<?>> extends AbstractRecordFileWriter<T> {

  private final MongoOperations mongoOperations;

  public MongoImportTempFileWriter(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  /**
   * Writes a {@link com.blueprint.centromere.core.model.Model} record to a temp file, formatted into JSON.
   *
   * @param record
   * @throws DataImportException
   */
  public void writeRecord(T record) throws DataImportException {
    FileWriter writer = this.getWriter();
    try {
      writer.write(convertEntityToJson(record));
      writer.write("\n");
    } catch (IOException e){
      e.printStackTrace();
      throw new DataImportException(e.getMessage());
    }
  }

  private String convertEntityToJson(Object entity){
    MongoConverter converter = mongoOperations.getConverter();
    DBObject dbObject = new BasicDBObject();
    converter.write(entity, dbObject);
    if (dbObject.containsField("_id") && dbObject.get("_id") == null){
      dbObject.removeField("_id");
    }
    if (dbObject.containsField("_class")){
      dbObject.removeField("_class");
    }
    return dbObject.toString();
  }

}