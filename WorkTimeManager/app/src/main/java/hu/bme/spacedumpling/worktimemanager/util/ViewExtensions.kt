package hu.bme.spacedumpling.worktimemanager.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.ListPopupWindow
import hu.bme.spacedumpling.worktimemanager.R

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}
fun View.gone(){
    this.visibility = View.GONE
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View.showDropDownMenu(
    items: List<Any>,
    itemSelected: (Int) -> Unit,
    @LayoutRes cellLayout: Int = R.layout.cell_dropdown_item,
    textId: Int = R.id.doropdown_item_text
) = ListPopupWindow(context).apply {
    isModal = true
    anchorView = this@showDropDownMenu
    inputMethodMode = ListPopupWindow.INPUT_METHOD_NOT_NEEDED

    setAdapter(NoFilterArrayAdapter<Any>(context, cellLayout, items, textId))

    setOnItemClickListener { _, _, position, _ ->
        itemSelected(position)
        dismiss()
    }
}.show()

class NoFilterArrayAdapter<T>(
    context: Context, resource: Int, objects: List<T>, textId: Int=0
) : ArrayAdapter<T>(context, resource, textId, objects) {
    private val items: List<T> = objects

    override fun getFilter(): Filter = NoFilter()

    private inner class NoFilter : Filter() {
        override fun performFiltering(arg0: CharSequence): FilterResults {
            return FilterResults().apply {
                values = items
                count = items.size
            }
        }

        override fun publishResults(
            arg0: CharSequence?,
            arg1: FilterResults
        ) {
        }
    }
}