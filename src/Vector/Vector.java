package Vector;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    private int _vectorClass;
    private List<Double> _values;
    private int _dimension;

    public Vector(List<Double> values, int vectorClass) {
        this._vectorClass = vectorClass;
        this._values = values;
        this._dimension = values.size();
    }
    public Vector(List<Double> values) {
        this(values,-1 );
    }

    public Vector(int dimension) {
        ArrayList<Double> vectorValues = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            vectorValues.add(0.0);
        }
        this._vectorClass=-1;
        this._values=vectorValues;
        this._dimension=dimension;
    }

    public Vector sum(Vector anotherVector) {
        if (anotherVector.get_dimension() == get_dimension()) {
            Vector sumVector = new Vector(get_dimension());
            for (int i = 0; i < get_dimension(); i++) {
                sumVector.get_values().set(i, (anotherVector.get_values().get(i) + this.get_values().get(i)));
            }
            return sumVector;
        }
        else throw new RuntimeException("Operation cannot be done. Dimensions do not match");
    }

    @Override
    public String toString() {
        return "Vector { " + this.get_values() + " } " + "\n Class of this vector is:" + get_vectorClass();
    }

    public int get_vectorClass() {
        return _vectorClass;
    }

    public List<Double> get_values() {
        return _values;
    }

    public int get_dimension() {
        return _dimension;
    }

    public double dotProduct(Vector anotherVector) {
        if (this.get_dimension() == anotherVector.get_dimension()) {
            double result = 0;
            for (int i = 0; i < this.get_dimension(); i++) {
                result += anotherVector.get_values().get(i) * this.get_values().get(i);
            }
            return result;
        }
        else throw new RuntimeException("Operation cannot be done. Dimensions need to match");
    }

    public Vector multiplyBy(double y) {
        Vector resultVector = new Vector(new ArrayList<>(this.get_values()));
        for (int i = 0; i < this.get_dimension(); i++) {
            resultVector.get_values().set(i, (resultVector.get_values().get(i) * y));
        }
        return resultVector;
    }
}
