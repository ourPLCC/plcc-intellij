package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCIcon;

public class PLCCConfigurationType extends ConfigurationTypeBase {

    public PLCCConfigurationType() {
        super("Plcc", "PLCC", "Auto-compiles the grammar into java files, then runs the specified command.", PLCCIcon.ICON);
        addFactory(new PLCCConfigurationFactory(this));
    }
}
