package yes.tdd.dojo.domain.args;

public class TooManyArgumentsException extends RuntimeException {
    public TooManyArgumentsException(Option option) {
        super(option.value());
    }

    public String getOption() {
        return super.getMessage();
    }
}
