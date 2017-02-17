package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtLoop;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;

/**
 * Created by Adrian on 10/02/2017.
 */
public class LTtoGT extends AbstractProcessor<CtBinaryOperator> {

    @Override
    public boolean isToBeProcessed(CtBinaryOperator element) {
        if (element.getKind() == BinaryOperatorKind.LT)
            if (!element.getParent().toString().startsWith("for"))
                return true;

        return false;
    }

    public void process(CtBinaryOperator elem) {
        elem.setKind(BinaryOperatorKind.GT);
    }
}