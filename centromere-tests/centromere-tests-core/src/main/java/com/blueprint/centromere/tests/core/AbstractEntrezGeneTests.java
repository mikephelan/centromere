package com.blueprint.centromere.tests.core;

import com.blueprint.centromere.core.commons.model.DataFile;
import com.blueprint.centromere.core.commons.model.DataSet;
import com.blueprint.centromere.core.commons.model.Gene;
import com.blueprint.centromere.core.commons.reader.EntrezGeneInfoReader;
import com.blueprint.centromere.core.commons.repository.DataFileRepository;
import com.blueprint.centromere.core.commons.repository.DataSetRepository;
import com.blueprint.centromere.core.commons.repository.GeneRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

/**
 * @author woemler
 */
public abstract class AbstractEntrezGeneTests {

  private static final ClassPathResource GENE_INFO_FILE = new ClassPathResource(
      "samples/Homo_sapiens.gene_info");

  @Autowired private GeneRepository geneRepository;
  @Autowired private DataSetRepository dataSetRepository;
  @Autowired private DataFileRepository dataFileRepository;

  @Before
  public void setup() throws Exception{
    dataFileRepository.deleteAll();
    dataSetRepository.deleteAll();
    geneRepository.deleteAll();
    EntrezGeneInfoReader reader = new EntrezGeneInfoReader();
    DataSet dataSet = new DataSet();
    dataSet.setShortName("metadata");
    dataSet.setDisplayName("Metadata");
    dataSetRepository.insert(dataSet);
    DataFile dataFile = new DataFile();
    dataFile.setDataSetId(dataSet.getId());
    dataFile.setFilePath(GENE_INFO_FILE.getPath());
    dataFileRepository.insert(dataFile);
    reader.setDataSet(dataSet);
    reader.setDataFile(dataFile);
    
    try {
      reader.doBefore();
      Gene gene = reader.readRecord();
      while (gene != null){
        geneRepository.insert(gene);
        gene = reader.readRecord();
      }
    } finally {
      reader.doAfter();
    }
    Assert.isTrue(geneRepository.count() == 5);
  }

  public GeneRepository getGeneRepository() {
    return geneRepository;
  }
}
