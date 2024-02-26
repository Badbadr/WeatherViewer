package rest.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamsCollector {

    public static Map<String, String> collect(String queryString) {
        return Arrays.stream(queryString.split("&")).map(s -> s.split("=")).collect(Collectors.toMap(
                arr -> arr[0], arr -> arr[1]));
    }
}
