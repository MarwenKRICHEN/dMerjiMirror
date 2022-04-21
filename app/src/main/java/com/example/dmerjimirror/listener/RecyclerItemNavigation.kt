package com.example.dmerjimirror.listener

import com.example.dmerjimirror.library.model.response.Component

interface RecyclerItemNavigation {
    fun onItemClick(component: Component)
}