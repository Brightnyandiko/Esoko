package com.bright.Esoko.Sealed

import com.bright.Esoko.Model.Order

sealed class OrderSealed {

    class Success(val data :MutableList<Order>): OrderSealed()
    class Failure(val message:String): OrderSealed()
    object Loading : OrderSealed()

}