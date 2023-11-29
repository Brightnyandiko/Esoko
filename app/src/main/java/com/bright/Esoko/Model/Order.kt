package com.bright.Esoko.Model

data class Order(
    var id: String = "",
    var productname: String = "",
    var subdescription: String = "",
    var description: String = "",
    var imageUrl: String = "",
    var price: Int = 0,
    var quantity: Int = 1,
    var date: String = ""
)