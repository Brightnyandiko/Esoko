package com.example.Esoko.Sealed

import com.example.Esoko.Model.Order

sealed class OrderSealed {

    class Success(val data :MutableList<Order>):OrderSealed()
    class Failure(val message:String):OrderSealed()
    object Loading :OrderSealed()

}