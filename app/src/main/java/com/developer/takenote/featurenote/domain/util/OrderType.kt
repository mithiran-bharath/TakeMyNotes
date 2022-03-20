package com.developer.takenote.featurenote.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
