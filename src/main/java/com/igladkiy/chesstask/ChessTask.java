package com.igladkiy.chesstask;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

        // for (Chessman chessman : Chessman.values()) {
        // for (int i = 0; i < m; i++) {
        // for (int j = 0; j < n; j++) {
        // Map<Cell, Chessman> map = new HashMap<Cell, Chessman>();
        // map.put(new Cell(i, j), chessman);
        // printField(m, n, map);
        // }
        // }
        // }

        Set<List<Chessman>> permutations = Sets.newHashSet(Collections2.permutations(chessmanList));

        permutations.parallelStream().forEach(t -> {
            calculate(m, n, t);
        });
    }

    public static void calculate(int m, int n, List<Chessman> chessmanList) {
        // TODO
    }

    public static Set<Cell> getUnavailableCells(int m, int n, Chessman chessman, Cell cell) {
        Set<Cell> unavailableCells = new HashSet<Cell>();
        if (chessman == Chessman.Knight) {
            int x;
            int y;

            x = cell.x - 2;
            y = cell.y - 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x - 2;
            y = cell.y + 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x - 1;
            y = cell.y - 2;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x + 1;
            y = cell.y - 2;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x + 2;
            y = cell.y - 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x + 2;
            y = cell.y + 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x - 1;
            y = cell.y + 2;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x + 1;
            y = cell.y + 2;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }
        }

        if (chessman == Chessman.King) {
            int x;
            int y;

            x = cell.x - 1;
            y = cell.y - 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x;
            y = cell.y - 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x + 1;
            y = cell.y - 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x + 1;
            y = cell.y;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x + 1;
            y = cell.y + 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x;
            y = cell.y + 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x - 1;
            y = cell.y + 1;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }

            x = cell.x - 1;
            y = cell.y;
            if (checkCell(m, n, x, y)) {
                unavailableCells.add(new Cell(x, y));
            }
        }
        if (chessman == Chessman.Rook) {
            calculateRookHits(m, n, cell, unavailableCells);
            unavailableCells.remove(cell);
        }
        if (chessman == Chessman.Bishop) {
            calculateBishopHits(m, n, cell, unavailableCells);
            unavailableCells.remove(cell);
        }
        if (chessman == Chessman.Queen) {
            calculateRookHits(m, n, cell, unavailableCells);
            calculateBishopHits(m, n, cell, unavailableCells);
            unavailableCells.remove(cell);
        }
        return unavailableCells;
    }

    private static void calculateRookHits(int m, int n, Cell cell, Set<Cell> unavailableCells) {
        for (int i = 0; i < m; i++) {
            unavailableCells.add(new Cell(i, cell.y));
        }
        for (int i = 0; i < n; i++) {
            unavailableCells.add(new Cell(cell.x, i));
        }
    }

    private static void calculateBishopHits(int m, int n, Cell cell, Set<Cell> unavailableCells) {
        int shiftX = 0;
        int shiftY = 0;
        if (cell.x > cell.y) {
            shiftX = cell.x - cell.y;
        } else if (cell.x < cell.y) {
            shiftY = cell.y - cell.x;
        }
        for (int i = 0; i < m || i < n; i++) {
            if (checkCell(m, n, i + shiftX, i + shiftY)) {
                unavailableCells.add(new Cell(i + shiftX, i + shiftY));
            }
        }
        for (int i = 0; i < m || i < n; i++) {
            if (checkCell(m, n, cell.x + i, cell.y - i)) {
                unavailableCells.add(new Cell(cell.x + i, cell.y - i));
            }
            if (checkCell(m, n, cell.x - i, cell.y + i)) {
                unavailableCells.add(new Cell(cell.x - i, cell.y + i));
            }
        }
    }

    private static boolean checkCell(int m, int n, int x, int y) {
        return x > -1 && y > -1 && x <= m && y <= n;
    }

    public static void printField(int m, int n, Map<Cell, Chessman> chessmanMap) {
        Set<Cell> unavailableCells = new HashSet<Cell>();
        for (Entry<Cell, Chessman> entry : chessmanMap.entrySet()) {
            unavailableCells.addAll(getUnavailableCells(m, n, entry.getValue(), entry.getKey()));
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Cell cell = new Cell(i, j);
                if (unavailableCells.contains(cell)) {
                    System.out.print("X" + " ");
                } else if (chessmanMap.containsKey(cell)) {
                    System.out.print(chessmanMap.get(cell).getUnicode() + " ");
                } else {
                    System.out.print("_" + " ");
                }
            }
            System.out.println();
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
    }
}
