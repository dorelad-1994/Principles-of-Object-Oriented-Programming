package Scalar;
import ScalarOperations.ScalarVisitor;

public interface Scalar {
    boolean isMatch(Scalar s);
    Scalar add(Scalar s);
    Scalar mul(Scalar s);
    Scalar mul(int i);
    Scalar power(int exp);
    int sign();
    int myType();
    RealScalar toReal(ScalarVisitor s);
    RationalScalar toRational(ScalarVisitor s);

}
