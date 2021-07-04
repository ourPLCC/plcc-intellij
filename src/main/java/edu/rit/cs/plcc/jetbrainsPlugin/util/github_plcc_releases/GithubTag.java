package edu.rit.cs.plcc.jetbrainsPlugin.util.github_plcc_releases;

import lombok.Data;

@Data
public class GithubTag {

    private String name;
    private String zipball_url;
    private String tarball_url;
    private GithubCommit commit;
    private String node_id;
}
