package ScalarOperations;
import Scalar.RationalScalar;
import Scalar.RealScalar;


public interface ScalarVisitor {
    RationalScalar toRational(RationalScalar s);
    RationalScalar toRational(RealScalar s);
    RealScalar toReal(RationalScalar s);
    RealScalar toReal(RealScalar s);
}
