package com.bright.Esoko.Sealed

import com.bright.Esoko.Model.Cartitem

sealed class Cartsealed {

    class Success(val data :MutableList<Cartitem>): Cartsealed()
    class Failure(val message:String): Cartsealed()
    object Loading : Cartsealed()

}