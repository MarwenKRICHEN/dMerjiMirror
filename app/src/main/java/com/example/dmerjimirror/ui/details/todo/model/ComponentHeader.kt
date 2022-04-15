package com.example.dmerjimirror.ui.details.todo.model

import com.example.dmerjimirror.library.model.Component
import com.example.dmerjimirror.library.model.Todo


class ComponentHeader(val component: Todo): Items {

    override fun listItemType(): Int {
        return Items.COMPONENT_HEADER
    }
}