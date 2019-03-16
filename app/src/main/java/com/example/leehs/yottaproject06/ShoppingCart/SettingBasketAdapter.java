package com.example.leehs.yottaproject06.ShoppingCart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leehs.yottaproject06.FindLocation.Item;
import com.example.leehs.yottaproject06.R;

import java.util.ArrayList;

/**
 * Created by MyCom on 2016-01-06.
 */
public class SettingBasketAdapter extends BaseAdapter {
    private ArrayList<Item> mLocationList;
    private LayoutInflater mInflater;
    private boolean[] isChecked;

    public SettingBasketAdapter(ArrayList<Item> list, LayoutInflater inflater) {
        this.mLocationList = new ArrayList<Item>();
        this.mLocationList = list;
        this.mInflater = inflater;
        this.isChecked = new boolean[mLocationList.size()];
    }

    public void setAllchecked(boolean check){
        int tempSize = isChecked.length;
        for(int i = 0; i < tempSize; i++){
            isChecked[i] = check;
        }
    }

    public void setChecked(int position){
        isChecked[position] = !isChecked[position];
    }

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return mLocationList.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return mLocationList.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.basket_list, null);
            item = new ViewItem();

            Log.d("nearAdapter", "setText하러 옴?");
            item.number = (TextView) convertView.findViewById(R.id.marketNumber);
            item.name = (TextView) convertView.findViewById(R.id.marketName);
            item.distance = (TextView) convertView.findViewById(R.id.marketDistance);
            item.checkBox = (CheckBox)convertView.findViewById(R.id.searchBasketChkBox);
            item.checkBox.setChecked(((ListView) parent).isItemChecked(position));
            item.checkBox.setFocusable(false);
            item.checkBox.setClickable(false);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        Item curItem = mLocationList.get(position);

        item.number.setText("" + (position + 1));
        item.name.setText("" + curItem.title);
        item.distance.setText("" + curItem.distance + "M");
        item.checkBox.setChecked(isChecked[position]);

        return convertView;
    }

    private class ViewItem {
        TextView number;
        TextView name;
        TextView distance;
        CheckBox checkBox = null;
    }
}
