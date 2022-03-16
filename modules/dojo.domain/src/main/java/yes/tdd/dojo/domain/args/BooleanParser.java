package yes.tdd.dojo.domain.args;

import java.util.List;

public class BooleanParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        return arguments.contains(option.value());
    }
}
