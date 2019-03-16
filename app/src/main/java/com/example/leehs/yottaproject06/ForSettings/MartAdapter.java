package com.example.leehs.yottaproject06.ForSettings;

import android.graphics.Paint;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehs.yottaproject06.R;
import com.example.leehs.yottaproject06.ToastMessage;

import java.util.List;

/**
 * Created by MyCom on 2016-01-13.
 */
public class MartAdapter extends BaseAdapter {

    private List<MartInfo> yMartList;
    private LayoutInflater mInflater;
    private boolean mShowCheckbox;

    public MartAdapter(List<MartInfo> list, LayoutInflater inflater, boolean showCheckbox) {
        yMartList = list;
        mInflater = inflater;
        mShowCheckbox = showCheckbox;
    }

    @Override
    public int getCount() {
        return yMartList.size();
    }

    @Override
    public Object getItem(int position) {
        return yMartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.mart_item,
                    null);
            item = new ViewItem();

            item.yMartImageView = (ImageView) convertView
                    .findViewById(R.id.ImageViewItem);

            item.yMartTitle = (TextView) convertView.findViewById(R.id.yMartTitle);
            item.yMartDistance = (TextView) convertView.findViewById(R.id.yMartDistance);
            item.yMartCheckbox = (CheckBox) convertView.findViewById(R.id.CheckBoxSelected);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        MartInfo curMart = yMartList.get(position);

        item.yMartImageView.setImageDrawable(curMart.GetMartImage());
        item.yMartTitle.setText(curMart.GetMartName());
        item.yMartDistance.setText(curMart.GetMartDistance() + "M");

        //Log.d("MartAdapter : " + curMart.GetMartName(), "제발 : " + curMart.SavedCheck());
        if(curMart.SavedCheck()) {
            //저장된 마트는 볼드체로 해서 찐하게.
            Log.d("MartAdapter", "저장됐던 마트(볼드체로) : " + curMart.GetMartName());
            item.yMartTitle.setPaintFlags(item.yMartTitle.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

            item.yMartDistance.setText("(저장된 마트)");
            item.yMartDistance.setPaintFlags(item.yMartDistance.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
            item.yMartCheckbox.setVisibility(View.GONE);
        }else{
            //Log.d("MartAdapter", "안됀애들 : " + curMart.GetMartName());
            item.yMartCheckbox.setVisibility(View.VISIBLE);
            item.yMartTitle.setPaintFlags(item.yMartTitle.getPaintFlags() & ~Paint.FAKE_BOLD_TEXT_FLAG);
            item.yMartDistance.setPaintFlags(item.yMartDistance.getPaintFlags() & ~Paint.FAKE_BOLD_TEXT_FLAG);
        }

        if(!mShowCheckbox) {
            Log.d("MartAdapter", "혹시 여기 ? : ");
            //item.yMartTitle.setPaintFlags(item.yMartTitle.getPaintFlags() & ~Paint.FAKE_BOLD_TEXT_FLAG);
            //item.yMartDistance.setPaintFlags(item.yMartDistance.getPaintFlags() & ~Paint.FAKE_BOLD_TEXT_FLAG);
            //item.yMartCheckbox.setVisibility(View.GONE);
        } else {
            if(curMart.GetSelect() == true){
                item.yMartCheckbox.setChecked(true);
            }
            else{
                item.yMartCheckbox.setChecked(false);
            }
        }

        return convertView;
    }

    private class ViewItem {
        ImageView yMartImageView;
        TextView yMartTitle;
        TextView yMartDistance;

        CheckBox yMartCheckbox;
    }
}