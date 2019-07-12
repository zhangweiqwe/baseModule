package cn.wsgwz.basemodule.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.data.User
import cn.wsgwz.basemodule.interfaces.listeners.OnUserSelectListener
import cn.wsgwz.basemodule.utilities.InjectorUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserBaseAdapter(context: Context, private val onUserSelectListener: OnUserSelectListener) :
        BaseAdapter(), Filterable, View.OnClickListener {


    interface OnUserSelectListener {
        fun onSelect(user: User)
    }

    private var isSelect = false

    private val layoutInflater = LayoutInflater.from(context)
    private val userRepository = InjectorUtils.provideUserRepository(context)
    private var users: MutableList<User> = runBlocking {
        userRepository.getUsers().toMutableList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        var view: View? = convertView
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_user, parent, false)
            ViewHolder(
                    view.findViewById(R.id.parent_cl),
                    view.findViewById(R.id.user_name_tv),
                    view.findViewById(R.id.delete_iv)
            ).apply {
                view?.tag = this
            }
        } else {
            view.tag as ViewHolder
        }.apply {

            val user = users[position]
            parent_cl.also {
                it.setOnClickListener(this@UserBaseAdapter)
            }
            user_name_tv.text = user.phone
            delete_iv.also {
                it.tag = user
                it.setOnClickListener(this@UserBaseAdapter)
            }

        }

        return view
    }

    override fun getItem(position: Int): Any {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return users.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                //Logger.d("--> $isSelect")

                if (isSelect) {
                    isSelect = false
                    return FilterResults()
                }

                return FilterResults().apply {
                    constraint?.also { constraint ->
                        if (!TextUtils.isEmpty(constraint)) {
                            ArrayList<User>().also { list ->
                                for (i in runBlocking { userRepository.getUsers() }) {
                                    i.phone?.also { phone ->
                                        if (phone.contains(constraint)) {
                                            list.add(i)
                                        }
                                    }
                                }

                                values = list
                                count = list.size
                            }
                        }
                    }
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {


                results.values?.also {
                    users = (it as List<User>).toMutableList()
                }

                notifyDataSetChanged()
            }

        }
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.delete_iv -> {
                val user = v.tag as User
                runBlocking {
                    launch {
                        userRepository.delete(user)
                    }
                    //CoroutineScope(Dispatchers.Main + Job()).launch{}
                }
                users.remove(user)
                notifyDataSetChanged()
            }
            R.id.parent_cl -> {
                val user = v.findViewById<ImageView>(R.id.delete_iv).tag as User
                isSelect = true
                onUserSelectListener.onSelect(user)
            }
        }
    }

    class ViewHolder(val parent_cl: ConstraintLayout, val user_name_tv: TextView, val delete_iv: ImageView)


    companion object {
        private const val TAG = "UserBaseAdapter"
    }
}