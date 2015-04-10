package com.igladkiy.chesstask;

public enum Chessman {
    King('K'), Queen('Q'), Rook('R'), Bishop('B'), Knight('H');

    private Character character;

    Chessman(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public static Chessman convert(Character character) {
        switch (character) {
        case 'K':
            return King;
        case 'Q':
            return Queen;
        case 'R':
            return Rook;
        case 'B':
            return Bishop;
        case 'H':
            return Knight;
        default:
            throw new RuntimeException("Convert character to chessman exception");
        }
    }
}
