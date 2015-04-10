package com.igladkiy.chesstask;

public class ChessmanCell {
    public Cell cell;
    public Chessman chessman;

    public ChessmanCell(Cell cell, Chessman chessman) {
        super();
        this.cell = cell;
        this.chessman = chessman;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cell == null) ? 0 : cell.hashCode());
        result = prime * result + ((chessman == null) ? 0 : chessman.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChessmanCell other = (ChessmanCell) obj;
        if (cell == null) {
            if (other.cell != null)
                return false;
        } else if (!cell.equals(other.cell))
            return false;
        if (chessman != other.chessman)
            return false;
        return true;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Chessman getChessman() {
        return chessman;
    }

    public void setChessman(Chessman chessman) {
        this.chessman = chessman;
    }
}
