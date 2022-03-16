package yes.tdd.dojo.domain.args;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static yes.tdd.dojo.domain.args.BooleanOptionParserTest.option;

public class IntegerOptionParserTest {

    @Test
    void should_not_accept_extra_argument_for_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
            new SingleValuedOptionParser<>(0, Integer::parseInt).parse(asList("-p", "8080", "8081"), option("-p")));
        assertEquals(e.getOption(), "-p");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    void should_not_accept_insufficient_argument_for_single_valued_object(String arguments) {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class, () ->
            new SingleValuedOptionParser<>(0, Integer::parseInt).parse(asList(arguments.split(" ")), option("-p")));
        assertEquals(e.getOption(), "-p");
    }

    @Test
    void should_set_default_value_for_int_option() {
        assertEquals(0, new SingleValuedOptionParser<>(0, Integer::parseInt).parse(List.of(), option("-p")));
    }

    @Test
    void should_set_default_value_for_string_option() {
        assertEquals(0, new SingleValuedOptionParser<>(0, String::valueOf).parse(List.of(), option("-p")));
    }

    @Test
    void should_not_accept_extra_argument_for_d_object() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () ->
            new SingleValuedOptionParser<>("", String::valueOf).parse(asList("-d", "/usr/logs", "~/logs"), option("-d")));
        assertEquals(e.getOption(), "-d");
    }
}
