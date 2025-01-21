package com.github.exadmin.model.landscapefile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.exadmin.model.automerge.AutoMergeable;
import com.github.exadmin.model.automerge.IgnoreWhenAutoMerging;

public class InAdditionalRepos extends AutoMergeable {
    @JsonProperty("additional_repos")
    @IgnoreWhenAutoMerging
    Object repo;

    @JsonProperty("repo_url")
    private String repos;

    @JsonProperty("branch")
    private String branch;

    public String getRepos() {
        return repos;
    }

    public String getBranch() {
        return branch;
    }

    @Override
    protected boolean allowMergeFrom(AutoMergeable other) {
        return false; // information about repo & bracnh must be always added - not merged
    }
}
