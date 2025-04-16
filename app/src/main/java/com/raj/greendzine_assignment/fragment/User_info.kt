package com.raj.greendzine_assignment.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.raj.greendzine_assignment.Adpater.CustomView_Adpater
import com.raj.greendzine_assignment.Model.User_Model
import com.raj.greendzine_assignment.R
import com.raj.greendzine_assignment.Util.ConnectionManager

class User_info : Fragment() {

    lateinit var listView: ListView
    lateinit var listAdpater: CustomView_Adpater
    lateinit var searchBox:EditText
    lateinit var  progressBar:ProgressBar
    val ConecetionManager = ConnectionManager()

    val Userdata = arrayListOf<User_Model>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)

        listView = view.findViewById(R.id.listview)
        searchBox = view.findViewById(R.id.Et_SearchBox)
        progressBar = view.findViewById(R.id.ProgressBar)


        // Create a Volley request queue
        progressBar.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(activity as Context)
        // Create a JSON request using Volley
        val url = getString(R.string.UrlString)

        if (ConecetionManager.checkConnection(activity as Context)) {

            val JsonOjectRequest = object : JsonObjectRequest(
                Request.Method.GET, url, null, Response.Listener {
                    val data = it.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val userinfo = data.getJSONObject(i)
                        val id = userinfo.getInt("id")
                        val email = userinfo.getString("email")
                        val first_name = userinfo.getString("first_name")
                        val last_name = userinfo.getString("last_name")
                        val avatar = userinfo.getString("avatar")

                        val data_per_user = User_Model(id, email, first_name, last_name, avatar)

                        Userdata.add(data_per_user)
                    }

                    listAdpater = CustomView_Adpater(context, Userdata)
                    listView.adapter = listAdpater
                    progressBar.visibility = View.GONE

                    searchBox.addTextChangedListener {
                        progressBar.visibility = View.VISIBLE
                        val textquery = it.toString().lowercase()
                        val searchname = Userdata.filter { users ->
                            users.first_name.lowercase().startsWith(textquery)

                        }
                        // update the adapter with the filtered list(search functionality =check customAdapter )
                        listAdpater.update_Data(ArrayList(searchname))
                        progressBar.visibility = View.GONE
                    }

                }, Response.ErrorListener {

                    println("error")
                    Toast.makeText(context, "errorUF_REL_Response ", Toast.LENGTH_SHORT).show()

                }) {
                // header or token  usage later
            }

            queue.add(JsonOjectRequest)
        }else{
             val dialog =AlertDialog.Builder(activity as Context)
             dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not found ")
            dialog.setPositiveButton("Open Internet"){text,listner ->
                 val settingInternet = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingInternet)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text,listner ->
                activity?.finish()

            }
            dialog.create()
            dialog.show()
        }
            return view

    }


}
