package com.eternalcode.gitcheck.gitea;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.shared.Http;
import org.json.simple.JSONObject;

public class GiteaReleaseProvider implements GitReleaseProvider {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_LATEST_RELEASE = "https://try.gitea.io/api/v1/repos/%s/tags?limit=1";

    @Override
    public GitRelease getLatestRelease(GitRepository repository) {

        Http http = new Http(repository, GET_LATEST_RELEASE, USER_AGENT);
        JSONObject json = http.requestLastRelease();

        //create my own giteaRelease builder

        return null;
    }
}
