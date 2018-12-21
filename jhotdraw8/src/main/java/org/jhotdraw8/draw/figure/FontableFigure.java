/* @(#)FontableFigure.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */
package org.jhotdraw8.draw.figure;

import javafx.geometry.VPos;
import javafx.scene.control.Labeled;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.jhotdraw8.css.CssFont;
import org.jhotdraw8.css.CssSize;
import org.jhotdraw8.draw.key.BooleanStyleableFigureKey;
import org.jhotdraw8.draw.key.CssSizeStyleableFigureKey;
import org.jhotdraw8.draw.key.DirtyBits;
import org.jhotdraw8.draw.key.DirtyMask;
import org.jhotdraw8.draw.key.DoubleStyleableFigureKey;
import org.jhotdraw8.draw.key.EnumStyleableFigureKey;
import org.jhotdraw8.draw.key.FontStyleableMapAccessor;
import org.jhotdraw8.draw.key.StringOrIdentStyleableFigureKey;
import org.jhotdraw8.draw.render.RenderContext;
import org.jhotdraw8.io.DefaultUnitConverter;
import org.jhotdraw8.io.UnitConverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A figure which supports font attributes.
 *
 * @author Werner Randelshofer
 * @version $Id$
 * @design.pattern Figure Mixin, Traits.
 */
public interface FontableFigure extends Figure {

    // text properties
    /**
     * Defines the font used. Default value: {@code new Font("Arial",12)}
     */
    StringOrIdentStyleableFigureKey FONT_FAMILY = new StringOrIdentStyleableFigureKey("fontFamily", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), "Arial");
    CssSizeStyleableFigureKey FONT_SIZE = new CssSizeStyleableFigureKey("fontSize", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), new CssSize(12.0));
    EnumStyleableFigureKey<FontPosture> FONT_STYLE = new EnumStyleableFigureKey<>("fontStyle", FontPosture.class, DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT),  FontPosture.REGULAR);
    EnumStyleableFigureKey<FontWeight> FONT_WEIGHT = new EnumStyleableFigureKey<>("fontWeight", FontWeight.class, DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT),  FontWeight.NORMAL);
    FontStyleableMapAccessor FONT = new FontStyleableMapAccessor("font", FONT_FAMILY, FONT_WEIGHT, FONT_STYLE, FONT_SIZE);
    /**
     * The line spacing. Default value: {@code 0.0}
     */
    DoubleStyleableFigureKey LINE_SPACING = new DoubleStyleableFigureKey("lineSpacing", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), 0.0);
    /**
     * Whether to strike through the text. Default value: {@code false}
     */
    BooleanStyleableFigureKey STRIKETHROUGH = new BooleanStyleableFigureKey("strikethrough", DirtyMask.of(DirtyBits.NODE), false);
    /**
     * The text alignment. Default value: {@code left}
     */
    EnumStyleableFigureKey<TextAlignment> TEXT_ALIGNMENT = new EnumStyleableFigureKey<>("textAlignment", TextAlignment.class, DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT),  TextAlignment.LEFT);
    /**
     * The vertical position of the text. Default value: {@code baseline}
     */
    EnumStyleableFigureKey<VPos> TEXT_VPOS = new EnumStyleableFigureKey<>("textVPos", VPos.class, DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT),  VPos.BASELINE);
    /**
     * Whether to underline the text. Default value: {@code false}
     */
    BooleanStyleableFigureKey UNDERLINE = new BooleanStyleableFigureKey("underline", DirtyMask.of(DirtyBits.NODE), false);
    /**
     * Text wrapping width. Default value: {@code 0.0} (no wrapping).
     */
    DoubleStyleableFigureKey WRAPPING_WIDTH = new DoubleStyleableFigureKey("wrappingWidth", DirtyMask.of(DirtyBits.NODE, DirtyBits.LAYOUT), 0.0);

    /**
     * Updates a text node with fontable properties.
     *
     * @param ctx  RenderContext, can be null
     * @param text a text node
     */
    default void applyFontableFigureProperties(@Nullable RenderContext ctx, @Nonnull Text text) {
        String family = getStyledNonnull(FONT_FAMILY);
        FontPosture style = getStyledNonnull(FONT_STYLE);
        FontWeight weight = getStyledNonnull(FONT_WEIGHT);
        UnitConverter units = ctx == null ? DefaultUnitConverter.getInstance() : ctx.getNonnull(RenderContext.UNIT_CONVERTER_KEY);
        CssSize cssSize = getStyledNonnull(FONT_SIZE);
        double size = units.convert(cssSize, UnitConverter.DEFAULT);
        CssFont f = CssFont.font(family, weight, style, size);

        Font font = f.getFont();
        if (!text.getFont().equals(font)) {
            text.setFont(font);
        }
        double d = getStyledNonnull(LINE_SPACING);
        if (text.getLineSpacing() != d) {
            text.setLineSpacing(d);
        }
        d = getStyledNonnull(WRAPPING_WIDTH);
        if (text.getWrappingWidth() != d) {
            text.setWrappingWidth(d);
        }
        TextAlignment ta = getStyledNonnull(TEXT_ALIGNMENT);
        if (text.getTextAlignment() != ta) {
            text.setTextAlignment(ta);
        }
        boolean b = getStyledNonnull(UNDERLINE);
        if (text.isUnderline() != b) {
            text.setUnderline(b);
        }
        b = getStyledNonnull(STRIKETHROUGH);
        if (text.isStrikethrough() != b) {
            text.setStrikethrough(b);
        }
        VPos vp = getStyledNonnull(TEXT_VPOS);
        if (text.getTextOrigin() != vp) {
            text.setTextOrigin(vp);
        }

        final FontSmoothingType fst = FontSmoothingType.GRAY;
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
    default void applyFontableFigureProperties(RenderContext ctx, @Nonnull Labeled text) {
        Font font = getStyledNonnull(FONT).getFont();
        if (!text.getFont().equals(font)) {
            text.setFont(font);
        }
        double d = getStyledNonnull(LINE_SPACING);
        if (text.getLineSpacing() == d) {
            text.setLineSpacing(d);
        }
        TextAlignment ta = getStyledNonnull(TEXT_ALIGNMENT);
        if (text.getTextAlignment() == ta) {
            text.setTextAlignment(ta);
        }
        boolean b = getStyledNonnull(UNDERLINE);
        if (text.isUnderline() == b) {
            text.setUnderline(b);
        }
    }
}
