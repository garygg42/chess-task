package com.igladkiy.chesstask;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

public class ChessTask {

    public static void main(String[] args) {
        int m;
        int n;
        List<Chessman> chessmanList;

        try {
            m = Integer.valueOf(args[0]);
            n = Integer.valueOf(args[1]);
            chessmanList = Arrays.asList(args[2].split(",")).stream().map((t) -> {
                return Chessman.convert(t.toCharArray()[0]);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Parsing input exception");
        }

        Set<List<Chessman>> permutations = Sets.newHashSet(Collections2.permutations(chessmanList));

        permutations.parallelStream().forEach(t -> {
            calculate(m, n, t);
        });
    }

    public static void calculate(int m, int n, List<Chessman> chessmanList) {
        // TODO
    }
}
