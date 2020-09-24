package edu.rit.cs.plcc.jetbrainsPlugin.sdk;

import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PLCCSdkType extends SdkType {

    public PLCCSdkType() {
        super("plcc");
    }

    @Override
    public @Nullable String suggestHomePath() {
        return System.getenv("LIBPLCC");
    }

    @Override
    public boolean isValidSdkHome(String path) {
        VirtualFile possibHome = LocalFileSystem.getInstance().findFileByIoFile(new File(path));
        if (nonNull(possibHome) && possibHome.exists() && possibHome.isDirectory()) {
            VirtualFile plccPythonFile = possibHome.findChild("plcc.py");
            return nonNull(plccPythonFile);
        }
        return false;
    }

    @Override
    public @NotNull String suggestSdkName(@Nullable String currentSdkName, String sdkHome) {
        return "PLCC SDK";
    }

    @Override
    public @Nullable AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel, @NotNull SdkModificator sdkModificator) {
        return null;
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getPresentableName() {
        return "PLCC SDK";
    }

    @Override
    public void saveAdditionalData(@NotNull SdkAdditionalData additionalData, @NotNull Element additional) {

    }

    @Override
    public @Nullable String getVersionString(String sdkHome) {
        if (isNull(sdkHome)) {
            return null;
        }

        try {
            return Files.readString(Paths.get(sdkHome, "VERSION"));
        } catch (IOException e) {
            return null;
        }
    }
}
