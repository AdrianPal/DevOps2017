package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;

/**
 * Replace all </<= (Lower Than/Equal) in > (Greater Than).
 */
public class LTtoGT extends AbstractProcessor<CtBinaryOperator> {

    public void process(CtBinaryOperator elem) {
        if (elem.getKind() == BinaryOperatorKind.LT || elem.getKind() == BinaryOperatorKind.LE) // If it's a "</<="
            elem.setKind(BinaryOperatorKind.GT);        // Set as a ">"
    }
}