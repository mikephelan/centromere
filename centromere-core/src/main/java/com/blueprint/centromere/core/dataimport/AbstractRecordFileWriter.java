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

import com.blueprint.centromere.core.model.Model;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Basic abstract implementation of {@link RecordWriter}, for writing records to temp files.  Handles the 
 *   file object opening and closing in the {@code doBefore} and {@code doAfter} methods, respectively.
 * 
 * @author woemler
 */
public abstract class AbstractRecordFileWriter<T extends Model<?>> 
		implements RecordWriter<T>, TempFileWriter {
  
  private static final Logger logger = LoggerFactory.getLogger(AbstractRecordFileWriter.class);
	
	private FileWriter writer;
	private Environment environment;

	/**
	 * Opens a new output file for writing.
	 * 
 	 * @param args
	 * @throws DataImportException
	 */
	@Override
	public void doBefore(Object... args) throws DataImportException {
		try {
			Assert.notEmpty(args, "One or more arguments is required.");
			Assert.isTrue(args[0] instanceof String, "The first argument must be a String.");
			Assert.notNull(environment, "Environment not set.");
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			throw new DataImportException(e.getMessage());
		}
		String tempFilePath = getTempFilePath((String) args[0]);
		this.open(tempFilePath);
		logger.info(String.format("Writing records to file: %s", tempFilePath));
	}

	/**
	 * Closes the open file writer.
	 * 
	 * @param args
 	 * @throws DataImportException
	 */
	@Override
	public void doAfter(Object... args) throws DataImportException {
		this.close();
	}

	/**
	 * Creates or overwrites an output file, creates a {@link FileWriter} for writing records to the file.
	 * 
	 * @param outputFilePath
	 * @throws DataImportException
	 */
	public void open(String outputFilePath) throws DataImportException{
		outputFilePath = cleanFilePath(outputFilePath);
		this.close();
		try {
			writer = new FileWriter(outputFilePath);
		} catch (IOException e){
			e.printStackTrace();
			throw new DataImportException(String.format("Cannot open output file: %s", outputFilePath));
		}
	}

	/**
	 * Flushes outstanding records to the output file and then closes the file and its writer object.
	 */
	public void close(){
		try {
			writer.flush();
			writer.close();
		} catch (Exception e){
			logger.debug(e.getMessage());
		}
	}

  /**
   * Returns the path of the temporary file to be written, if necessary.  Uses the input file's name
   *   and the pre-determined temp file directory to generate the name, so as to overwrite previous
   *   jobs' temp file.
   * @param inputFilePath
   * @return
   */
  @Override
  public String getTempFilePath(String inputFilePath){
    File tempDir;
    if (!environment.containsProperty("centromere.import.temp-dir")
        || environment.getRequiredProperty("centromere.import.temp-dir") == null
        || "".equals(environment.getRequiredProperty("centromere.import.temp-dir"))){
      tempDir = new File(System.getProperty("java.io.tmpdir"));
    } else {
      tempDir = new File(environment.getRequiredProperty("centromere.import.temp-dir"));
    }
    String fileName = "centromere.import.tmp";
    File tempFile = new File(tempDir, fileName);
    return tempFile.getPath();
  }

	protected FileWriter getWriter() {
		return writer;
	}
	
	protected String cleanFilePath(String path){
		return path.replaceAll("\\s+", "_");
	}

	@Override 
	@Autowired
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	protected Environment getEnvironment() {
		return environment;
	}
}
