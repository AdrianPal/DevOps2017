package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;

/**
 * Replace all >/>= (Greater Than/Equal) in < (Lower Than), except those which are in loops.
 */
public class GTtoLTNotInLoop extends AbstractProcessor<CtBinaryOperator> {

    @Override
    public boolean isToBeProcessed(CtBinaryOperator elem) {
        if (elem.getKind() == BinaryOperatorKind.GT || elem.getKind() == BinaryOperatorKind.GE) // If it's a ">/>="
            if (!elem.getParent().toString().startsWith("for")) // If we're not in a loop
                return true;                                    // OK, we can edit it

        return false; // Don't touch!
    }

    public void process(CtBinaryOperator elem) {
        elem.setKind(BinaryOperatorKind.LT);
    }
}