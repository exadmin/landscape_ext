package com.github.exadmin.model.landscapefile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.exadmin.model.automerge.AutoMergeable;

import java.util.Objects;

public class InOrganization extends AutoMergeable {
    @JsonProperty("name")
    private String name;

    public InOrganization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    protected boolean allowMergeFrom(AutoMergeable other) {
        if (other == null || getClass() != other.getClass()) return false;
        InOrganization that = (InOrganization) other;
        return Objects.equals(name, that.name);
    }
}
