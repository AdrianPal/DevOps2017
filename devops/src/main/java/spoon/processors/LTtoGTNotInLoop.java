package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;

/**
 * Created by Adrian on 10/02/2017.
 */
public class LTtoGTNotInLoop extends AbstractProcessor<CtBinaryOperator> {

    @Override
    public boolean isToBeProcessed(CtBinaryOperator elem) {
        if (elem.getKind() == BinaryOperatorKind.LT)
            if (!elem.getParent().toString().startsWith("for"))
                return true;

        return false;
    }

    public void process(CtBinaryOperator elem) {
        elem.setKind(BinaryOperatorKind.GT);
    }
}