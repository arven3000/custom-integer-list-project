package pro.sky;

import java.util.Arrays;
import java.util.Objects;

public class IntegerListImpl implements IntegerList {

    private final int DEFAULT_CAPACITY = 5;
    private Integer[] elementData;
    private int size;

    public IntegerListImpl() {
        this.elementData = new Integer[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public Integer add(Integer item) {
        if (size == elementData.length - 1) {
            elementData = grow(elementData);
        }
        elementData[size++] = item;
        return elementData[size];
    }

    @Override
    public Integer add(int index, Integer item) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Задан некоректный индекс. Длина StringList=%d, запарашиваемый индекс=%d", size, index));
        }
        if (size == elementData.length - 1) {
            elementData = grow(elementData);
        }
        System.arraycopy(elementData, index, elementData, index + 1, elementData.length - 1 - index);
        elementData[index] = item;
        size++;
        return elementData[index];
    }

    @Override
    public Integer set(int index, Integer item) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Задан некоректный индекс. Длина StringList=%d, запарашиваемый индекс=%d", size, index));
        }
        elementData[index] = item;
        return elementData[index];
    }

    @Override
    public Integer remove(Integer item) {
        for (int i = 0; i < size; ++i) {
            if (elementData[i].equals(item)) {
                remove(i);
                return item;
            }
        }
        throw new IllegalArgumentException("Элемент отсутствует в списке.");
    }

    @Override
    public Integer remove(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Задан некоректный индекс. Длина StringList=%d, запарашиваемый индекс=%d", size, index));
        }
        Integer item = elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, elementData.length - 1 - index);
        size--;
        if (size < elementData.length * 1.5 && elementData.length > DEFAULT_CAPACITY + 1) {
            elementData = Arrays.copyOf(elementData, (int) (elementData.length / 1.5));
        }
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        Integer[] copy = Arrays.copyOf(elementData, elementData.length);
        quickSort(copy, 0, size - 1);
        int min = 0;
        int max = size - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (Objects.equals(item, copy[mid])) {
                return true;
            }

            if (item < copy[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; ++i) {
            if (elementData[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = size - 1; i > -1; --i) {
            if (elementData[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Задан некоректный индекс. Длина StringList=%d, запарашиваемый индекс=%d", size, index));
        }
        return elementData[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (otherList == null) {
            throw new IllegalArgumentException("Переданные StringList = null");
        }
        return Arrays.equals(toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        while (size != 0) {
            remove(0);
        }
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    private Integer[] quickSort(Integer[] arr, int begin, int end) {

        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
        return arr;
    }

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    private Integer[] grow(Integer[] arr) {
        return Arrays.copyOf(arr, (int) (arr.length * 1.5));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IntegerListImpl={");
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
