/**
 * @(#)SimpleHarmonicRule.java  1.0  Apr 27, 2008
 *
 * Copyright (c) 2008 Werner Randelshofer
 * Staldenmattweg 2, CH-6405 Immensee, Switzerland
 * All rights reserved.
 *
 * The copyright of this software is owned by Werner Randelshofer. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Werner Randelshofer. For details see accompanying license terms. 
 */
package org.jhotdraw.color;

/**
 * SimpleHarmonicRule.
 *
 * @author Werner Randelshofer
 * @version 1.0 Apr 27, 2008 Created.
 */
public class SimpleHarmonicRule extends AbstractHarmonicRule {

    private float difference;
    private int componentIndex;

    public SimpleHarmonicRule(int componentIndex, float difference, int baseIndex, int... derivedIndices) {
        this.componentIndex = componentIndex;
        this.difference = difference;
        this.baseIndex = baseIndex;
        this.derivedIndices = derivedIndices;
    }

    public void setConstraint(float constraint) {
        this.difference = constraint;
    }

    public float getConstraint(float constraint) {
        return constraint;
    }

    public void setComponentIndex(int newValue) {
        this.componentIndex = newValue;
    }

    public int getComponentIndex() {
        return componentIndex;
    }

    public void apply(HarmonicColorModel model) {
        if (derivedIndices != null) {
            CompositeColor baseColor = model.get(getBaseIndex());
            if (baseColor != null) {
                float[] derivedComponents = null;
                for (int i = 0; i < derivedIndices.length; i++) {
                    derivedComponents = baseColor.getComponents(derivedComponents);
                    derivedComponents[componentIndex] = baseColor.getComponent(componentIndex) + difference * (i + 1);
                    model.set(derivedIndices[i], new CompositeColor(model.getColorSystem(), derivedComponents));
                }
            }
        }
    }

    public void colorChanged(HarmonicColorModel model, int index, CompositeColor oldValue, CompositeColor newValue) {
        //
    }
}
