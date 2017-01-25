package com.shorttext.text;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Created by hok on 1/23/17.
 */
public class ND4JBackendTest {
    public static void main(String[] args) {
        INDArray ndArray1 = Nd4j.create(new double[]{1., 2.2, 3.3});
        System.out.println(ndArray1);
        INDArray ndArray2 = Nd4j.create(new double[]{1., 2.4, 2.3});
        System.out.println(ndArray2);
        System.out.println(ndArray1.add(ndArray2));
        System.out.println(ndArray1.div(ndArray1.distance2(Nd4j.zeros(3))));
    }
}
