package com.app.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.myapplication.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    var cityList = ArrayList<City>()
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding.rvCity.layoutManager=LinearLayoutManager(this@MainActivity)
        activityMainBinding.tvSelectedValues.movementMethod=ScrollingMovementMethod()
        for(i in 1..100){
            cityList.add(City("city $i",false))
        }
        var adapter=CityAdapter(this@MainActivity,cityList)
        activityMainBinding.rvCity.adapter=adapter
        activityMainBinding.etSearch.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                adapter.filter.filter(p0)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        activityMainBinding.cbCheckedAll.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1){
                    for(i in 0..cityList.size-1){
                        cityList.get(i).isSeleted=true
                    }
                    adapter.notifyDataSetChanged()
                    activityMainBinding.tvSelectedValues.text="All Selected"
                }else{
                    for(i in 0..cityList.size-1){
                        cityList.get(i).isSeleted=false
                    }
                    adapter.notifyDataSetChanged()
                    activityMainBinding.tvSelectedValues.text=""
                }
            }
        })
    }
    fun updateText(cityList:ArrayList<City>){
        var stringbuilder=StringBuilder()
        for(i in 0..cityList.size-1){
            if(cityList.get(i).isSeleted){
                if(stringbuilder.length==0){
                    stringbuilder.append(cityList.get(i).name)
                }else{
                    stringbuilder.append(",")
                    stringbuilder.append(cityList.get(i).name)
                }
            }
        }
        activityMainBinding.tvSelectedValues.text=stringbuilder.toString()
    }
}