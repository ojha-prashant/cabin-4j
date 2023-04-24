import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArithmeticSolver {

    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String result = solveArithmeticExpression(line);
                writer.write(result + System.lineSeparator());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String solveArithmeticExpression(String expression) {
        Pattern pattern = Pattern.compile("[\\d+\\-*/^().\\[\\]{}]+");
        Matcher matcher = pattern.matcher(expression);

        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        while (matcher.find()) {
            String match = matcher.group();

            if (match.matches("\\d+")) {
                operands.push(Double.parseDouble(match));
            } else {
                char operator = match.charAt(0);
                while (!operators.empty() && hasHigherPrecedence(operators.peek(), operator)) {
                    double b = operands.pop();
                    double a = operands.pop();
                    char op = operators.pop();
                    operands.push(applyOperation(a, b, op));
                }
                operators.push(operator);
            }
        }

        while (!operators.empty()) {
            double b = operands.pop();
            double a = operands.pop();
            char op = operators.pop();
            operands.push(applyOperation(a, b, op));
        }

        return expression.replaceAll("[\\d+\\-*/^().\\[\\]{}]+", operands.pop().toString());
    }

    private static boolean hasHigherPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return true;
        } else if ((op1 == '^') && (op2 == '+' || op2 == '-' || op2 == '*' || op2 == '/')) {
            return true;
        }
        return false;
    }

    private static double applyOperation(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
