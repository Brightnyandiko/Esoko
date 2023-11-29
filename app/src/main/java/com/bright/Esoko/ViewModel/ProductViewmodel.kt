package com.bright.Esoko.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bright.Esoko.Model.Cartitem
import com.bright.Esoko.Model.Order
import com.bright.Esoko.Model.Product
import com.bright.Esoko.Repositery.ProductRepositery
import com.bright.Esoko.Sealed.Cartsealed
import com.bright.Esoko.Sealed.OrderSealed
import com.bright.Esoko.Sealed.ProductSealed
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductViewmodel (private val repositery: ProductRepositery):ViewModel() {


    val response :MutableState<ProductSealed> = mutableStateOf(ProductSealed.Loading)

    val favresponse :MutableState<ProductSealed> = mutableStateOf(ProductSealed.Loading)
    val searchlist :MutableState<List<Product>> = mutableStateOf(emptyList())

    val readcard :MutableState<Cartsealed> = mutableStateOf(Cartsealed.Loading)
    val readorder :MutableState<OrderSealed> = mutableStateOf(OrderSealed.Loading)

    var productitem = mutableStateOf<Product?>(null)



    //Filter
    fun filter(query: String, list: MutableList<Product>)
    {

        val filterlist = mutableListOf<Product>()

       for (item in list)
       {

           if (item.productname.lowercase().contains(query.lowercase()))
           {

               filterlist.add(item)
           }

       }

        if (query == "" || query.isEmpty())
        {
            searchlist.value = list
        }
        else{

            searchlist.value = filterlist
        }




    }

    init {


        readData()
        readFavData()
        readCartData()
        readOrderData()


    }

    fun addProduct(product: Product){

        productitem.value = product


    }


   fun submitdata(product: Product, onSuccess :()->Unit, onFailure:(String)-> Unit) = viewModelScope.launch(Dispatchers.IO) {

       repositery.submitData( product, onSuccess = { onSuccess() }, onFailure = { onFailure(it) })


   }




    fun readData() {

        val list = mutableListOf<Product>()

        Firebase.firestore.collection("Product").get().addOnSuccessListener { querysnapshot ->




            for (document in querysnapshot) {


                 val product = document.toObject(Product::class.java)

                 list.add(product)



            }


            response.value = ProductSealed.Success(list)



        }.addOnFailureListener {


            response.value = ProductSealed.Failure(it.message.toString())

        }



    }





    //OrderProduct

    fun readOrderData(){

        val list = mutableListOf<Order>()

        Firebase.firestore.collection("order").get().addOnSuccessListener { querysnapshot ->




            for (document in querysnapshot) {


                val favproduct = document.toObject(Order::class.java)

                list.add(favproduct)



            }




            readorder.value = OrderSealed.Success(list)


        }.addOnFailureListener {



            readorder.value = OrderSealed.Failure(it.message.toString())

        }

    }

    fun addToOrder(order: Order) = viewModelScope.launch(Dispatchers.IO) {

        repositery.addtoOrder(order)
        readOrderData()

    }

    fun removeToOrder(order: Order) = viewModelScope.launch(Dispatchers.IO) {

        repositery.removeToordrdb(order)
        readOrderData()

    }

    //CartProduct

    fun readCartData(){

        val list = mutableListOf<Cartitem>()

        Firebase.firestore.collection("cart").get().addOnSuccessListener { querysnapshot ->




            for (document in querysnapshot) {


                val favproduct = document.toObject(Cartitem::class.java)

                list.add(favproduct)



            }




            readcard.value = Cartsealed.Success(list)


        }.addOnFailureListener {



            readcard.value = Cartsealed.Failure(it.message.toString())

        }

    }

    fun addtocart(cartitem: Cartitem) = viewModelScope.launch(Dispatchers.IO) {

        repositery.addTocart(cartitem)
        readCartData()

    }

    fun removetocart(cartitem: Cartitem) = viewModelScope.launch(Dispatchers.IO) {

        repositery.removeTocart(cartitem)
        readCartData()

    }



    //FavouriteProduct

    fun readFavData() {

        val list = mutableListOf<Product>()

        Firebase.firestore.collection("Fav").get().addOnSuccessListener { querysnapshot ->




            for (document in querysnapshot) {


                val favproduct = document.toObject(Product::class.java)

                list.add(favproduct)



            }




            favresponse.value = ProductSealed.Success(list)


        }.addOnFailureListener {



            favresponse.value = ProductSealed.Failure(it.message.toString())

        }





    }



    fun favadd(product: Product) = viewModelScope.launch(Dispatchers.IO) {

        repositery.addFav(product)
        readFavData()

    }



    fun removeFav(product: Product) = viewModelScope.launch(Dispatchers.IO) {

        repositery.removeFav(product)
        readFavData()

    }




    class Factory(private val repositery: ProductRepositery) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductViewmodel(repositery) as T
        }
    }

}