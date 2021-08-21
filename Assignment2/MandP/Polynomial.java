package MandP;

import Scalar.RationalScalar;
import Scalar.RealScalar;
import Scalar.Scalar;
import java.util.HashMap;
import java.util.Map;

public class Polynomial {
    private Map<Integer, Monomial> monomials;

    public Polynomial() {
        monomials = new HashMap<>();
    }

    public Polynomial build(char type, String input) {
        String deleteSpaces = input.trim().replaceAll("\\s{2,}", " ");
        String[] inputArray = deleteSpaces.split(" ");
        int exp = 0;
        if (type == 'R') {
            for (String num : inputArray) {
                RealScalar realNum = new RealScalar(Double.parseDouble(num));
                Monomial mono = new Monomial(realNum, exp);
                monomials.put(exp, mono);
                exp++;
            }
        } else {
            for (String num : inputArray) {
                int a, b;
                if (num.contains("/")) {
                    String[] tmp = num.split("/");
                    a = Integer.parseInt(tmp[0]);
                    b = Integer.parseInt(tmp[1]);
                } else {
                    a = Integer.parseInt(num);
                    b = 1;
                }
                RationalScalar rationalNum = new RationalScalar(a, b);
                Monomial mono = new Monomial(rationalNum, exp);
                monomials.put(exp, mono);
                exp++;
            }
        }
        return this;
    }

    public boolean isMatch(Polynomial p) {
        Monomial mono1 = p.monomials.get(0);
        Monomial myMono = monomials.get(0);
        return myMono.isMatch(mono1);
    }

    public Polynomial add(Polynomial p) {
        if (!isMatch(p))
            return null;
        Polynomial output = new Polynomial();
        int pSize = p.monomials.size();
        int mySize = monomials.size();
        int index = Math.min(pSize, mySize);
        for (int i = 0; i < index; i++) {
            Monomial m1 = p.monomials.get(i);
            Monomial m2 = monomials.get(i);
            Monomial newM = m1.add(m2);
            output.monomials.put(i, newM);
        }
        if (pSize > mySize)
            for (int i = index; i < pSize; i++) {
                Monomial m1 = p.monomials.get(i);
                output.monomials.put(i, m1);
            }
        if (mySize > pSize)
            for (int i = index; i < mySize; i++) {
                Monomial m2 = monomials.get(i);
                output.monomials.put(i, m2);
            }
        return output;
    }

    public Polynomial mul(Polynomial p) {
        if (!isMatch(p))
            return null;
        Polynomial output = new Polynomial();
        int pSize = p.monomials.size();
        for (int i = 0; i < monomials.size(); i++) {
            for (int j = 0; j < pSize; j++) {
                Monomial myMono = monomials.get(i);
                Monomial pMono = p.monomials.get(j);
                Monomial newMono = myMono.mul(pMono);
                int key = newMono.getExp();
                if (output.monomials.containsKey(key))
                    output.monomials.put(key, output.monomials.get(key).add(newMono));
                else
                    output.monomials.put(key, newMono);
            }
        }
        return output;
    }

    public Scalar evaluate(Scalar scalar) {
        if (!(monomials.get(0).getCoe().isMatch(scalar)))
            return null;
        Scalar output = monomials.get(0).evaluate(scalar);
        for (int i = 1; i < monomials.size(); i++) {
            Scalar tmp = monomials.get(i).evaluate(scalar);
            output = output.add(tmp);
        }
        return output;

    }

    public Polynomial derivative() {
        Polynomial output = new Polynomial();
        for (int i = 0; i < monomials.size(); i++) {
            Monomial tmp = monomials.get(i).clone();
            output.monomials.put(i,tmp.derivative());
        }
        return output;
    }


    @Override
    public String toString() {
        String output = "";
        if (monomials.size() == 1 && monomials.get(0).toString().equals(" "))
            return "0";
        for (int i = 0; i < monomials.size(); i++) {
            String tmp = monomials.get(i).toString();
            if (tmp.charAt(0) != '-' & tmp.charAt(0) != ' ') {
                output = output + "+";
            }
            output = output + tmp;
        }
        output = output.replaceAll("\\s+", "");
        if (!output.isEmpty() && output.charAt(0) == '+')
            output = output.substring(1);

        return output;
    }
}