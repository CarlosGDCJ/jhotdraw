/* @(#)DocumentStyleManager.java
 * Copyright (c) 2015 by the authors and contributors of JHotDraw.
 * You may only use this file in compliance with the accompanying license terms.
 */
package org.jhotdraw.xml.css;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jhotdraw.util.ReversedList;
import org.jhotdraw.xml.css.ast.Declaration;
import org.jhotdraw.xml.css.ast.Ruleset;
import org.jhotdraw.xml.css.ast.SelectorGroup;
import org.jhotdraw.xml.css.ast.Stylesheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * DocumentStyleManager applies styling rules to a {@code Document} or to an
 * individual {@code Element} of a document.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class DocumentStyleManager extends AbstractStyleManager {

    private final DocumentSelectorModel selectorModel = new DocumentSelectorModel();

    private final CssParser parser = new CssParser();

    public DocumentStyleManager() {
    }

    public void applyStylesRecursively(Element elem) {
        NodeList list = elem.getElementsByTagName("*");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            applyStylesTo((Element) list.item(i));
        }
    }

    public void applyStylesTo(Element elem) {
        HashMap<String, String> applicableDeclarations = new HashMap<>();

        // user agent stylesheet can not override element attributes
        for (Stylesheet s : userAgentStylesheets) {
            for (Ruleset r : s.getRulesets()) {
                if (r.getSelectorGroup().matches(selectorModel, elem)) {
                    for (Declaration d : r.getDeclarations()) {
                        if (!elem.hasAttribute(d.getProperty())) {
                            applicableDeclarations.put(d.getProperty(), d.getTerms());
                        }
                    }
                }
            }
        }

        // author stylesheet override user agent stylesheet and element attributes
        for (Stylesheet s : authorStylesheets) {
            for (Ruleset r : s.getRulesets()) {
                if (r.getSelectorGroup().matches(selectorModel, elem)) {
                    for (Declaration d : r.getDeclarations()) {
                        applicableDeclarations.put(d.getProperty(), d.getTerms());
                    }
                }
            }
        }

        // inline styles can override all other values
        if (elem.hasAttribute("style")) {
            try {
                for (Declaration d : parser.parseDeclarations(elem.getAttribute("style"))) {
                    applicableDeclarations.put(d.getProperty(), d.getTerms());
                }
            } catch (IOException ex) {
                System.err.println("DOMStyleManager: Invalid style attribute on element. style=" + elem.getAttribute("style"));
                ex.printStackTrace();
            }
        }

        for (Map.Entry<String, String> entry : applicableDeclarations.entrySet()) {
            elem.setAttribute(entry.getKey(), entry.getValue());
        }
        applicableDeclarations.clear();
    }
}