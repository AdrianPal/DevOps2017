package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtVariable;

/**
 * Reports warnings when empty catch blocks are found.
 */
public class TozProcessor extends AbstractProcessor<CtVariable> {
    public void process(CtVariable element) {
        element.setSimpleName("toz_"+(int)Math.floor(Math.random() * 100000001));
    }
}