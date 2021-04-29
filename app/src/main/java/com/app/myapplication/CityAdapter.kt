package com.app.myapplication

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.myapplication.databinding.AdapterCityBinding

class CityAdapter(var context: MainActivity,var cityList:ArrayList<City>):RecyclerView.Adapter<CityAdapter.MyViewHolder>(),Filterable{
  var cityWithoutFiltered = ArrayList<City>()
    var cityFilter:CityFilter?=null
   init {
       this.cityWithoutFiltered=cityList
   }
    inner class MyViewHolder(var view:AdapterCityBinding):RecyclerView.ViewHolder(view.root) {

        fun bind(city:City){
            view.tvCityValue.text=city.name?:""
            Log.d("CHECK_LOGS","="+city.isSeleted)
            if(city.isSeleted){
                view.cbChecked.isChecked=true
            }else{
                view.cbChecked.isChecked=false
            }
            view.cbChecked.setOnClickListener {
               if(city.isSeleted){
                   city.isSeleted=false
               }else{
                   city.isSeleted=true
               }
                for(k in 0..cityWithoutFiltered.size-1){
                    if(city.name.equals(cityWithoutFiltered.get(k).name)){
                        cityWithoutFiltered.get(k).isSeleted= city.isSeleted
                        break
                    }
                }
                context.updateText(cityWithoutFiltered)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.MyViewHolder {
        var inflater=LayoutInflater.from(parent.context)
        var binding:AdapterCityBinding= DataBindingUtil.inflate(inflater,R.layout.adapter_city,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return cityList.size
    }

    override fun onBindViewHolder(holder: CityAdapter.MyViewHolder, position: Int) {
       holder.bind(cityList.get(position))
    }


    override fun getFilter(): Filter {
      if(cityFilter==null){
          cityFilter= CityFilter()
      }
        return cityFilter!!
    }

   inner class CityFilter:Filter(){
        override fun performFiltering(value: CharSequence?): FilterResults {
            var filterresults=FilterResults()
            var filteredResults= ArrayList<City>()
            if(value!=null&&value.toString().trim().length>0){
              for (i in 0..cityWithoutFiltered.size-1){
               if(cityWithoutFiltered.get(i).name.contains(value)){
                   filteredResults.add(cityWithoutFiltered.get(i))
               }
              }
                filterresults.count=filteredResults.size
                filterresults.values=filteredResults
            }else{
                filterresults.count=cityWithoutFiltered.size
                filterresults.values=cityWithoutFiltered
            }
            return filterresults
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            cityList= p1!!.values as ArrayList<City>
            notifyDataSetChanged()
        }

    }

}