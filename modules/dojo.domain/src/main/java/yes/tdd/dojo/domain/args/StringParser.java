package yes.tdd.dojo.domain.args;

import java.util.List;

public class StringParser implements OptionParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf(option.value());
        return arguments.get(index + 1);
    }
}
