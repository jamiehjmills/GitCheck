package com.eternalcode.gitcheck.github;

import com.eternalcode.gitcheck.git.GitException;
import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.shared.Http;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class GitHubReleaseProvider implements GitReleaseProvider {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_LATEST_RELEASE = "https://api.github.com/repos/%s/releases/latest";

    @Override
    public GitRelease getLatestRelease(GitRepository repository) {
        Http http = new Http(repository, GET_LATEST_RELEASE, USER_AGENT);
        JSONObject json = http.requestLastRelease();

        return GitRelease.builder()
                .name(JSONUtil.asString(json, "name"))
                .branch(JSONUtil.asString(json, "target_commitish"))
                .tag(JSONUtil.asGitTag(json, "tag_name"))
                .pageUrl(JSONUtil.asString(json, "html_url"))
                .publishedAt(JSONUtil.asInstant(json, "published_at"))
                .build();
    }

}
