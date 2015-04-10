package com.igladkiy.chesstask;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    public static Set<Cell> getUnavailableCells(int m, int n, ChessmanCell chessmanCoordinate) {
        Set<Cell> unavailableCells = new HashSet<Cell>();
        if (chessmanCoordinate.chessman == Chessman.Knight) {
            int x;
            int y;

            x = chessmanCoordinate.cell.x - 2;
            y = chessmanCoordinate.cell.y - 1;
            if (x > -1 && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x - 2;
            y = chessmanCoordinate.cell.y + 1;
            if (x > -1 && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x - 1;
            y = chessmanCoordinate.cell.y - 2;
            if (x > -1 && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x + 1;
            y = chessmanCoordinate.cell.y - 2;
            if (x <= m && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x + 2;
            y = chessmanCoordinate.cell.y - 1;
            if (x <= m && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x + 2;
            y = chessmanCoordinate.cell.y + 1;
            if (x <= m && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x - 1;
            y = chessmanCoordinate.cell.y + 2;
            if (x > -1 && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x + 1;
            y = chessmanCoordinate.cell.y + 2;
            if (x <= m && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }
        }

        if (chessmanCoordinate.chessman == Chessman.King) {
            int x;
            int y;

            x = chessmanCoordinate.cell.x - 1;
            y = chessmanCoordinate.cell.y - 1;
            if (x > -1 && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x;
            y = chessmanCoordinate.cell.y - 1;
            if (x > -1 && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x + 1;
            y = chessmanCoordinate.cell.y - 1;
            if (x <= m && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x + 1;
            y = chessmanCoordinate.cell.y;
            if (x <= m && y > -1) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x + 1;
            y = chessmanCoordinate.cell.y + 1;
            if (x <= m && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x;
            y = chessmanCoordinate.cell.y + 1;
            if (x <= m && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x - 1;
            y = chessmanCoordinate.cell.y + 1;
            if (x > -1 && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }

            x = chessmanCoordinate.cell.x - 1;
            y = chessmanCoordinate.cell.y;
            if (x > -1 && y <= n) {
                unavailableCells.add(new Cell(x, y));
            }
        }
        return unavailableCells;
    }

    public static void printField(int m, int n, Set<ChessmanCell> chessmanCoordinates) {
        Set<Cell> unavailableCells = new HashSet<Cell>();
        Map<Cell, Chessman> chessmanMap = chessmanCoordinates.stream().collect(Collectors.toMap(ChessmanCell::getCell, ChessmanCell::getChessman));
        for (ChessmanCell chessmanCoordinate : chessmanCoordinates) {
            unavailableCells.addAll(getUnavailableCells(m, n, chessmanCoordinate));
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Cell cell = new Cell(i, j);
                if (unavailableCells.contains(cell)) {
                    System.out.print("X" + " ");
                } else if (chessmanMap.containsKey(cell)) {
                    System.out.print(chessmanMap.get(cell).getCharacter() + " ");
                } else {
                    System.out.print("_" + " ");
                }
            }
            System.out.println();
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
    }
}
