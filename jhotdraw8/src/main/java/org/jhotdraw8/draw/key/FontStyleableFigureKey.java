/* @(#)FontStyleableFigureKey.java
 * Copyright © 2017 by the authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.key;

import java.util.function.Function;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import org.jhotdraw8.styleable.StyleablePropertyBean;
import org.jhotdraw8.draw.figure.Figure;
import org.jhotdraw8.text.Converter;
import org.jhotdraw8.text.CssFontConverter;
import org.jhotdraw8.text.StyleConverterAdapter;
import org.jhotdraw8.text.CssFont;
import org.jhotdraw8.styleable.WriteableStyleableMapAccessor;

/**
 * FontStyleableFigureKey.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class FontStyleableFigureKey extends AbstractStyleableFigureKey<CssFont> implements WriteableStyleableMapAccessor<CssFont> {

    private final static long serialVersionUID = 1L;

    private final CssMetaData<?, CssFont> cssMetaData;

    /**
     * Creates a new instance with the specified name and with null as the
     * default value.
     *
     * @param name The name of the key.
     */
    public FontStyleableFigureKey(String name) {
        this(name, null);
    }

    /**
     * Creates a new instance with the specified name and default value.
     *
     * @param name The name of the key.
     * @param defaultValue The default value.
     */
    public FontStyleableFigureKey(String name, CssFont defaultValue) {
        super(name, CssFont.class, //
                DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT),//
                defaultValue);
        /*
         StyleablePropertyFactory factory = new StyleablePropertyFactory(null);
         cssMetaData = factory.createFontCssMetaData(
         Figure.JHOTDRAW_CSS_PREFIX + getCssName(), s -> {
         StyleablePropertyBean spb = (StyleablePropertyBean) s;
         return spb.getStyleableProperty(this);
         });
         */
        Function<Styleable, StyleableProperty<CssFont>> function = s -> {
            StyleablePropertyBean spb = (StyleablePropertyBean) s;
            return spb.getStyleableProperty(this);
        };
        boolean inherits = false;
        String property = Figure.JHOTDRAW_CSS_PREFIX + getCssName();
        final StyleConverter<String, CssFont> converter
                = new StyleConverterAdapter<CssFont>(new CssFontConverter());
        CssMetaData<Styleable, CssFont> md
                = new SimpleCssMetaData<Styleable, CssFont>(property, function,
                        converter, defaultValue, inherits);
        cssMetaData = md;
    }

    @Override
    public CssMetaData<?, CssFont> getCssMetaData() {
        return cssMetaData;

    }

    private Converter<CssFont> converter;

    @Override
    public Converter<CssFont> getConverter() {
        if (converter == null) {
            converter = new CssFontConverter();
        }
        return converter;
    }
}
