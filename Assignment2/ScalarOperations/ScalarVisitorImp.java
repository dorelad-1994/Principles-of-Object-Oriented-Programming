package ScalarOperations;
import Scalar.RationalScalar;
import Scalar.RealScalar;

public class ScalarVisitorImp implements ScalarVisitor {

    @Override
    public RationalScalar toRational(RationalScalar s) {
        return s;
    }

    @Override
    public RationalScalar toRational(RealScalar s) {
        return null;
    }

    @Override
    public RealScalar toReal(RationalScalar s) {
        return null;
    }

    @Override
    public RealScalar toReal(RealScalar s) {
        return s;
    }
}
