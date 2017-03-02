package spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;

/**
 * Replace > (Greater Than) in < (Lower Than), with a probability threshold.
 */
public class GTtoLTRandom extends AbstractProcessor<CtBinaryOperator> {

    public static final double THRESHOLD = 0.3;

    @Override
    public boolean isToBeProcessed(CtBinaryOperator elem) {
        // If it's a ">" && random (with a probability)
        return elem.getKind() == BinaryOperatorKind.GT && Math.random() > THRESHOLD;
    }

    public void process(CtBinaryOperator elem) {
        elem.setKind(BinaryOperatorKind.LT);
    }
}