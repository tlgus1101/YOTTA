package com.example.leehs.yottaproject06.AboutCategoryAndFavor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leehs.yottaproject06.R;

import java.util.ArrayList;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    //Selectctivity에서 받은 데이터를 BaseExpandableAdapter에 있는 변수에 저장을 하는 메소드
    public ExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<String>> childList){

        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public String getGroup(int groupPosition){
        return groupList.get(groupPosition);
    }

    @Override
    public int getGroupCount(){
        return groupList.size();
    }

    @Override
    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.category_group, parent, false);
            viewHolder.tv_groupName = (TextView)v.findViewById(R.id.tv_group);
            viewHolder.tv_childName = (TextView)v.findViewById(R.id.tv_child);
            viewHolder.iv_image = (ImageView)v.findViewById(R.id.iv_image);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        ///////////////////////////////////////이미지 삽입!
        viewHolder.iv_image.setBackgroundColor(Color.WHITE);
        switch (groupPosition) {
            case 0 :
                viewHolder.iv_image.setImageResource(R.drawable.fruits);
                break;
            case 1 :
                viewHolder.iv_image.setImageResource(R.drawable.processed);
                break;
            case 2 :
                viewHolder.iv_image.setImageResource(R.drawable.frozen);
                break;
            case 3 :
                viewHolder.iv_image.setImageResource(R.drawable.milk);
                break;
            case 4 :
                viewHolder.iv_image.setImageResource(R.drawable.seafood);
                break;
            case 5 :
                viewHolder.iv_image.setImageResource(R.drawable.meat);
                break;
        }

        viewHolder.tv_groupName.setText(getGroup(groupPosition));

        return v;
    }

    @Override
    public String getChild(int groupPosition, int childPosition){
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition){
        return childList.get(groupPosition).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.category_group, null);
            viewHolder.tv_childName = (TextView) v.findViewById(R.id.tv_child);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));

        return v;
    }

    @Override
    public boolean hasStableIds(){return true;}

    @Override
    public boolean isChildSelectable(int groupPostion, int childPosition){return true;}



    class ViewHolder{
        public ImageView iv_image;
        public TextView tv_groupName;
        public TextView tv_childName;
    }
}