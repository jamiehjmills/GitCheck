package com.eternalcode.gitcheck.gitea;

import com.eternalcode.gitcheck.git.GitRelease;
import com.eternalcode.gitcheck.git.GitReleaseProvider;
import com.eternalcode.gitcheck.git.GitRepository;

public class GiteaReleaseProvider implements GitReleaseProvider {


    private static final String GET_LATEST_RELEASE = "https://try.gitea.io/api/v1/repos/%s/tags/%s";


    @Override
    public GitRelease getLatestRelease(GitRepository repository) {
        return null;
    }
}
