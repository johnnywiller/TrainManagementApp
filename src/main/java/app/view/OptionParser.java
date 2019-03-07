package app.view;

import app.graph.DefaultParser;
import app.graph.Interface.GraphParser;
import app.routemap.TrainStop;

import java.util.Objects;

public class OptionParser {

    private static GraphParser<TrainStop> parser = new DefaultParser<>();

    public static Option parseOption(String line) throws IllegalArgumentException {

        if (Objects.isNull(line) || line.isBlank())
            throw new IllegalArgumentException("Invalid Option");

        var regexOneNode = "[a-zA-Z]";
        var regexTwoNode = "[a-zA-Z],[a-zA-Z]";
        var regexMultiNode = "(([a-zA-Z],)|([a-zA-Z])$)+";
        var regexNumber = "\\d{1,3}";

        var op = new Option();
        var _op = line.charAt(0);
        line = line.substring(1).strip();

        switch (_op) {

            case '1':
                if (!line.matches(regexMultiNode))
                    throw new IllegalArgumentException("Invalid string. Must provide at least one node (with commas)");
                op.option = 1;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '2':
                if (!line.matches(regexOneNode))
                    throw new IllegalArgumentException("Invalid string. Must provide exactly one node");
                op.option = 2;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '3':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 3;
                op.value = Integer.parseInt(line);
                break;
            case '4':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 4;
                op.value = Integer.parseInt(line);
                break;
            case '5':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 5;
                op.value = Integer.parseInt(line);
                break;
            case '6':
                if (!line.matches(regexNumber))
                    throw new IllegalArgumentException("Invalid string. Must provide one number from 0-999");
                op.option = 6;
                op.value = Integer.parseInt(line);
                break;
            case '7':
                if (!line.matches(regexTwoNode))
                    throw new IllegalArgumentException("Invalid string. Must provide two nodes (with commas)");
                op.option = 7;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '8':
                if (!line.matches(regexMultiNode))
                    throw new IllegalArgumentException("Invalid string. Must provide at least one node (with commas)");
                op.option = 8;
                op.stops = parser.parseNodes(line, TrainStop::new);
                break;
            case '9':
                op.option = 9;
                break;

            case '0':
                op.option = 0;
                break;

            default:
                throw new IllegalArgumentException("Invalid Option");
        }

        return op;
    }

}
