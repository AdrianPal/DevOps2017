package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;

/**
 * Created by Adrian on 10/02/2017.
 */
public class LTtoGT extends AbstractProcessor<CtBinaryOperator> {
    public void process(CtBinaryOperator elem) {
        if (elem.getKind() == BinaryOperatorKind.LT)
            elem.setKind(BinaryOperatorKind.GT);
    }
}