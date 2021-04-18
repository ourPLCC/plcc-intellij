package edu.rit.cs.plcc.jetbrainsPlugin.util.github_plcc_releases;

public class GettingPLCCVersionsFromGithubException extends Exception {
    public GettingPLCCVersionsFromGithubException(String message, Throwable cause) {
        super(message, cause);
    }

    public GettingPLCCVersionsFromGithubException(String message) {
        super(message);
    }
}
