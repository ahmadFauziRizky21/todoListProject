package com.example.todolistproject

class TodoList {
    var name: String? = null
    var desc: String? = null
    var isChecked: Boolean = false

    constructor(name:String?, desc:String?, isChecked: Boolean){
        this.name = name
        this.desc = desc
        this.isChecked = isChecked
    }

}