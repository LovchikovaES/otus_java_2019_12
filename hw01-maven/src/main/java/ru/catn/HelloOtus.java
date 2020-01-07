package ru.catn;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

public class HelloOtus {

    public static void main(String[] args) {
        Range<Integer> range = Range.closed(0, 9);
        System.out.println("Range = " + range);

        Range<Integer> encloseRange = Range.open(2,6);
        Range<Integer> intersectionRange = Range.closed(8, 10);
        Range<Integer> otherRange = Range.closed(13, 50);

        System.out.println();
        System.out.println("contains(0) = " + range.contains(0));
        System.out.println("lowerBoundType = " + range.lowerBoundType());
        System.out.println("upperBoundType = " + range.upperBoundType());
        System.out.println("lowerEndpoint = " + range.lowerEndpoint());
        System.out.println("upperEndpoint = " + range.upperEndpoint());
        System.out.println("canonical(integers) = " + range.canonical(DiscreteDomain.integers()));
        System.out.println("encloses([8..10]) = " + range.encloses(intersectionRange));
        System.out.println("encloses((2..6)) = " + range.encloses(encloseRange));
        System.out.println("gap([13..50]) = " + range.gap(otherRange));
        System.out.println("isConnected([8..10]) = " + range.isConnected(intersectionRange));
        System.out.println("intersection([8..10]) = " + range.intersection(intersectionRange));
        System.out.println("isConnected([13..50]) = " + range.isConnected(otherRange));
    }
}
