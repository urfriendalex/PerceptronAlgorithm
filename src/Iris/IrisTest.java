package Iris;

import Algorithm.Flow;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IrisTest {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the learning rate for the perceptron: ");
        double learningRate = Double.parseDouble(reader.readLine());

        Flow flow = new Flow("/Users/tobeurdeath/Desktop/untitled/prog/NAI/NAI2/src/Iris/test.txt",
                "/Users/tobeurdeath/Desktop/untitled/prog/NAI/NAI2/src/Iris/training.txt",
                learningRate);

        System.out.println("=================\n" +
                "Enter the values of the vector to be classified. Please specify the same number of values as in the training document" +
                "\nWhen finished type \"done\"" +
                "\nWaiting...");
        System.out.println(flow.classify());
    }
}
