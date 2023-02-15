package com.joelkuvlo.sampleprojectperson;

import java.util.HashSet;
import java.util.Set;

public class DataSource {
    private Set<String> dataElements = new HashSet<>();
    private Long id;

    public Set<String> getDataElements() {
        return dataElements;
    }

    public void setDataElements(Set<String> dataElements) {
        this.dataElements = dataElements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


