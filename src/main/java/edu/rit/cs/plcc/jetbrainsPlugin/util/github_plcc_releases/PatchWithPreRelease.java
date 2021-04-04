package edu.rit.cs.plcc.jetbrainsPlugin.util.github_plcc_releases;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchWithPreRelease {
    private int patchVersion;
    private String preReleaseIdentifiers;
}
