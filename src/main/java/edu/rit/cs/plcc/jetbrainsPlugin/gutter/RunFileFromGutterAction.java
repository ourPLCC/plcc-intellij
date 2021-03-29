package edu.rit.cs.plcc.jetbrainsPlugin.gutter;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.RunManager;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironmentBuilder;
import com.intellij.history.core.Paths;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import edu.rit.cs.plcc.jetbrainsPlugin.run_configuration.PLCCRunConfiguration;
import lombok.val;
import org.jetbrains.annotations.NotNull;

public class RunFileFromGutterAction extends AnAction implements DumbAware {

    public RunFileFromGutterAction() {
        super("Compile This PLCC File");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        val project = e.getProject();
        if (project == null) return;

        val context = ConfigurationContext.getFromContext(e.getDataContext());
        val fromContext =
                RunConfigurationProducer.getInstance(PlccRunConfigurationProducer.class).createConfigurationFromContext(context);
        if (fromContext == null) return;

        val settings = fromContext.getConfigurationSettings();

        val configuration = (PLCCRunConfiguration)settings.getConfiguration();
        configuration.setName(Paths.getNameOf(configuration.getPlccFile()));

        val runManager = RunManager.getInstance(project);
        runManager.setTemporaryConfiguration(settings);
        runManager.setSelectedConfiguration(settings);
        val builder = ExecutionEnvironmentBuilder.createOrNull(DefaultRunExecutor.getRunExecutorInstance(), settings);
        if (builder != null) {
            ExecutionManager.getInstance(project).restartRunProfile(builder.build());
        }
    }
}
