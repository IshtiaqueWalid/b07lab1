import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[1];
        exponents = new int[1];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File f) throws Exception {
        if (!f.exists() || f.isDirectory()) throw new Exception("Invalid file");
        // input
        Scanner scan = new Scanner(f);
        String str = scan.nextLine();
        scan.close();
        // temporary tokens of str split by '-' and '+' with them included
        String[] temp = new String[str.length()];
        String token = "";
        int length = 0;
        for (int i = 0; i < temp.length; i++) {
            char c = str.charAt(i);
            if (c == '+' || c == '-') {
                // to handle when a minus appears at the beginning
                if (token.equals("")) temp[length] = "0";
                else temp[length] = token;
                temp[length + 1] = c + "";
                token = "";
                length += 2;
                continue;
            }
            token += c;
        }
        temp[length] = token;
        length++;
        // remove redundant nulls
        temp = ArrayHelper.trim(temp, length);
        // tokens solely consisting of coefficients and exponents
        String[] tokens = new String[str.length() * 2];
        token = "";
        length = 0;
        int i = 0;
        // start loop from 1 if there's a minus at the front, otherwise 0
        if (temp[0].equals("-")) i++;
        // loop through every 2 tokens to skip all the +'s and -'s
        for (; i < temp.length; i += 2) {
            // set the coefficent and exponent tokens based on where the x is placed in the token
            String t = temp[i];
            String[] parts = t.split("x");
            if (t.charAt(0) == 'x') {
                tokens[length] = "1";
                if (t.length() == 1) tokens[length + 1] = "1";
                else tokens[length + 1] = parts[1];
            }
            else if (t.charAt(t.length() - 1) == 'x') {
                tokens[length] = parts[0];
                tokens[length + 1] = "1";
            }
            else if (t.contains("x")) {
                tokens[length] = parts[0];
                tokens[length + 1] = parts[1];
            }
            else {
                tokens[length] = t;
                tokens[length + 1] = "0";
            }
            // negate the coefficient if '-' exists
            if (i > 0 && temp[i - 1].equals("-")) tokens[length] = "-" + tokens[length];
            length += 2;
        }
        // cast and set the coefficients and exponents
        coefficients = new double[length / 2];
        for (i = 0; i < length / 2; i++) {
            coefficients[i] = Double.parseDouble(tokens[i * 2]);
        }
        exponents = new int[length / 2];
        for (i = 0; i < length / 2; i++) {
            exponents[i] = Integer.parseInt(tokens[i * 2 + 1]);
        }
    }

    public Polynomial simplify() {
        // prevent mutating original coefficients and exponents
        double[] newCoefficients = new double[exponents.length];
        int[] newExponents = new int[exponents.length];
        // build the coefficients and exponents based on whether or not like terms exist
        int length = 0;
        for (int i = 0; i < exponents.length; i++) {
            // all instances of terms with the same exponent (like-terms)
            int[] exponentIndices = ArrayHelper.indices(exponents, exponents[i]);
            // only add new coefficient/exponent if a like-term hasn't been added
            // which is when it is the first occurance of the term with the exponent
            if (i == exponentIndices[0]) {
                // add up like terms
                double sum = 0.0;
                for (int j = 0; j < exponentIndices.length; j++) {
                    sum += coefficients[exponentIndices[j]];
                }
                // only add the coefficient/exponent if it isn't 0
                if (sum != 0.0) {
                    newCoefficients[length] = sum;
                    newExponents[length] = exponents[i];
                    length++;
                }
            }
        }
        // remove redundant nulls
        newCoefficients = ArrayHelper.trim(newCoefficients, length);
        newExponents = ArrayHelper.trim(newExponents, length);
        return new Polynomial(newCoefficients, newExponents);
    }

    public Polynomial add(Polynomial p) {
        // maximum possible length
        int length = exponents.length + p.exponents.length;
        // prevent mutating originals
        double[] newCoefficients = new double[length];
        int[] newExponents = new int[length];
        // add first polynomial
        for (int i = 0; i < exponents.length; i++) {
            newCoefficients[i] = coefficients[i];
            newExponents[i] = exponents[i];
        }
        // add second polynomial
        for (int i = 0; i < p.exponents.length; i++) {
            newCoefficients[i + exponents.length] = p.coefficients[i];
            newExponents[i + exponents.length] = p.exponents[i];
        }
        // add up like terms
        return new Polynomial(newCoefficients, newExponents).simplify();
    }

    private Polynomial multiplyTerm(Polynomial p, double coefficients, int exponent) {
        // prevent mutating original coefficients/exponents of p
        Polynomial t = new Polynomial(
            ArrayHelper.clone(p.coefficients),
            ArrayHelper.clone(p.exponents));
        // multiply term to the polynomial
        for (int i = 0; i < t.exponents.length; i++) {
            t.coefficients[i] *= coefficients;
            t.exponents[i] += exponent;
        }
        return t;
    }

    public Polynomial multiply(Polynomial p) {
        // add products of each term by p to t
        Polynomial t = new Polynomial();
        for (int i = 0; i < exponents.length; i++) {
            t = t.add(multiplyTerm(p, coefficients[i], exponents[i]));
        }
        return t;
    }

    public void saveToFile(String filename) throws Exception {
        File f = new File(filename);
        f.createNewFile();
        FileWriter writer = new FileWriter(f);
        String str = "";
        for (int i = 0; i < exponents.length; i++) {
            if (coefficients[i] == 0.0) continue;
            if (coefficients[i] != 1.0) {
                if (coefficients[i] == (int)coefficients[i]) str += (int)coefficients[i];
                else str += coefficients[i];
            }
            if (exponents[i] == 1) str += "x";
            if (exponents[i] > 1) str += "x" + exponents[i];
            if (i < exponents.length - 1 && coefficients[i + 1] > 0.0) str += "+";
        }
        writer.write(str);
        writer.close();
    }

    public void print() {
        // print function, useful for debugging
        System.out.print(coefficients[0] + "x^" + exponents[0]);
        for (int i = 1; i < exponents.length; i++) {
            System.out.print(" + " + coefficients[i] + "x^" + exponents[i]);
        }
        System.out.println();
    }

    public double evaluate(double x) {
        // add up terms with their coefficients multiplied by x raised to the exponent
        double sum = 0.0;
        for (int i = 0; i < exponents.length; i++) {
            sum += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        // return true if the polynomial evaluated at x is close to 0 to prevent rounding errors
        double y = evaluate(x);
        double epsilon = 0.001;
        return y > -epsilon && y < epsilon;
    }
}
