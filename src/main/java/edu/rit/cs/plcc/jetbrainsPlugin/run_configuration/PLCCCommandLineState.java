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
import java.util.ArrayList;
import java.util.List;

public class PLCCCommandLineState extends CommandLineState {

    private PLCCRunConfiguration runConfig;

    protected PLCCCommandLineState(ExecutionEnvironment environment, PLCCRunConfiguration runConfig) {
        super(environment);
        this.runConfig = runConfig;
    }

    @Override
    protected @NotNull ProcessHandler startProcess() throws ExecutionException {
        var plccDir = PropertiesComponent.getInstance().getValue(PLCCToolchain.PLCC_LOCATION_PROPERTY_KEY);
        assert plccDir != null;
        var plccPyPath = Paths.get(plccDir, "plcc.py").toString();

        var plccFile = new File(runConfig.getPlccFile()).getName();
        List<String> commands = new ArrayList<>() {{
            add("python3");
            add(plccPyPath);
            add(plccFile);
        }};

        GeneralCommandLine commandLine = new GeneralCommandLine(commands)
                .withWorkDirectory(new File(runConfig.getPlccFile()).getParent())
                .withEnvironment("LIBPLCC", plccDir);

        KillableProcessHandler handler = new KillableProcessHandler(commandLine);
        ProcessTerminatedListener.attach(handler, runConfig.getProject());
        return handler;
    }
}
