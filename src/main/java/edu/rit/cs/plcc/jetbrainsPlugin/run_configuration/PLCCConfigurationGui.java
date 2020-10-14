package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCToolchain;

import javax.swing.*;
import java.awt.*;

public class PLCCConfigurationGui extends JPanel {
    private JComboBox<String> plccComboBox;
    private JComboBox<Sdk> jdkComboBox;
    private JPanel rootPanel;

    public PLCCConfigurationGui() {
        super(new BorderLayout());
        new PLCCToolchain(plccComboBox, this).populateToolchainComboBox();
        for (Sdk item : ProjectJdkTable.getInstance().getAllJdks()) {
            if (item.getSdkType().toString().equals("JavaSDK")) {
                jdkComboBox.addItem(item);
            }
        }
        add(rootPanel, BorderLayout.CENTER);
    }
}
