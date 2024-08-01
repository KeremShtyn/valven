package com.example.valven.util.api;

public class PlatformApiEndPoints {

    public static final String GITHUB_COMMITS_ENDPOINT = "https://api.github.com/repos/%s/%s/commits?since=%s";

    public static final String GITLAB_COMMITS_ENDPOINT = "https://gitlab.com/api/v4/projects/%s/repository/commits?since=%s";
}
