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

package com.blueprint.centromere.cli.commands;

import com.beust.jcommander.JCommander;
import com.blueprint.centromere.cli.CommandLineRunnerException;
import com.blueprint.centromere.cli.ModelProcessorBeanRegistry;
import com.blueprint.centromere.cli.Printer;
import com.blueprint.centromere.cli.Printer.Level;
import com.blueprint.centromere.cli.parameters.ImportFileCommandParameters;
import com.blueprint.centromere.core.commons.model.DataFile;
import com.blueprint.centromere.core.commons.model.DataSet;
import com.blueprint.centromere.core.commons.repository.DataFileRepository;
import com.blueprint.centromere.core.commons.repository.DataOperations;
import com.blueprint.centromere.core.commons.repository.DataSetRepository;
import com.blueprint.centromere.core.config.DataImportProperties;
import com.blueprint.centromere.core.dataimport.exception.DataImportException;
import com.blueprint.centromere.core.dataimport.processor.RecordProcessor;
import com.blueprint.centromere.core.repository.ModelRepository;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.support.Repositories;

/**
 * @author woemler
 * @since 0.5.0
 */
public class FileImportExecutor {
	
	private ModelProcessorBeanRegistry processorRegistry;
	private DataSetRepository dataSetRepository;
	private DataFileRepository dataFileRepository;
	private Repositories repositories;
	private DataImportProperties dataImportProperties;
	
	private static final Logger logger = LoggerFactory.getLogger(FileImportExecutor.class);
	
	public void run(ImportFileCommandParameters parameters) throws CommandLineRunnerException {
	  
	  // If help flag is active, show info and exit.
	  if (parameters.isHelp()){
      showHelp();
	    return;
    }
	  
	  String dataType = parameters.getDataType();
	  String filePath = parameters.getFilePath();
	  updateDataImportProperties(parameters);

    // Check to make sure the target data type is supported and get the processor
	  if (!processorRegistry.isSupportedDataType(dataType)){
	    throw new CommandLineRunnerException(String.format("Data type %s is not supported by a " 
          + "registered record processor.", dataType));
		}
    RecordProcessor processor = processorRegistry.getByDataType(dataType);
    logger.info(String.format("Using record processor: %s", processor.getClass().getName()));
		Printer.print(String.format("Running file import: data-type=%s  file=%s", dataType, filePath), logger, Level.INFO);
		
	  // If data set ID is specified, get the record
    DataSet dataSet = null;
    if (parameters.getDataSetKey() != null){
      Optional<DataSet> dataSetOptional = Optional.ofNullable(dataSetRepository.findOne(parameters.getDataSetKey()));
      if (!dataSetOptional.isPresent()){
        dataSetOptional = dataSetRepository.findByShortName(parameters.getDataSetKey());
      }
      if (!dataSetOptional.isPresent()){
        throw new CommandLineRunnerException(String.format("Unable to identify data set using key: %s", 
            parameters.getDataSetKey()));
      }
      dataSet = dataSetOptional.get();
      Printer.print(String.format("Using DataSet record: %s", dataSet.toString()), logger, Level.INFO);
    }
    
    // If data set is not set, use the default
    if (dataSet == null){
      dataSet = dataImportProperties.getDataSet();
      Optional<DataSet> dataSetOptional = dataSetRepository.findByShortName(dataSet.getShortName());
      if (!dataSetOptional.isPresent()){
        Printer.print(String.format("Registering new data set: %s", dataSet.toString()), logger, Level.INFO);
        dataSet = dataSetRepository.insert(dataSet);
      }
    }

    // Get the data file record
    DataFile dataFile;
    Optional<DataFile> dfOptional = dataFileRepository.findByFilePath(filePath);
    
    // DataFile record already exists
    if (dfOptional.isPresent()){
      
      dataFile = dfOptional.get();
      
      // No overwrite
      if (!parameters.isOverwrite()){
        Printer.print(String.format("DataFile record already exists.  Skipping import: %s",
            filePath), logger, Level.WARN);
        return;
      } 
      
      // Overwrite
      else {

        // Check to see if the file has a different checksum before overwriting
        try {
          HashCode hashCode = Files.hash(new File(filePath), Hashing.md5());
          String checksum = hashCode.toString();
          if (dataFile.getChecksum().equalsIgnoreCase(checksum)){
            Printer.print(String.format("File is identical to original, overwrite will be skipped: %s", 
                filePath), logger, Level.INFO);
            return;
          }
        } catch (IOException e){
          throw new CommandLineRunnerException(e);
        }

        Printer.print(String.format("Overwriting existing data file record: %s", filePath), logger, Level.INFO);

        // Get the repository for the file's data type
        ModelRepository r;
        try {
          r = (ModelRepository) repositories.getRepositoryFor(dataFile.getModelType());
        } catch (ClassNotFoundException e){
          throw new CommandLineRunnerException(e);
        }

        // If possible, delete the associated records for the file
        if (r instanceof DataOperations) {
          ((DataOperations) r).deleteByDataFileId(dataFile.getId());
        } else {
          Printer.print("Data is not over-writable.  Exiting.", logger, Level.WARN);
          return;
        }

        // Update the existing record
        dataFile.setDateCreated(dataFile.getDateCreated());
        //dataFileRepository.delete(dataFile);
        Printer.print(String.format("Updating existing data file record: %s", dataFile.toString()),
            logger, Level.INFO);
        dataFile = dataFileRepository.update(dataFile);
        
      }
    } 
    
    // New file
    else {

      dataFile = new DataFile();
      dataFile.setFilePath(filePath);
      dataFile.setDataType(dataType);
      dataFile.setModel(processor.getModel());
      dataFile.setDataSetId(dataSet.getId());
      dataFile.setDateCreated(new Date());
      dataFile.setDateUpdated(new Date());
      try {
        HashCode hashCode = Files.hash(new File(filePath), Hashing.md5());
        dataFile.setChecksum(hashCode.toString());
      } catch (IOException e){
        throw new CommandLineRunnerException(e);
      }

      Printer.print(String.format("Registering new data file record: %s", dataFile.toString()),
          logger, Level.INFO);
      dataFile = dataFileRepository.insert(dataFile);
      
    }

		// Configure the processor
		processor.setDataSet(dataSet);
    processor.setDataFile(dataFile);
    runProcessor(processor);
    Printer.print("File processing complete.", logger, Level.INFO);
    
	}

  /**
   * Execute the {@link RecordProcessor} on the supplied file.
   * 
   * @param processor
   * @throws CommandLineRunnerException
   */
	private void runProcessor(RecordProcessor processor) throws CommandLineRunnerException {
    
	  logger.info("Running processor doBefore method");
    try {
      processor.doBefore();
    } catch (DataImportException e){
      throw new CommandLineRunnerException(e);
    }

    Exception exception = null;
    try {
      logger.info("Processing file");
      processor.run();
    } catch (Exception e){
      exception = e;
    }

    try {
      if (processor.isInFailedState()) {
        logger.error("Processor execution failed.  Triggering 'doOnFailure' method.");
        processor.doOnFailure();
        if (exception != null)
          throw new CommandLineRunnerException(exception);
      } else {
        logger.info("Running processor doAfter method");
        processor.doAfter();
      }
    } catch (DataImportException e){
      throw new CommandLineRunnerException(e);
    }
    
  }

  /**
   * Displays usage information for file import, and shows the available data types and {@link DataSet}
   *   identifiers for usage.
   */
	private void showHelp() {
    JCommander.newBuilder()
        .addCommand(ImportFileCommandParameters.COMMAND, new ImportFileCommandParameters())
        .build()
        .usage();
    System.out.println("\nAvailable data types:");
    System.out.println("    Name  Description");
    System.out.println("    ----  -----------");
    for (Map.Entry<String, String> entry: new TreeMap<>(processorRegistry.getDataTypeDescriptionMap()).entrySet()){
      System.out.println(String.format("    %s: %s", entry.getKey(), entry.getValue()));
    }
    System.out.println("\nAvailable data sets:");
    System.out.println("    ShortName  ID");
    System.out.println("    ----  -----------");
    for (DataSet dataSet: dataSetRepository.findAll(new Sort(Direction.ASC, "shortName"))){
      System.out.println(String.format("    %s: %s", dataSet.getShortName(), dataSet.getId()));
    }
  }
	
  private void updateDataImportProperties(ImportFileCommandParameters parameters){
	  if (parameters.isSkipInvalidFiles()) dataImportProperties.setSkipInvalidFiles(true);
    if (parameters.isSkipInvalidGenes()) dataImportProperties.setSkipInvalidGenes(true);
    if (parameters.isSkipInvalidRecords()) dataImportProperties.setSkipInvalidRecords(true);
    if (parameters.isSkipInvalidSamples()) dataImportProperties.setSkipInvalidSamples(true);
  }

	@Autowired
	public void setProcessorRegistry(ModelProcessorBeanRegistry processorRegistry) {
		this.processorRegistry = processorRegistry;
	}

	@Autowired
  public void setDataImportProperties(DataImportProperties dataImportProperties) {
    this.dataImportProperties = dataImportProperties;
  }

  @Autowired
  public void setDataSetRepository(DataSetRepository dataSetRepository) {
    this.dataSetRepository = dataSetRepository;
  }

  @Autowired
  public void setDataFileRepository(DataFileRepository dataFileRepository) {
    this.dataFileRepository = dataFileRepository;
  }

  @Autowired
  public void setRepositories(Repositories repositories) {
    this.repositories = repositories;
  }
}
