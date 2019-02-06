package com.example.amanullah.myapplication63;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

public class FoldingAdapter extends ArrayAdapter<Player> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    public FoldingAdapter(Context context, List<Player> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Player item = getItem(position);
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);

            viewHolder.price = (TextView) cell.findViewById(R.id.title_price);
            viewHolder.role = (TextView) cell.findViewById(R.id.title_time_label);
            viewHolder.name = (TextView) cell.findViewById(R.id.title_from_address);
            viewHolder.hall = (TextView) cell.findViewById(R.id.title_to_address);
            viewHolder.totalScore = (TextView) cell.findViewById(R.id.title_requests_count);
            viewHolder.match = (TextView) cell.findViewById(R.id.title_pledge);
            viewHolder.roll = (TextView) cell.findViewById(R.id.title_weight);

            viewHolder.name2 = (TextView) cell.findViewById(R.id.content_name_view);
            viewHolder.totalScore2 = (TextView) cell.findViewById(R.id.content_from_address_1);
            viewHolder.price2 = (TextView) cell.findViewById(R.id.content_to_address_1);
            viewHolder.hall2 = (TextView) cell.findViewById(R.id.content_delivery_time);
            viewHolder.role2 = (TextView) cell.findViewById(R.id.role);
            viewHolder.match2 = (TextView) cell.findViewById(R.id.content_deadline_time);
            viewHolder.roll2 = (TextView) cell.findViewById(R.id.roll);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.content_request_btn);
            viewHolder.contentRequestBtn2 = (TextView) cell.findViewById(R.id.content_request_btn2);

            viewHolder.roleBat = (ImageView) cell.findViewById(R.id.head_image2);
            viewHolder.roleBol = (ImageView) cell.findViewById(R.id.head_image3);
            viewHolder.roleWK = (ImageView) cell.findViewById(R.id.head_image6);
            viewHolder.roleBat2 = (ImageView) cell.findViewById(R.id.head_image4);
            viewHolder.roleBol2 = (ImageView) cell.findViewById(R.id.head_image5);
            viewHolder.head_image_left_text = (TextView) cell.findViewById(R.id.head_image_left_text);
            viewHolder.head_image_right_text = (TextView) cell.findViewById(R.id.head_image_right_text);

            cell.setTag(viewHolder);
        } else {
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        viewHolder.price.setText(item.getPrice()+"");
        viewHolder.totalScore.setText(item.getScore()+"");
        viewHolder.name.setText(item.getName()+"");
        viewHolder.hall.setText(item.getHall()+"");
        viewHolder.role.setText(item.getRole()+"");
        viewHolder.match.setText(item.getMaidens()+"");
        viewHolder.roll.setText(item.getRoll().toString().substring(0,2));

        viewHolder.price2.setText(item.getPrice()+"");
        viewHolder.totalScore2.setText(item.getScore()+"");
        viewHolder.name2.setText(item.getName()+"");
        viewHolder.hall2.setText(item.getHall()+"");
        viewHolder.role2.setText(item.getRole()+"");
        viewHolder.match2.setText(item.getMaidens()+"");
        viewHolder.roll2.setText(item.getRoll()+"");
        viewHolder.head_image_left_text.setText(item.getPrice()+"");
        viewHolder.head_image_right_text.setText(item.getScore()+"");

        if(item.getRole().equals("Batsman")){
            viewHolder.roleBat.setVisibility(View.VISIBLE);
            viewHolder.roleBol.setVisibility(View.GONE);
            viewHolder.roleWK.setVisibility(View.GONE);
            viewHolder.roleBat2.setVisibility(View.GONE);
            viewHolder.roleBol2.setVisibility(View.GONE);
        }else if(item.getRole().equals("Bowler")){
            viewHolder.roleBat.setVisibility(View.GONE);
            viewHolder.roleBol.setVisibility(View.VISIBLE);
            viewHolder.roleWK.setVisibility(View.GONE);
            viewHolder.roleBat2.setVisibility(View.GONE);
            viewHolder.roleBol2.setVisibility(View.GONE);
        }else if(item.getRole().equals("Batsman + WK")){
            viewHolder.roleBat.setVisibility(View.GONE);
            viewHolder.roleBol.setVisibility(View.GONE);
            viewHolder.roleWK.setVisibility(View.VISIBLE);
            viewHolder.roleBat2.setVisibility(View.GONE);
            viewHolder.roleBol2.setVisibility(View.GONE);
        }else {
            viewHolder.roleBat.setVisibility(View.GONE);
            viewHolder.roleBol.setVisibility(View.GONE);
            viewHolder.roleWK.setVisibility(View.GONE);
            viewHolder.roleBat2.setVisibility(View.VISIBLE);
            viewHolder.roleBol2.setVisibility(View.VISIBLE);
        }

            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
            viewHolder.contentRequestBtn2.setOnClickListener(defaultRequestBtnClickListener);


        return cell;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    private static class ViewHolder {
        TextView name;
        TextView totalScore;
        TextView price;
        TextView hall;
        TextView role;
        TextView match;
        TextView roll;

        TextView name2;
        TextView totalScore2;
        TextView price2;
        TextView hall2;
        TextView role2;
        TextView match2;
        TextView roll2;
        TextView contentRequestBtn;
        TextView contentRequestBtn2;
        ImageView roleBat;
        ImageView roleBol;
        ImageView roleWK;
        ImageView roleBat2;
        ImageView roleBol2;
        TextView head_image_right_text;
        TextView head_image_left_text;
    }
}
