package com.company;

public class MutaKey {

    /*Same as the Key class in the HexLand project, except that y has a setter, all MutaKeys with the same x are
    the same object, and there is a constructor that only takes x as an input.
     */


    private final int x;
    private int y;

    public MutaKey(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MutaKey(int x) {
        this.x = x;
        this.y = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MutaKey)) return false;
        MutaKey key = (MutaKey) o;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result;
        return result;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

}
