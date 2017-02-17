package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;

/**
 * Replace all < (Lower Than) in > (Greater Than).
 */
public class LTtoGT extends AbstractProcessor<CtBinaryOperator> {

    public void process(CtBinaryOperator elem) {
        if (elem.getKind() == BinaryOperatorKind.LT)    // If it's a "<"
            elem.setKind(BinaryOperatorKind.GT);        // Set as a ">"
    }
}