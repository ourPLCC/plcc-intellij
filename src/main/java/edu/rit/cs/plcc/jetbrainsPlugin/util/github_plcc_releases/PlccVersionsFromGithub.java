package edu.rit.cs.plcc.jetbrainsPlugin.util.github_plcc_releases;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.val;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PlccVersionsFromGithub {

    private static final String PLCC_TAGS_API_URL = "https://api.github.com/repos/ourPLCC/plcc/tags";

    private final @NonNull ObjectMapper mapper;

    private List<GithubTag> tags;

    private HashMap<Integer, HashMap<Integer, HashMap<PatchWithPreRelease, GithubTag>>> structuredTags;

    public PlccVersionsFromGithub() {
        mapper = new ObjectMapper();
    }

    public void findVersions() throws GettingPLCCVersionsFromGithubException {
        try {
            tags = mapper.readValue(
                    new URL(PLCC_TAGS_API_URL),
                    new TypeReference<>() {}
            );
        } catch (MalformedURLException mue) {
            throw new GettingPLCCVersionsFromGithubException("The URL: " + PLCC_TAGS_API_URL + "is not a valid URL", mue);
        } catch (IOException ioe) {
            throw new GettingPLCCVersionsFromGithubException("The Github API response was not able to be parsed into the data classes", ioe);
        }

        structuredTags = new HashMap<>();

        for (val tag : tags) {
            val strippedName = tag.getName().charAt(0) == 'v' ? tag.getName().substring(1) : tag.getName();
            val splitNameStrings = strippedName.split("\\.", 3);
            if (splitNameStrings.length != 3) {
                throw new GettingPLCCVersionsFromGithubException("Tag name: " + strippedName + " is not in MAJOR.MINOR.PATCH semver format");
            }

            int majorVersion;
            int minorVersion;
            int patchVersion;
            String preReleaseIdentifiers = null;

            try {
                majorVersion = Integer.parseInt(splitNameStrings[0]);
                minorVersion = Integer.parseInt(splitNameStrings[1]);
            } catch (NumberFormatException nfe) {
                throw new GettingPLCCVersionsFromGithubException("Tag name: " + strippedName + " is not in MAJOR.MINOR.PATCH semver format", nfe);
            }

            try {
                patchVersion = Integer.parseInt(splitNameStrings[2]);
            } catch (NumberFormatException nfe) {
                val splitPatchVersion = splitNameStrings[2].split("[-+]", 2);
                try {
                    patchVersion = Integer.parseInt(splitPatchVersion[0]);
                } catch (NumberFormatException nfe2) {
                    throw new GettingPLCCVersionsFromGithubException("Tag name: " + strippedName + " is not in MAJOR.MINOR.PATCH semver format", nfe2);
                }
                preReleaseIdentifiers = splitPatchVersion[1];
            }

            val patchWithPreRelease = PatchWithPreRelease.builder()
                    .preReleaseIdentifiers(preReleaseIdentifiers)
                    .patchVersion(patchVersion)
                    .build();

            if (structuredTags.containsKey(majorVersion)) {
                val map1 = structuredTags.get(majorVersion);
                if (map1.containsKey(minorVersion)) {
                    val map2 = map1.get(minorVersion);
                    if (!map2.containsKey(patchWithPreRelease)) {
                        map2.put(patchWithPreRelease, tag);
                    }
                } else {
                    val map2 = new HashMap<PatchWithPreRelease, GithubTag>();
                    map2.put(patchWithPreRelease, tag);
                    map1.put(minorVersion, map2);
                }
            } else {
                val map1 = new HashMap<Integer, HashMap<PatchWithPreRelease, GithubTag>>();
                val map2 = new HashMap<PatchWithPreRelease, GithubTag>();
                map2.put(patchWithPreRelease, tag);
                map1.put(minorVersion, map2);

                structuredTags.put(majorVersion, map1);
            }
        }
    }

    public GithubTag findLatestVersion() throws GettingPLCCVersionsFromGithubException {
        findVersions();
        val maxMajorOpt = structuredTags.keySet().stream().max(Integer::compare);
        if (maxMajorOpt.isPresent()) {
            val minorMap = structuredTags.get(maxMajorOpt.get());
            val maxMinorOpt = minorMap.keySet().stream().max(Integer::compare);
            if (maxMinorOpt.isPresent()) {
                val patchMap = minorMap.get(maxMinorOpt.get());
                val maxPatch = patchMap.keySet().stream()
                        .filter(x -> x.getPreReleaseIdentifiers() == null)
                        .max(Comparator.comparingInt(PatchWithPreRelease::getPatchVersion));
                if (maxPatch.isPresent()) {
                    return patchMap.get(maxPatch.get());
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        val builder = new StringBuilder();
        structuredTags.forEach((num0, map1) -> {
            builder.append(num0).append(" {\n");
            map1.forEach((num1, map2) -> {
                builder.append("    ").append(num1).append(" {\n");
                map2.forEach((num2, tag) -> {
                    builder.append("        ").append(num2).append(" {\n");
                    builder.append("            ").append(tag.getTarball_url()).append("\n");
                    builder.append("        }\n");
                });
                builder.append("    }\n");
            });
            builder.append("}\n");
        });
        return builder.toString();
    }
}

