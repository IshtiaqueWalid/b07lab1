
public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial p) {
        Polynomial a = this;
        Polynomial b = p;
        if (this.coefficients.length < p.coefficients.length) {
            a = p;
            b = this;
        }
        Polynomial t = new Polynomial(a.coefficients);
        for (int i = 0; i < b.coefficients.length; i++) {
            t.coefficients[i] += b.coefficients[i];
        }
        return t;
    }

    public double evaluate(double x) {
        double sum = 0.0;
        int i = 0;
        for (double c : coefficients) {
            sum += c * Math.pow(x, i);
            i++;
        }
        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
