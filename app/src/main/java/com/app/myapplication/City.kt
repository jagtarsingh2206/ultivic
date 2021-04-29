package com.app.myapplication

data class City(var name:String,
                var isSeleted:Boolean) {
    var _isSelected
    get() = isSeleted
    set(value) {
        this.isSeleted=value
    }
}