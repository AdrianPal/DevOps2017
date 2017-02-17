package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtVariable;

/**
 * Update all variables with a name like 'broken_RANDOM-NUMBER'.
 */
public class BrokenProcessor extends AbstractProcessor<CtVariable> {
    public void process(CtVariable element) {
        element.setSimpleName("broken_"+(int)Math.floor(Math.random() * 100000001));
    }
}