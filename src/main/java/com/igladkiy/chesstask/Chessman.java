package com.igladkiy.chesstask;

public enum Chessman {
    King('K', '\u265A'), Queen('Q', '\u265B'), Rook('R', '\u265C'), Bishop('B', '\u265D'), Knight('H', '\u265E');

    private Character character;
    private Character unicode;

    Chessman(Character character, Character unicode) {
        this.character = character;
        this.unicode = unicode;
    }

    public Character getCharacter() {
        return character;
    }

    public Character getUnicode() {
        return unicode;
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
