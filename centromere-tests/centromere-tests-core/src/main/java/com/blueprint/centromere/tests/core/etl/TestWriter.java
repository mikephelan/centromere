package com.blueprint.centromere.tests.core.etl;

import com.blueprint.centromere.core.etl.DataImportException;
import com.blueprint.centromere.core.etl.writer.RecordWriter;
import com.blueprint.centromere.core.model.Model;
import java.io.File;
import java.util.Map;

/**
 * @author woemler
 */
public class TestWriter<T extends Model<?>> implements RecordWriter<T> {
  
  private final Class<T> model;
  
  public TestWriter(Class<T> model) {
    this.model = model;
  }

  @Override
  public void writeRecord(T record) throws DataImportException {
    System.out.println("Running writeRecord method");
  }

  @Override
  public void doBefore(File file, Map<String, String> args) throws DataImportException {
    System.out.println("Running doBefore method");
  }

  @Override
  public void doOnSuccess(File file, Map<String, String> args) throws DataImportException {
    System.out.println("Running doOnSuccess method");
  }

  @Override
  public void doOnFailure(File file, Map<String, String> args) throws DataImportException {
    System.out.println("Running doOnFailure method");
  }
}
