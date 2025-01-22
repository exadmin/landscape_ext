package com.github.exadmin.model.landscapefile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.exadmin.model.automerge.AutoMergeable;
import com.github.exadmin.model.automerge.IgnoreWhenAutoMerging;

import java.util.List;
import java.util.Objects;

public class TheSubCategory extends AutoMergeable {
    @JsonProperty("subcategory")
    @IgnoreWhenAutoMerging
    private TheSubCategory theSubcategory;

    @JsonProperty("name")
    private String name;

    @JsonProperty("items")
    private List<TheItem> theItemList;

    @JsonProperty("note")
    private String note;


    public String getName() {
        return name;
    }

    public List<TheItem> getItemList() {
        return theItemList;
    }

    public String getNote() {
        return note;
    }

    @Override
    protected boolean allowMergeFrom(AutoMergeable other) {
        if (other == null || getClass() != other.getClass()) return false;
        TheSubCategory that = (TheSubCategory) other;
        return Objects.equals(name, that.name);
    }
}
