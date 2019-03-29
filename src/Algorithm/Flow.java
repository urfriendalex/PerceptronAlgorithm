package Algorithm;

import Vector.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Flow {

    private Set<Vector> trainingSet; //all vectors read from the training file
    private Set<Vector> testingSet;
    private Perceptron perceptron;


    public Flow(String trainingSetPath, String testingSetPath, double learningRate) throws Exception {
        this.trainingSet = readDataSet(trainingSetPath);
        this.testingSet = readDataSet(testingSetPath);
        this.perceptron = new Perceptron(getDimension(trainingSet), learningRate, 0);

        System.out.println("Finished reading training set! Training...");

        int epoch = 0, error = 0, vectorsTrained = 0;
        double ERROR_THRESHHOLD = 0.06;
        do {
            for (Vector v : trainingSet) {
                int expectedOutput = v.get_vectorClass();
                int actualOutput = perceptron.calculateActualOutput(v);
                perceptron.trainVector(v, expectedOutput);
                vectorsTrained++;
                error += Math.abs(expectedOutput - actualOutput);
            }
            System.out.println(epoch++);
        }
        while ((double) error / vectorsTrained > ERROR_THRESHHOLD);
        getPrecisionForClasses();
        System.out.println("Estimated model's accuracy: " + getAccuracy(testingSet));
    }

    private int getDimension(Set<Vector> trainingSet) throws Exception {
        Set<Integer> dimension = new HashSet<>();
        for (Vector vector: trainingSet) {
            dimension.add(vector.get_dimension());
        }
        if (dimension.size() == 1) {
            System.out.println("Training set vectors' dimension is: " + dimension);
            return (int)dimension.toArray()[0];
        } else throw new Exception("Vector's  dimension is not the same for the vectors of training set.");
    }

    private double getAccuracy(Set<Vector> testingSet) {
        double mistakesCount = 0;
        int actualOutput, expectedOutput;
        for (Vector v: testingSet) {
            actualOutput = perceptron.calculateActualOutput(v);
            expectedOutput = v.get_vectorClass();
           if(expectedOutput!=actualOutput)
                mistakesCount++;
            }
        return 100 - (mistakesCount/testingSet.size())*100;
    }

    private void getPrecisionForClasses() {
        for(int i = 0; i < 2; i++) {
            int k = i;
            Set<Vector> testingSet = new HashSet<>(this.testingSet).stream()
                    .filter(e -> e.get_vectorClass()==k)
                    .collect(Collectors.toSet());
            System.out.println("Precision for " + k + ": " + getAccuracy(testingSet));
        }
    }


    private Set<Vector> readDataSet(String path) {
        BufferedReader reader;
        Set<Vector> resultSet = new HashSet<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                int i;
                String vectorClass;
                int vClass = 0;
                String[] lineSplit = line.split(",");
                List<Double> vectorValue = new ArrayList<>();
                for (i = 0; i < lineSplit.length-1; i++) {
                        vectorValue.add(Double.parseDouble(lineSplit[i]));
                }
                vectorClass = lineSplit[i];
                switch (vectorClass) {
                    case "Iris-versicolor":
                         vClass= 1;
                        break;
                    case "Iris-virginica":
                        vClass = 0;
                        break;
                    case("0"):
                    case("1"):
                        vClass = Integer.parseInt(vectorClass);
                        break;
                }
                Vector vector = new Vector(vectorValue, vClass);
                resultSet.add(vector);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int classify() {
        Vector vector = scanVector();
        return perceptron.calculateActualOutput(vector) == 0 ? 0 : 1;
    }

    private static Vector scanVector(){
        Scanner scanner = new Scanner(System.in);

        ArrayList<Double> inputVector = new ArrayList<>();

        while (scanner.hasNextDouble()) {
            inputVector.add(scanner.nextDouble());
        }

        return new Vector(inputVector);
    }

}
