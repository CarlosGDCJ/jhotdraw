/* @(#)TextFontableFigure.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.samples.modeler.figure;

import javafx.scene.control.Labeled;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jhotdraw8.annotation.Nonnull;
import org.jhotdraw8.annotation.Nullable;
import org.jhotdraw8.css.CssFont;
import org.jhotdraw8.css.CssSize;
import org.jhotdraw8.css.DefaultUnitConverter;
import org.jhotdraw8.css.UnitConverter;
import org.jhotdraw8.draw.figure.Figure;
import org.jhotdraw8.draw.key.CssSizeStyleableFigureKey;
import org.jhotdraw8.draw.key.DirtyBits;
import org.jhotdraw8.draw.key.DirtyMask;
import org.jhotdraw8.draw.key.EnumStyleableFigureKey;
import org.jhotdraw8.draw.key.FontStyleableMapAccessor;
import org.jhotdraw8.draw.key.StringOrIdentStyleableFigureKey;
import org.jhotdraw8.draw.render.RenderContext;

/**
 * A figure which supports font attributes.
 *
 * @author Werner Randelshofer
 * @version $Id$
 * @design.pattern Figure Mixin, Traits.
 */
public interface BodyFontableFigure extends Figure {

    // text properties
    /**
     * Defines the font used. Default value: {@code new Font("Arial",12)}
     */
    StringOrIdentStyleableFigureKey BODY_FONT_FAMILY = new StringOrIdentStyleableFigureKey("bodyFontFamily", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), "Arial");
    CssSizeStyleableFigureKey BODY_FONT_SIZE = new CssSizeStyleableFigureKey("bodyFontSize", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), new CssSize(12.0));
    EnumStyleableFigureKey<FontPosture> BODY_FONT_STYLE = new EnumStyleableFigureKey<>("bodyFontStyle", FontPosture.class, DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), FontPosture.REGULAR);
    EnumStyleableFigureKey<FontWeight> BODY_FONT_WEIGHT = new EnumStyleableFigureKey<>("bodyFontWeight", FontWeight.class, DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), FontWeight.NORMAL);
    FontStyleableMapAccessor BODY_FONT = new FontStyleableMapAccessor("bodyFont", BODY_FONT_FAMILY, BODY_FONT_WEIGHT, BODY_FONT_STYLE, BODY_FONT_SIZE);

    /**
     * Updates a text node with fontable properties.
     *
     * @param ctx  RenderContext, can be null
     * @param text a text node
     */
    default void applyBodyTextFontableFigureProperties(@Nullable RenderContext ctx, @Nonnull Text text) {
        String family = getStyledNonnull(BODY_FONT_FAMILY);
        FontPosture style = getStyledNonnull(BODY_FONT_STYLE);
        FontWeight weight = getStyledNonnull(BODY_FONT_WEIGHT);
        UnitConverter units = ctx == null ? DefaultUnitConverter.getInstance() : ctx.getNonnull(RenderContext.UNIT_CONVERTER_KEY);
        CssSize cssSize = getStyledNonnull(BODY_FONT_SIZE);
        double size = units.convert(cssSize, UnitConverter.DEFAULT);
        CssFont f = CssFont.font(family, weight, style, size);

        Font font = f.getFont();
        if (!text.getFont().equals(font)) {
            text.setFont(font);
        }

        final FontSmoothingType fst = FontSmoothingType.LCD;
        if (text.getFontSmoothingType() != fst) {
            text.setFontSmoothingType(fst);
        }

    }

    /**
     * Updates a Laeled node with fontable properties.
     *
     * @param ctx  context
     * @param text a text node
     */
    default void applyBodyTextFontableFigureProperties(RenderContext ctx, @Nonnull Labeled text) {
        UnitConverter units = ctx == null ? DefaultUnitConverter.getInstance() : ctx.getNonnull(RenderContext.UNIT_CONVERTER_KEY);
        Font font = getStyledNonnull(BODY_FONT).getFont();
        if (!text.getFont().equals(font)) {
            text.setFont(font);
        }
    }
}
