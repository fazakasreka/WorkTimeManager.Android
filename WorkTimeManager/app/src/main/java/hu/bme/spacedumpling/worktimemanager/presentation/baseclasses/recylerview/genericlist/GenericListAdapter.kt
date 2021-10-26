package hu.bitraptors.recyclerview.genericlist

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import hu.bme.spacedumpling.worktimemanager.util.safeOffer
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterIsInstance


interface GenericListInteractionListener {
    fun onItemSelected(item: Any)
}

class GenericListAdapter(
    private val itemClickOutput: BroadcastChannel<Any> ,
    vararg adapters: AdapterDelegate<List<GenericListItem>>
) : ListDelegationAdapter<List<GenericListItem>>(*adapters), GenericListInteractionListener {

    val itemClickEvents: Flow<Any> = itemClickOutput.asFlow()

    inline fun <reified T> itemClickEvents(): Flow<T> = itemClickEvents.filterIsInstance()

    fun updateData(list: List<GenericListItem>) {
        items = list
        this.notifyDataSetChanged()
    }

    override fun onItemSelected(item: Any) {
        itemClickOutput.safeOffer(item)
    }
}
