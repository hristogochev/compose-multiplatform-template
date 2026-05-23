package io.github.hristogochev.template.util.riptide

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import kotlin.jvm.JvmInline

@JvmInline
@Parcelize
value class TabNavigatorHistory(val value: List<String>) : Parcelable {
    fun push(key: String): TabNavigatorHistory {
        val rootKey = value.firstOrNull() ?: return TabNavigatorHistory(value)
        val sub = value.subList(1, value.size)

        val newSub = sub.toMutableList().apply {
            remove(key)

            add(key)

            while (this.isNotEmpty() && this.first() == rootKey) {
                removeFirstOrNull()
            }
        }

        newSub.add(0, rootKey)

        return TabNavigatorHistory(newSub)
    }

    fun pop(): TabNavigatorHistory {
        val rootKey = value.firstOrNull() ?: return TabNavigatorHistory(value)

        val sub = value.subList(1, value.size)

        val newSub = sub.toMutableList().apply {
            removeLastOrNull()
        }

        newSub.add(0, rootKey)

        return TabNavigatorHistory(newSub)
    }
}