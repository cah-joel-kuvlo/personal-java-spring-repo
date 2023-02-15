package com.joelkuvlo.sampleprojectperson.repository;

import com.joelkuvlo.sampleprojectperson.DataSource;

import java.util.List;

public interface DataSourceRepository {
    List<DataSource> findByName(String name);

    void save(DataSource dataSource);

    void saveAll(List<DataSource> dataSources);

    List<String> findAllDataElements();

    public List<DataSource> getDataSources();
}


