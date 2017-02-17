package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;

/**
 * Replace all > (Greater Than) in < (Lower Than), except those which are in loops.
 */
public class GTtoLTNotInLoop extends AbstractProcessor<CtBinaryOperator> {

    @Override
    public boolean isToBeProcessed(CtBinaryOperator elem) {
        if (elem.getKind() == BinaryOperatorKind.GT)
            if (!elem.getParent().toString().startsWith("for"))
                return true;

        return false;
    }

    public void process(CtBinaryOperator elem) {
        elem.setKind(BinaryOperatorKind.LT);
    }
}