package hu.bitraptors.recyclerview.genericlist

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter



class GenericListAdapter(
    vararg adapters: AdapterDelegate<List<GenericListItem>>
) : ListDelegationAdapter<List<GenericListItem>>(*adapters) {

    fun updateData(list: List<GenericListItem>) {
        items = list
        this.notifyDataSetChanged()
    }
}
