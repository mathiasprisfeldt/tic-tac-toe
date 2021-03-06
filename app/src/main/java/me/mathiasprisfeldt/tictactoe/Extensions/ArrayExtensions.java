package me.mathiasprisfeldt.tictactoe.Extensions;

public class ArrayExtensions {
    public static <T> T[] Flatten(T[][] array, int length) {
        T[] flatArray = (T[]) new Object[length];

        for (int columnIndex = 0; columnIndex < array.length; columnIndex++) {
            T[] column = array[columnIndex];

            for (int rowIndex = 0; rowIndex < column.length; rowIndex++) {
                T entry = column[rowIndex];
                flatArray[(columnIndex * column.length) + rowIndex] = entry;
            }
        }

        return flatArray;
    }
}
