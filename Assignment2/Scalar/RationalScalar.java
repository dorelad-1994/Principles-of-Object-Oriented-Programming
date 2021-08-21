package Scalar;

import ScalarOperations.ScalarVisitor;
import ScalarOperations.ScalarVisitorImp;


public class RationalScalar implements Scalar {
    private int a;
    private int b;

    public RationalScalar(int... num) {
        this.a = num[0];
        if (num.length > 1)
            this.b = num[1];
        else this.b = 1;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public boolean isMatch(Scalar s) {
        return s.myType() == 1;
    }

    @Override
    public Scalar add(Scalar s) {
        if (!isMatch(s))
            return null;
        else {
            ScalarVisitor add = new ScalarVisitorImp();
            RationalScalar rational = s.toRational(add);
            int newA = (getA() * rational.getB()) + (rational.getA() * getB());
            int newB = getB() * rational.getB();
            return new RationalScalar(newA, newB);
        }
    }

    @Override
    public Scalar mul(int i) {
        return new RationalScalar(getA() * i, getB());
    }

    @Override
    public Scalar power(int exp) {
        int newA = (int) Math.pow(getA(), exp);
        int newB = (int) Math.pow(getB(), exp);
        return new RationalScalar(newA, newB);
    }

    @Override
    public int sign() {
        boolean aNegative = getA() < 0;
        boolean bNegative = getB() < 0;
        if ((aNegative & bNegative) | (!aNegative & !bNegative))
            return 1;
        else return -1;
    }

    public int myType() {
        return 1;
    }

    @Override
    public RealScalar toReal(ScalarVisitor s) {
        return s.toReal(this);
    }

    @Override
    public RationalScalar toRational(ScalarVisitor s) {
        return s.toRational(this);
    }

    @Override
    public Scalar mul(Scalar s) {
        if (!isMatch(s))
            return null;
        else {
            ScalarVisitor mul = new ScalarVisitorImp();
            RationalScalar rational = s.toRational(mul);
            int newA = getA() * rational.getA();
            int newB = getB() * rational.getB();
            return new RationalScalar(newA, newB);
        }

    }

    @Override
    public String toString() {
        String output;
        if (getA() % getB() == 0) {
            int tmp = getA() / getB();
            output = String.valueOf(tmp);
        } else {
            if (sign() == -1 & getB() < 0) {
                int tmp = (-1) * getB();
                output = "(-" + getA() + "/" + tmp + ")";
            } else if (sign() == 1 & getA() < 0) {
                output = "(" + getA() * (-1) + "/" + getB() * (-1) + ")";
            } else
                output = "(" + getA() + "/" + getB() + ")";
        }
        return output;
    }
}

