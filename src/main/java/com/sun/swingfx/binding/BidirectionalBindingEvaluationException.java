package com.sun.swingfx.binding;

public class BidirectionalBindingEvaluationException extends RuntimeException {
    private final BidirectionalBinding<?> binding;

    public BidirectionalBindingEvaluationException(BidirectionalBinding<?> binding, String msg) {
        super(msg);
        this.binding = binding;
    }

    public BidirectionalBinding<?> getBinding() {
        return binding;
    }
}
