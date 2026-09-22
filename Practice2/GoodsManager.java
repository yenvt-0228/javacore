package Practice2;

import java.util.Arrays;

public class GoodsManager {
    private static final int DEFAULT_CAPACITY = 10;

    private Goods[] goodsList = new Goods[DEFAULT_CAPACITY];
    private int size = 0;

    public boolean isProductCodeTaken(String productCode) {
        for (int i = 0; i < size; i++) {
            if (goodsList[i].getProductCode().equalsIgnoreCase(productCode)) {
                return true;
            }
        }
        return false;
    }

    // Requirement 3: add to the array; succeeds only if the product code is not a duplicate
    public boolean addGoods(Goods goods) {
        if (isProductCodeTaken(goods.getProductCode())) {
            return false;
        }
        if (size == goodsList.length) {
            goodsList = Arrays.copyOf(goodsList, goodsList.length * 2);
        }
        goodsList[size++] = goods;
        return true;
    }

    public int getTotalQuantityByType(String typeName) {
        int total = 0;
        for (int i = 0; i < size; i++) {
            if (goodsList[i].getTypeName().equals(typeName)) {
                total += goodsList[i].getQuantity();
            }
        }
        return total;
    }

    public double getTotalVatByType(String typeName) {
        double total = 0;
        for (int i = 0; i < size; i++) {
            if (goodsList[i].getTypeName().equals(typeName)) {
                total += goodsList[i].getVatAmount();
            }
        }
        return total;
    }

    public Goods[] getAllGoods() {
        return Arrays.copyOf(goodsList, size);
    }

    public int getSize() {
        return size;
    }
}
