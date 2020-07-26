package org.swingfx.demo;

import javax.swing.JPanel;
import java.awt.LayoutManager;

abstract class DemoTab extends JPanel {
    DemoTab() {
        super();
    }

    DemoTab(LayoutManager layoutManager) {
        super(layoutManager);
    }

    abstract String getTitle();
}
