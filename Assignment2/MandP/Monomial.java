package MandP;

import Scalar.Scalar;

public class Monomial {
    private Scalar coe;
    private int exp;

    public Monomial(Scalar coe, int exp) {
        this.coe = coe;
        this.exp = exp;
    }

    public boolean isMatch(Monomial m) {
        return getCoe().isMatch(m.getCoe());
    }

    public boolean canAdd(Monomial m) {
        return (isMatch(m) && getExp() == m.getExp());
    }

    public Monomial add(Monomial m) {
        if (!canAdd(m))
            return null;
        return new Monomial(getCoe().add(m.getCoe()), getExp());
    }

    public Monomial mul(Monomial m) {
        if (!isMatch(m))
            return null;
        Scalar newCoe = getCoe().mul(m.getCoe());
        int newExp = getExp() + m.getExp();
        return new Monomial(newCoe, newExp);
    }

    public Scalar evaluate(Scalar scalar) {
        Scalar tmp = scalar.power(getExp());
        return tmp.mul(getCoe());
    }

    public Monomial derivative() {
        Scalar tmp = getCoe().mul(getExp());
        setExp(getExp() - 1);
        return new Monomial(tmp, getExp());
    }

    public int sign() {
        return getCoe().sign();
    }
    public Monomial clone(){
        return new Monomial(getCoe(),getExp());
    }

    public String toString() {
        String output;
        if (getCoe().toString().equals("0"))
            return " ";
        if (getExp() == 0)
            output = getCoe().toString();
        else if (getExp() == 1) {
            if (getCoe().toString().equals("1")) {
                output = "x";
            } else if (getCoe().toString().equals("-1")) {
                output = "-x";
            } else output = getCoe().toString() + "x";
        } else {
            if (getCoe().toString().equals("1")) {
                output = "x^" + getExp();
            } else if (getCoe().toString().equals("-1"))
                output = "-x^" + getExp();
            else output = getCoe().toString() + "x^" + getExp();
        }
        return output;
    }


    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Scalar getCoe() {
        return coe;
    }

}

