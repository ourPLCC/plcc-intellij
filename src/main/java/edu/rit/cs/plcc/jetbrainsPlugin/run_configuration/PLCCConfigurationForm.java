package edu.rit.cs.plcc.jetbrainsPlugin.run_configuration;

import edu.rit.cs.plcc.jetbrainsPlugin.util.PLCCToolchain;

import javax.swing.*;
import java.awt.*;

public class PLCCConfigurationForm extends JPanel {
    private JComboBox comboBox1;
    private JPanel rootPanel;

    public PLCCConfigurationForm() {
        super(new BorderLayout());
        new PLCCToolchain(comboBox1, this).populateToolchainComboBox();
        add(rootPanel, BorderLayout.CENTER);
    }
}
