package com.bright.Esoko.Sealed

import com.bright.Esoko.Model.Product

sealed class ProductSealed {

    class Success(val data :MutableList<Product>): ProductSealed()
    class Failure(val message:String): ProductSealed()
    object Loading : ProductSealed()

}