package com.chenb.entry;

import org.springframework.stereotype.Component;

@Component
public class Depot {

    private Integer depotId;
    private String simpleName;
    private String fullName;
    private String description;

    public void setDepotId(Integer depotId) {
        this.depotId = depotId;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDepotId() {
        return depotId;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }
}
