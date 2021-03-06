import org.jhotdraw8.draw.spi.DrawResourceBundleProvider;

module org.jhotdraw8.draw {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires java.logging;
    requires java.desktop;
    requires transitive java.prefs;
    requires transitive javafx.fxml;
    requires transitive javafx.swing;
    requires jdk.javadoc;
    requires transitive org.jhotdraw8.application;

    opens org.jhotdraw8.draw.inspector;
    opens org.jhotdraw8.draw;
    opens org.jhotdraw8.draw.action.images;

    exports org.jhotdraw8.css;
    exports org.jhotdraw8.css.ast;
    exports org.jhotdraw8.draw;
    exports org.jhotdraw8.draw.action;
    exports org.jhotdraw8.draw.constrain;
    exports org.jhotdraw8.draw.figure;
    exports org.jhotdraw8.draw.handle;
    exports org.jhotdraw8.draw.inspector;
    exports org.jhotdraw8.draw.input;
    exports org.jhotdraw8.draw.io;
    exports org.jhotdraw8.draw.tool;
    exports org.jhotdraw8.svg;
    exports org.jhotdraw8.draw.model;
    exports org.jhotdraw8.graph;
    exports org.jhotdraw8.draw.key;
    exports org.jhotdraw8.draw.render;
    exports org.jhotdraw8.geom;
    exports org.jhotdraw8.draw.connector;
    exports org.jhotdraw8.draw.locator;
    exports org.jhotdraw8.css.text;
    exports org.jhotdraw8.tree;
    exports org.jhotdraw8.xml.text;
    exports org.jhotdraw8.xml;
    exports org.jhotdraw8.styleable;
    exports org.jhotdraw8.text;
    exports org.jhotdraw8.draw.gui;

    provides java.util.spi.ResourceBundleProvider with DrawResourceBundleProvider;
}