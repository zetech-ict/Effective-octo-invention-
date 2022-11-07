package com.shanidev.zesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdIntro extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third_intro, container, false);

        requireActivity().findViewById(R.id.materialButton).setVisibility(View.VISIBLE);

        return view;
    }
}