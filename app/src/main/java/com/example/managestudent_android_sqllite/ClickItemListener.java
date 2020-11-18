package com.example.managestudent_android_sqllite;

import com.example.managestudent_android_sqllite.models.Product;

import java.util.ArrayList;

public interface ClickItemListener {
    public void onItemCLick(Product listProduct, int position);
}
