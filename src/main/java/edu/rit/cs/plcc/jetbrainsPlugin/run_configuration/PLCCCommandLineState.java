package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.ide.util.PropertiesComponent;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCToolchain;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;

public class PLCCCommandLineState extends CommandLineState {

    private PLCCRunConfiguration runConfig;

    protected PLCCCommandLineState(ExecutionEnvironment environment, PLCCRunConfiguration runConfig) {
        super(environment);
        this.runConfig = runConfig;
    }

    // You should use a run configuration entity class to store the configuration not PropertiesComponent
    @Override
    protected @NotNull ProcessHandler startProcess() throws ExecutionException {
        var plccDir = PropertiesComponent.getInstance().getValue(PLCCToolchain.PLCC_LOCATION_PROPERTY_KEY);
        assert plccDir != null;
        var plccmk = Paths.get(plccDir, "plccmk").toString();
        GeneralCommandLine commandLine = new GeneralCommandLine(plccmk)
                .withWorkDirectory(new File(runConfig.getPlccFile()).getParent())
                .withEnvironment("LIBPLCC", plccDir);

        KillableProcessHandler handler = new KillableProcessHandler(commandLine);
        ProcessTerminatedListener.attach(handler, runConfig.getProject());
        return handler;
    }
}
