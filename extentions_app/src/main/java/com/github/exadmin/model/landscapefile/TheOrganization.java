package com.github.exadmin.model.landscapefile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.exadmin.model.automerge.AutoMergeable;
import com.github.exadmin.model.automerge.IgnoreWhenAutoMerging;

import java.util.Objects;

public class TheOrganization extends AutoMergeable {
    @JsonProperty("organization")
    @IgnoreWhenAutoMerging
    private Object organization;

    @JsonProperty("name")
    private String name;

    public TheOrganization(String name) {
        this.name = name;
    }

    public TheOrganization() {
    }

    public String getName() {
        return name;
    }

    @Override
    protected boolean allowMergeFrom(AutoMergeable other) {
        if (other == null || getClass() != other.getClass()) return false;
        TheOrganization that = (TheOrganization) other;
        return Objects.equals(name, that.name);
    }
}
