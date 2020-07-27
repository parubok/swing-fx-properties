package org.swingfx.demo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GUI to demo component property binding.
 */
public class Demo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Demo::buildUI);
    }

    private static void buildUI() {
        Logger.getLogger("org.swingfx").setLevel(Level.FINEST);

        JPanel contentPanel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        addDemoTab(new SelectedRowCountPropertyJTable(), tabbedPane);
        addDemoTab(new FocusedPropertyJComponent(), tabbedPane);
        addDemoTab(new EnabledPropertyJComponent(), tabbedPane);
        addDemoTab(new SelectedItemJComboBox(), tabbedPane);
        addDemoTab(new BackgroundPropertyJComponent(), tabbedPane);
        addDemoTab(new MouseOverPropertyJComponent(), tabbedPane);
        addDemoTab(new SelectedRowsJTable(), tabbedPane);
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static void addDemoTab(DemoTab demoTab, JTabbedPane tabbedPane) {
        tabbedPane.addTab(demoTab.getTitle(), demoTab);
    }
}
