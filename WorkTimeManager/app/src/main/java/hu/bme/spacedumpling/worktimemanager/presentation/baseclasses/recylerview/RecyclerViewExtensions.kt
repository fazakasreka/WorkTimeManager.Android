package hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.recylerview

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import hu.bitraptors.recyclerview.genericlist.GenericListAdapter
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


fun setupRecyclerView(
    recyclerView: RecyclerView,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(recyclerView.context),
    clickChannel: BroadcastChannel<Any>? = null,
    vararg delegates: AdapterDelegate<List<GenericListItem>>,
): GenericListAdapter {
    val chanel = clickChannel ?: BroadcastChannel(Channel.BUFFERED)
    val itemsListAdapter = GenericListAdapter(chanel, *delegates)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = itemsListAdapter
    return itemsListAdapter
}

fun Fragment.setupRecyclerView(
    recyclerView: RecyclerView,
    items: LiveData<List<GenericListItem>>,
    vararg delegates: AdapterDelegate<List<GenericListItem>>
) {
    //setUpAdapter
    val itemsListAdapter = setupRecyclerView(
        recyclerView = recyclerView,
        delegates = *delegates
    )

    //subscribeOnItemChange
    items.observe(this,
        Observer {
            itemsListAdapter.updateData(it)
        }
    )
}

inline fun <reified T : Any> Fragment.setupClickableRecyclerView(
    recyclerView: RecyclerView,
    items: LiveData<List<GenericListItem>>,
    delegates: (clickChannel: SendChannel<Any>) -> Array<AdapterDelegate<List<GenericListItem>>>,
    crossinline onItemClicked: (T) -> Unit
) {
    //make clickChannel
    val clickChannel = BroadcastChannel<Any>(Channel.BUFFERED)

    //setUpAdapter
    val itemsListAdapter = setupRecyclerView(
        recyclerView = recyclerView,
        delegates = *delegates(clickChannel),
        clickChannel = clickChannel
    )

    //subscribeOnItemChange
    items.observe(this,
        Observer {
            itemsListAdapter.updateData(it)
        }
    )
    //OnClickListener
    itemsListAdapter.itemClickEvents<T>().onEach{
        onItemClicked(it)
    }.launchIn(lifecycle.coroutineScope)
}