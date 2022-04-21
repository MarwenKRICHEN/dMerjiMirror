package com.example.dmerjimirror.ui.details.todo.model

import com.example.dmerjimirror.library.model.response.Component


class ComponentHeader(val component: Component): Items {

    override fun listItemType(): Int {
        return Items.COMPONENT_HEADER
    }
}