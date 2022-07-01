package io.github.parubok.swingfx.beans.binding;

/**
 * Thrown, for example, if evaluation of value function of the binding resulted in exception.
 * In the original JavaFX code such exceptions were just logged and default value was returned, which, IMHO, is a bad
 * practice since it hides possible bugs.
 *
 * @since swing-fx-properties 1.7
 */
public class BindingEvaluationException extends RuntimeException {

    private final Binding<?> binding;

    public BindingEvaluationException(Binding<?> binding, Exception source) {
        super("Exception while evaluating binding", source);
        this.binding = binding;
    }

    public BindingEvaluationException(Binding<?> binding, String msg) {
        super(msg);
        this.binding = binding;
    }

    /**
     * @return Binding where the exception occurred.
     */
    public Binding<?> getBinding() {
        return binding;
    }
}
