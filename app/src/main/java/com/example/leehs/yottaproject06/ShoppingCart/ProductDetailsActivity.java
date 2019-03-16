package com.example.leehs.yottaproject06.ShoppingCart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.ListItem;
import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

import java.util.List;
import java.util.Vector;

/**
 * Created by MyCom on 2016-01-06.
 */

/*
SelectActivity에서 리스트 아이템을 클릭하면 이 액티비티가 실행되며
여기서 수량과 가격정보(추후에 추가할 것)를 입력하면
해당 정보가 입력되서 장바구니에 추가됩니다.
 */
public class ProductDetailsActivity extends Activity {

    LinearLayout ySelectDetailsLayout;

    public static final String PRODUCT_INFO = "PRODUCT";

    List<Product> catalog;

    Button addToCartButton;
    Product selectedProduct;

    ListItem getList;
    Bitmap getBitmap;
    int getNum;

    EditText editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_details);

        //다이얼로그를 띄우고 밖을 클릭했을 시 꺼지는 것을 방지함.
        this.setFinishOnTouchOutside(false);

        ySelectDetailsLayout = (LinearLayout)findViewById(R.id.ySelectDetailsLayout);
        ySelectDetailsLayout.setBackgroundDrawable(new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.shoppingbag_bg)));

        Intent intent = getIntent();
        getList = (ListItem) intent.getSerializableExtra(PRODUCT_INFO);
        getBitmap = (Bitmap) intent.getParcelableExtra("IMAGE");

        catalog = new Vector<Product>();
        final List<Product> cart = ShoppingHelper.getCart();

        selectedProduct = new Product(getList.getData(0), getBitmap,
                getList.getData(3), Integer.parseInt(getList.getData(1)));

        // Set the proper image and text

        ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);
        TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
        TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);
        editPrice = (EditText)findViewById(R.id.editPrice);


        productImageView.setImageBitmap(selectedProduct.GetBitmap());
        productTitleTextView.setText(selectedProduct.GetName());
        productDetailsTextView.setText(selectedProduct.GetDepartment());

        addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //여기서 카운트 곱하기하면 될듯.
                selectedProduct.setPicker(getNum);
                Log.d("장바구니 수략", "" + selectedProduct.GetPicker());

                //selectedProduct.setTotalPrice();
                cart.add(selectedProduct);

                ToastMessage toast;
                toast = new ToastMessage(ProductDetailsActivity.this);
                toast.showToast(selectedProduct.GetName() + "이(가) 장바구니에 추가되었습니다.\n("
                        + selectedProduct.GetDepartment() + ")", Toast.LENGTH_SHORT);
                finish();
            }
        });

        // Disable the add to cart button if the item is already in the cart

        for (int i = 0; i < cart.size(); ++i) {
            if (cart.get(i).GetName().equals(selectedProduct.GetName())) {
                addToCartButton.setEnabled(false);
                addToCartButton.setText("Item in Cart");
            }
        }

        setupUI();
    }

    public void setupUI() {
        NumberPicker np = (NumberPicker) findViewById(R.id.npid);
        np.setMaxValue(100); //최대 선택할 수 있는 값이 100
        np.setMinValue(1); //최소 선택할 수 있는 값이 1
        np.setWrapSelectorWheel(false); //최대값에서 더이상 올라가지 않게하기.
        np.setOnLongPressUpdateInterval(100); //길게 누르고 있으면 더 빨리 값이 바뀌게 하기.
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                getNum = newVal;
                Log.d("ProductDetailsActivity", "피커 값 : " + newVal);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.ySelectDetailsLayout));
    }

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();
            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }
}