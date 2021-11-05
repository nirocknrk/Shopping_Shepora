package com.nrk.sephora.assignmentshepora.view.ui.navigation

sealed class Destinations(val route:String) {
    object MainPage : Destinations("MainPage")
    object ProductDetailPage : Destinations("ProductDetail"){
        val argProductId:String = "ProductId"
    }

    fun withArgs(vararg args:String) :String {
        return buildString {
            append(route)
            args.forEach { append("/$it") }
        }
    }
}