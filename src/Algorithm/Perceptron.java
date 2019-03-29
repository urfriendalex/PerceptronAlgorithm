package Algorithm;

import Vector.Vector;
import java.util.ArrayList;
import java.util.List;


public class Perceptron {

    private Vector weightsVector;
    private double learningRate;

    public Perceptron(int inputs, double learningRate, double threshold) {
        this.learningRate = learningRate;
        this.weightsVector = initializeWeightsVector(inputs);
        this.weightsVector.get_values().set(inputs - 1, threshold);
    }

    public Vector VectorFakeInput(Vector inputVector){
        List<Double> modifiedInputVectorValues = new ArrayList<>(inputVector.get_values());
        modifiedInputVectorValues.add(-1.0);
        return new Vector(modifiedInputVectorValues,inputVector.get_vectorClass());
    }

    public int calculateActualOutput(Vector inputVector) {
        Vector modifiedInputVector = VectorFakeInput(inputVector);
        double dotProduct = weightsVector.dotProduct(modifiedInputVector);
        return dotProduct > 0 ? 1 : 0;
    }

    private Vector initializeWeightsVector(int dimension) {
        Vector result = new Vector(dimension + 1);
        for (int i = 0; i < result.get_values().size(); i++) {
            result.get_values().set(i,0.1);
        }
        return result;
    }

    public void trainVector(Vector inputVector, int expectedOutput) {
        int actualOutput = calculateActualOutput(inputVector);
        Vector modifiedInput = VectorFakeInput(inputVector);
        System.out.println("Input vector with fake input: " + modifiedInput + "\n\t Weights vector condition before: " + this.weightsVector);
        this.weightsVector = this.weightsVector.sum(modifiedInput.multiplyBy(learningRate * (expectedOutput - actualOutput)));
        System.out.println("\t Weights vector condition after: " + this.weightsVector);
    }
}
