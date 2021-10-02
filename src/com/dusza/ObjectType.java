package com.dusza;

public enum ObjectType {
    FLOOR('.'),
    WALL('F'),
    CHAIR('S'),
    TABLE('T'),
    SOFA('K');

    char type;
    private ObjectType(char c) {
        this.type = c;
    }

    public char getType() {
        return type;
    }
}
