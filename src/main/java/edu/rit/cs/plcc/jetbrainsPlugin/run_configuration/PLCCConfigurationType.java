package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PlccIcons;

public class PLCCConfigurationType extends ConfigurationTypeBase {

    public static PLCCConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(PLCCConfigurationType.class);
    }

    public PLCCConfigurationType() {
        super("Plcc", "PLCC", "Auto-compiles the grammar into java files, then runs the specified command.", PlccIcons.x16);
        addFactory(new PLCCConfigurationFactory(this));
    }
}
