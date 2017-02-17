package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;

/**
 * Replace all > (Greater Than) in < (Lower Than).
 */
public class GTtoLT extends AbstractProcessor<CtBinaryOperator> {
    public void process(CtBinaryOperator elem) {
        if (elem.getKind() == BinaryOperatorKind.GT)
            elem.setKind(BinaryOperatorKind.LT);
    }
}