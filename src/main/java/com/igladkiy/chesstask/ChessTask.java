package com.igladkiy.chesstask;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

        Set<Map<Cell, Chessman>> resultSet = Sets.newConcurrentHashSet();

        Set<List<Chessman>> permutations = Sets.newHashSet(Collections2.permutations(chessmanList));
        permutations.parallelStream().forEach(t -> {
            calculate(m, n, t, resultSet);
        });

        for (Map<Cell, Chessman> map : resultSet) {
            printField(m, n, map);
        }
    }

    public static void calculate(int m, int n, List<Chessman> chessmanList, Set<Map<Cell, Chessman>> resultSet) {
        calculate(m, n, 0, chessmanList, new HashMap<Cell, Chessman>(), resultSet);
    }

    public static void calculate(int m, int n, int chessmanIndex, List<Chessman> chessmanList,
            Map<Cell, Chessman> chessmanMap, Set<Map<Cell, Chessman>> resultSet) {
        Chessman chessman = chessmanList.get(chessmanIndex);

        Set<Cell> hited = getHitCells(m, n, chessmanMap);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Cell cell = new Cell(i, j);
                if (!chessmanMap.containsKey(cell) && !hited.contains(cell)
                        && Collections.disjoint(chessmanMap.keySet(), getHitCells(m, n, cell, chessman))) {
                    HashMap<Cell, Chessman> chessmanMapInner = new HashMap<Cell, Chessman>(chessmanMap);
                    chessmanMapInner.put(cell, chessman);
                    if (chessmanMapInner.size() == chessmanList.size()) {
                        resultSet.add(chessmanMapInner);
                    } else {
                        calculate(m, n, chessmanIndex + 1, chessmanList, chessmanMapInner, resultSet);
                    }
                }
            }
        }
    }

    public static boolean hasNextCell(int m, int n, Cell cell) {
        return cell.y < n - 1 || (cell.y == n - 1 && cell.x < m - 1);
    }

    public static Cell nextCell(int m, int n, Cell cell) {
        int x;
        int y;
        if (cell.y < n - 1) {
            x = cell.x;
            y = cell.y + 1;
        } else {
            x = cell.x + 1;
            y = 0;
        }
        return new Cell(x, y);
    }

    public static Set<Cell> getHitCells(int m, int n, Map<Cell, Chessman> chessmanMap) {
        Set<Cell> hitCells = new HashSet<Cell>();
        for (Entry<Cell, Chessman> entry : chessmanMap.entrySet()) {
            hitCells.addAll(getHitCells(m, n, entry.getKey(), entry.getValue()));
        }
        return hitCells;
    }

    public static Set<Cell> getHitCells(int m, int n, Cell cell, Chessman chessman) {
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
            getRookHits(m, n, cell, unavailableCells);
            unavailableCells.remove(cell);
        }
        if (chessman == Chessman.Bishop) {
            getBishopHits(m, n, cell, unavailableCells);
            unavailableCells.remove(cell);
        }
        if (chessman == Chessman.Queen) {
            getRookHits(m, n, cell, unavailableCells);
            getBishopHits(m, n, cell, unavailableCells);
            unavailableCells.remove(cell);
        }
        return unavailableCells;
    }

    private static void getRookHits(int m, int n, Cell cell, Set<Cell> unavailableCells) {
        for (int i = 0; i < m; i++) {
            unavailableCells.add(new Cell(i, cell.y));
        }
        for (int i = 0; i < n; i++) {
            unavailableCells.add(new Cell(cell.x, i));
        }
    }

    private static void getBishopHits(int m, int n, Cell cell, Set<Cell> unavailableCells) {
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
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Cell cell = new Cell(i, j);
                if (chessmanMap.containsKey(cell)) {
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
