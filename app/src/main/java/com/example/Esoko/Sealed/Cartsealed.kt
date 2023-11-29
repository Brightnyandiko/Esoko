package com.example.Esoko.Sealed

import com.example.Esoko.Model.Cartitem

sealed class Cartsealed {

    class Success(val data :MutableList<Cartitem>):Cartsealed()
    class Failure(val message:String):Cartsealed()
    object Loading :Cartsealed()

}