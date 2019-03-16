package com.example.leehs.yottaproject06.ShoppingCart;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leehs.yottaproject06.R;

import java.util.List;

/**
 * Created by MyCom on 2016-01-06.
 */

/*
장바구니 및 제품선택(CategoryActivity)에서 사용될 리스트뷰에
추가하기 위한 어댑터입니다.
 */
public class ProductAdapter extends BaseAdapter {

    private List<Product> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowCheckbox;
    //private boolean checkBasket;

    public ProductAdapter(List<Product> list, LayoutInflater inflater, boolean showCheckbox ) {
        mProductList = list;
        mInflater = inflater;
        mShowCheckbox = showCheckbox;
        //checkBasket = _checkBasket;
    }

    @Override
    public int getCount() {
        //return 2;
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.basket_item, null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.ImageViewItem);

            item.productname = (TextView) convertView.findViewById(R.id.TextViewItemname);
            item.productprice = (TextView) convertView.findViewById(R.id.TextViewItemprice);
            item.productCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        Product curProduct = mProductList.get(position);

        item.productImageView.setImageBitmap(curProduct.GetBitmap());

        if(!mShowCheckbox) {
            item.productname.setText(curProduct.GetName());
            item.productCheckbox.setVisibility(View.GONE);
        } else {
            if (curProduct.GetYesNo()) {
                item.productname.setPaintFlags(item.productname.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                item.productname.setText(curProduct.GetName());
                item.productprice.setText(curProduct.GetPrice() + "원 * " + curProduct.GetPicker() + "개");
            } else {
                item.productname.setPaintFlags(item.productname.getPaintFlags() & ~Paint.FAKE_BOLD_TEXT_FLAG);
                item.productname.setText(curProduct.GetName());
                item.productprice.setText(curProduct.GetPrice() + "원 * " + curProduct.GetPicker() + "개");
            }
            if(curProduct.GetSelect()== true)
                item.productCheckbox.setChecked(true);
            else
                item.productCheckbox.setChecked(false);
        }
        return convertView;
    }

    private class ViewItem {
        ImageView productImageView;
        TextView productname;
        TextView productprice;
        CheckBox productCheckbox;
    }
}