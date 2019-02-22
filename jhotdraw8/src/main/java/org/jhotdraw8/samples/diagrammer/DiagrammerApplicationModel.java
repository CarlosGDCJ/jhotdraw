/* @(#)DiagrammerApplicationModel.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.samples.diagrammer;

import javafx.scene.input.DataFormat;
import org.jhotdraw8.app.Application;
import org.jhotdraw8.app.SimpleApplicationModel;
import org.jhotdraw8.app.action.Action;
import org.jhotdraw8.app.action.file.ExportFileAction;
import org.jhotdraw8.app.action.file.PrintFileAction;
import org.jhotdraw8.collection.HierarchicalMap;
import org.jhotdraw8.draw.gui.DrawingExportOptionsPane;
import org.jhotdraw8.draw.io.BitmapExportOutputFormat;
import org.jhotdraw8.draw.io.XMLEncoderOutputFormat;
import org.jhotdraw8.gui.URIExtensionFilter;
import org.jhotdraw8.svg.SvgExporter;
import org.jhotdraw8.util.Resources;

import java.util.ResourceBundle;

/**
 * DiagrammerApplicationModel.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class DiagrammerApplicationModel extends SimpleApplicationModel {
    public final static DataFormat GRAPHER_FORMAT;

    static {
        DataFormat fmt = DataFormat.lookupMimeType("application/xml+diagrammer");
        if (fmt == null) {
            fmt = new DataFormat("application/xml+diagrammer");
        }
        GRAPHER_FORMAT = fmt;
    }

    public DiagrammerApplicationModel() {
        super("Diagrammer", DiagrammerActivityController::new,
                DiagrammerApplication.class.getResource("DiagrammerMenuBar.fxml"),
                "Diagrammer Files", GRAPHER_FORMAT, "*.xml");
        getExportExtensionFilters().add(new URIExtensionFilter("SVG", SvgExporter.SVG_FORMAT, "*.svg"));
        getExportExtensionFilters().add(new URIExtensionFilter("PNG", BitmapExportOutputFormat.PNG_FORMAT, "*.png"));
        getExportExtensionFilters().add(new URIExtensionFilter("XMLSerialized", XMLEncoderOutputFormat.XML_SERIALIZER_FORMAT, "*.ser.xml"));
    }

    @Override
    public HierarchicalMap<String, Action> createApplicationActionMap(Application app) {
        HierarchicalMap<String, Action> map = super.createApplicationActionMap(app);
        map.put(PrintFileAction.ID, new PrintFileAction(app, null));
        map.put(ExportFileAction.ID, new ExportFileAction(app, DrawingExportOptionsPane::createDialog));
        return map;
    }

    @Override
    public ResourceBundle getResources() {
        return Resources.getResources("org.jhotdraw8.samples.diagrammer.Labels");
    }

    @Override
    public String getCopyright() {
        return "Version " + DiagrammerApplicationModel.class.getPackage().getImplementationVersion() + ". MIT License";
        /* Needs Java SE 9 or higher:
        ModuleDescriptor descriptor = DiagrammerApplicationModel.class.getModule().getDescriptor();
        return "Version "+descriptor.version().orElse(null)+". MIT License.";
        */
    }
}
