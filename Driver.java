import java.io.File;

public class Driver {
    public static void main(String[] args) throws Exception {
        Polynomial p = new Polynomial(new File("demo_p.txt"));
        Polynomial q = new Polynomial(new File("demo_q.txt"));
        Polynomial product = p.multiply(q);
        System.out.print("p(x) = ");
        p.print();
        System.out.print("q(x) = ");
        q.print();
        System.out.print("simplified p(x) = ");
        p.simplify().print();
        System.out.print("simplified q(x) = ");
        q.simplify().print();
        System.out.print("(p*q)(x) = ");
        product.print();
        System.out.println("(p*q)(0.1) = " + product.evaluate(0.1));
        // expected value: 4.846
        System.out.println("Is 0.6944 a root of (p*q)(x)? " +
            (product.hasRoot(0.6944) ? "Yes" : "No"));
        // expected value: Yes
        System.out.println("Is 0.7044 a root of (p*q)(x)? " +
            (product.hasRoot(0.7044) ? "Yes" : "No"));
        // expected value: No
    }
}
