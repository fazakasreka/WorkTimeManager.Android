package hu.bme.spacedumpling.worktimemanager.presentation.baseclasses.recylerview

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import hu.bitraptors.recyclerview.genericlist.GenericListAdapter
import hu.bitraptors.recyclerview.genericlist.GenericListItem
import hu.bitraptors.recyclerview.genericlist.fallbackDelegate
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


fun Fragment.setupRecyclerView(
    recyclerView: RecyclerView,
    items: LiveData<List<GenericListItem>>,
    vararg delegates: AdapterDelegate<List<GenericListItem>>
) {
    //setUpRecycler
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = GenericListAdapter( *delegates, fallbackDelegate())

    //subscribeOnItemChange
    items.observe(this,
        Observer {
            (recyclerView.adapter as? GenericListAdapter)?.updateData(it)
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
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.adapter = GenericListAdapter( *delegates(clickChannel), fallbackDelegate())

    //subscribeOnItemChange
    items.observe(this,
        Observer {
            (recyclerView.adapter as? GenericListAdapter)?.updateData(it)
        }
    )
    //OnClickListener
    clickChannel.asFlow().filterIsInstance<T>().onEach{
        onItemClicked(it)
    }.launchIn(lifecycle.coroutineScope)
}