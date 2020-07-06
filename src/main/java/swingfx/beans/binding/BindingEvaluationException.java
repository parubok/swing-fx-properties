package swingfx.beans.binding;

/**
 * Thrown, for example, if evaluation of user provided dependencies of the binding resulted in exception.
 * In the original JavaFX code such exceptions were just logged, which, IMHO, is a bad practice.
 * Now this exception will be thrown instead and will arrive to the calling code.
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
