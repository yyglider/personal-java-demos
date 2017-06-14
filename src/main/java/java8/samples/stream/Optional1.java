package main.java.samples.stream;

import java.util.Optional;

/**
 * @author Benjamin Winterberg
 * Optional is a simple container for a value which may be null or non-null.
 */
public class Optional1 {

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("bam");

        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
    }

}