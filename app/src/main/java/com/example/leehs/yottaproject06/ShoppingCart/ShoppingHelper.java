package com.example.leehs.yottaproject06.ShoppingCart;

import java.util.List;
import java.util.Vector;

/**
 * Created by MyCom on 2016-01-06.
 */

/*
ShoppingHelper는 static을 통해서 Product를 리턴받음으로서
여러 액티비티에서 한 리스트의 정보를 공유할 수 있도록 해놨습니다.
때문에 ProductDetailsActivity에서 추가한 정보를
BasketActivity에서 ShoppingHelper를 통해 리턴받음으로서 별다른 Intent 사용없이도 같은 정보를 사용할 수 있습니다.
 */
public class ShoppingHelper {
    private static List<Product> yCart;

    public static List<Product> getCart() {
        if(yCart == null) {
            yCart = new Vector<Product>();
        }
        return yCart;
    }
}
