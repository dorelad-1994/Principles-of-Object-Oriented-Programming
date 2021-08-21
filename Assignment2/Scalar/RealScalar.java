package Scalar;
import ScalarOperations.ScalarVisitor;
import ScalarOperations.ScalarVisitorImp;

public class RealScalar implements Scalar {
    private double v;

    public RealScalar(double v) {
        this.v = v;
    }

    public boolean isMatch(Scalar s) {
        return s.myType() == 0;
    }


    public Scalar add(Scalar s) {
        if (!isMatch(s))
            return null;
        else {
            ScalarVisitor add = new ScalarVisitorImp();
            RealScalar real = s.toReal(add);
            double newV = getV() + real.getV();
            return new RealScalar(newV);
        }
    }

    public double getV() {
        return v;
    }


    public Scalar mul(int i) {
        return new RealScalar(getV() * i);
    }

    public int myType() {
        return 0;
    }

    @Override
    public RealScalar toReal(ScalarVisitor s) {
        return s.toReal(this);
    }

    @Override
    public RationalScalar toRational(ScalarVisitor s) {
        return s.toRational(this);
    }

    public Scalar power(int exp) {
        double newV = Math.pow(getV(), exp);
        return new RealScalar(newV);
    }


    public int sign() {
        if (getV() < 0)
            return -1;
        else return 1;
    }


    public Scalar mul(Scalar s) {
        if (!isMatch(s))
            return null;
        else {
            ScalarVisitor mul = new ScalarVisitorImp();
            RealScalar real = s.toReal(mul);
            double newV = getV() * real.getV();
            return new RealScalar(newV);
        }
    }


    public String toString() {
        String output = String.format("%.3f", getV());
        return output.replaceAll("()\\.0+$|(\\..+?)0+$", "$2");
    }
}

