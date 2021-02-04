package com.gyamfimartins.sample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.math.MathUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

public class GetHeight {

    public void getHeights(final Context context) {
        final   List<Integer> heightList = new ArrayList<>();
        String URLstring = context.getString(R.string.weblink) + "1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                response -> {

                    Log.d("response", ">>" + response);

                    try {

                        JSONObject obj = new JSONObject(response);
                        JSONArray dataArray  = obj.getJSONArray("results");

                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject dataobj = dataArray.getJSONObject(i);
                            int height = dataobj.getInt("height");
                            heightList.add(height);

                        }

                        stats(heightList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show()){

        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void stats(List<Integer> heights){
        if(heights.isEmpty()){
            System.out.println("List is empty");
        } else {
           int minimum = Collections.min(heights);
           int maximum = Collections.max(heights);
           int average = average(heights);
            Log.d("mimimum", ">>" + minimum);
            Log.d("maximum", ">>" + maximum);
            Log.d("average", ">>" + average);
        }

    }


    private int average(List<Integer> heights){
            int sum = 0;
            for (int i : heights) {
                sum+=i;
            }
         return sum/heights.size();
    }


}
