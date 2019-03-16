package com.example.leehs.yottaproject06.ForSearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leehs.yottaproject06.InsertDataToServer.InsertDataToServer;
import com.example.leehs.yottaproject06.R;

import java.util.List;

/**
 * Created by MyCom on 2016-01-15.
 */
public class SearchAdapter extends BaseAdapter {

    private List<SearchProduct> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowCheckbox;
    //private boolean checkBasket;

    public SearchAdapter(List<SearchProduct> list, LayoutInflater inflater, boolean showCheckbox ) {
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
            convertView = mInflater.inflate(R.layout.search_item,
                    null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.imageView);

            item.productTitle = (TextView) convertView.findViewById(R.id.name);
            item.productPrice = (TextView)convertView.findViewById(R.id.price);
            item.productDepartment = (TextView)convertView.findViewById(R.id.department);
            item.productDistance = (TextView)convertView.findViewById(R.id.distance);
            item.productCount = (TextView)convertView.findViewById(R.id.count);

            item.productCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        SearchProduct curProduct = mProductList.get(position);

        item.productImageView.setImageBitmap(curProduct.GetPImage());

        if(!mShowCheckbox) {
            item.productTitle.setText(curProduct.GetName());
            item.productPrice.setText("" + curProduct.GetPrice() + "원");
            item.productDepartment.setText(curProduct.GetDepartment());
            item.productDistance.setText(curProduct.GetDistance() + "M");
            item.productCount.setText(""+curProduct.GetCount());

            //Log.d("SearchAdapter", "------------------------------------------");
            //Log.d("SearchAdapter", "제품명 : " + curProduct.GetName());
            //Log.d("SearchAdapter", "오늘 날짜 : " + InsertDataToServer.getDateString());
            //Log.d("SearchAdapter", "서버에서 받아온 날짜 : " + curProduct.GetDate());

            if(curProduct.GetDate().equals(InsertDataToServer.getDateString()) == false){
                Log.d("SearchAdapter", curProduct.GetName()+"의 날짜("+curProduct.GetDate()+")가 일치하지 않기 때문에 텍스트 색깔을 변경합니다.");
                item.productPrice.setTextColor(0xFF808080);
            }else{
                Log.d("SearchAdapter", curProduct.GetName()+"의 날짜("+curProduct.GetDate()+")가 일치합니다.");
                item.productPrice.setTextColor(0xFF276171);
            }
            //Log.d("SearchAdapter", "------------------------------------------");

            //Log.d("SearchAdapter", "마트명은 " + curProduct.GetDepartment() + "입니다.");
            //마트에 따른 컬러설정
            if(curProduct.GetDepartment().contains("이마트")){
                if(curProduct.GetDepartment().contains("에브리데이")){
                    Log.d("SearchAdapter","에브리데이는 이마트랑 다름.");
                    item.productDepartment.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
                }else{
                    item.productDepartment.setTextColor(0xFFE6B611); //어두운 노랑
                }
            }else if(curProduct.GetDepartment().contains("롯데마트")){
                if(curProduct.GetDepartment().contains("999")){
                    Log.d("SearchAdapter","999는 롯데마트랑 다름.");
                    item.productDepartment.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
                }else{
                    item.productDepartment.setTextColor(0xFF9e0000); //빨강
                }
            }else if(curProduct.GetDepartment().contains("홈플러스")){
                if(curProduct.GetDepartment().contains("익스프레스")){
                    Log.d("SearchAdapter","익스프레스는 홈플러스랑 다름.");
                    item.productDepartment.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
                }else{
                    item.productDepartment.setTextColor(0xFF1313A6); //어두운 파랑
                }
            }else{
                item.productDepartment.setTextColor(0xFF000000); //이외의 정보는 검은색으로 출력.
            }

            item.productCheckbox.setVisibility(View.GONE);
        } else {
            //아마 이 부분의 기능은 쓰일 일이 없을 것.
            item.productTitle.setText(curProduct.GetName() + " - " + curProduct.GetPrice() + "원 * " + curProduct.GetCount() + "개");
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
        TextView productPrice;
        TextView productDepartment;
        TextView productDistance;
        TextView productCount;
        CheckBox productCheckbox;
    }
}