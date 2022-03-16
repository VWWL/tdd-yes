package yes.tdd.dojo.domain.args;

import java.util.List;

public interface OptionParser<T> {
    T parse(List<String> arguments, Option option);
}
