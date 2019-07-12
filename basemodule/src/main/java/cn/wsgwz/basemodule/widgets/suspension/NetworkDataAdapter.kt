package cn.wsgwz.basemodule.widgets.suspension

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.wsgwz.baselibrary.retrofit.bean.RequestData
import cn.wsgwz.basemodule.NetworkDataActivity
import cn.wsgwz.basemodule.R
import kotlinx.android.synthetic.main.list_item_network_data.view.*

class NetworkDataAdapter(private val context: Context) :
        RecyclerView.Adapter<NetworkDataAdapter.ViewHolder>(), View.OnClickListener {

    companion object {
        private const val TAG = "NetworkDataAdapter"
    }

    private val layoutInflater = LayoutInflater.from(context)
    private val networkDataManager = NetworkDataManager.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.list_item_network_data, parent, false)).apply {
            itemView.parent_cl.setOnClickListener(this@NetworkDataAdapter)
        }
    }

    override fun getItemCount(): Int {
        return networkDataManager.getRequestData().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        networkDataManager.getRequestData()[position].also { item ->
            holder.itemView.parent_cl.tag = item
            holder.itemView.simple_tv.text = "$position\t" + item.uri.toString()

           /* holder.itemView.simple_tv.post {
                Logger.t(TAG).d(holder.itemView.simple_tv.width)


            }*/
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.parent_cl -> {
                context.startActivity(Intent(context, NetworkDataActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("requestData", v.tag as RequestData)
                })
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        /*val parent_cl = itemView.findViewById<ConstraintLayout>(R.id.parent_cl)
        val simple_tv = itemView.findViewById<TextView>(R.id.simple_tv)*/
    }

}