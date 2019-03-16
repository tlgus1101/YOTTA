package com.example.leehs.yottaproject06.AboutCategoryAndFavor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leehs.yottaproject06.ForSearch.SearchProduct;
import com.example.leehs.yottaproject06.R;

import java.util.List;

/**
 * Created by Administrator on 2016-01-25.
 */
public class MustHotAdapter extends BaseAdapter {

    private List<SearchProduct> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowCheckbox;
    //private boolean checkBasket;

    public MustHotAdapter(List<SearchProduct> list, LayoutInflater inflater, boolean showCheckbox ) {
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
            convertView = mInflater.inflate(R.layout.musthot_item,
                    null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.imageView);

            item.productTitle = (TextView) convertView.findViewById(R.id.name);
            //item.productDistance = (TextView)convertView.findViewById(R.id.distance);

            item.productCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        SearchProduct curProduct = mProductList.get(position);

        item.productImageView.setImageBitmap(curProduct.GetPImage());

        if(!mShowCheckbox) {
            item.productTitle.setText(curProduct.GetName());
            item.productCheckbox.setVisibility(View.GONE);
        } else {
            //아마 이 부분의 기능은 쓰일 일이 없을 것.
            if(curProduct.GetSelect() == true)
                item.productCheckbox.setChecked(true);
            else
                item.productCheckbox.setChecked(false);
        }
        return convertView;
    }

    private class ViewItem {
        ImageView productImageView;
        TextView productTitle;
        CheckBox productCheckbox;
    }
}
