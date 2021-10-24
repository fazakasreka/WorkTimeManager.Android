package hu.bme.spacedumpling.worktimemanager.presentation.cell.presentationmodels

import androidx.annotation.DrawableRes
import hu.bitraptors.recyclerview.genericlist.GenericListItem

data class TestItem  (
    val title: String,
    @DrawableRes val icon: Int
) : GenericListItem{
    override fun getItemHash(): Int {
        return hashCode()
    }

    override fun getItemId(): String {
        return this::class.java.name+title
    }

}