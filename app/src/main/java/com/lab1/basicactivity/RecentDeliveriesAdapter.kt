package com.lab1.basicactivity

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class RecentDeliveriesAdapter(context: Context, fragment: RecentDeliveriesFragment) {

    private var routesRef = FirebaseFirestore.getInstance().collection("Routes")

    init {
        Log.d(Constants.TAG, "creating a RecentDeliveriesAdapter")
        routesRef.addSnapshotListener { snapshot, exception ->
            Log.d(Constants.TAG, "made a snapshot listener")
            if (exception != null) {
                Log.e(Constants.TAG, "Listen error $exception")
                return@addSnapshotListener
            }
            routesRef.orderBy(Route.ROUTE_KEY, Query.Direction.ASCENDING).get().addOnSuccessListener {snapshot: QuerySnapshot? ->
                Log.d(Constants.TAG, "got an orders by route thing")
                var rs = snapshot!!.toObjects(Route::class.java)
                var count: Int = 1
                for (r in rs) {
                    fragment.updateRow(count, r.orderNum, r.name, r.phone)
                    count += 1
                    if (count > 11) {
                        break
                    }
                }

//            for (docChange in snapshot!!.documentChanges) {
//                val h = Hour.fromSnapshot(docChange.document)
//                when (docChange.type) {
//                    DocumentChange.Type.ADDED -> {
//                        pics.add(p!!)
//                        notifyDataSetChanged()
//                    }
//                    DocumentChange.Type.REMOVED -> {
//                        pics.remove(p)
//                        notifyDataSetChanged()
//                    }
//                    DocumentChange.Type.MODIFIED -> {
//                        for ((pos, pic) in pics.withIndex()) {
//                            if (p!!.id == pic!!.id) {
//                                edit(pos, p.caption, p.url)
//                                notifyDataSetChanged()
//                                break
//                            }
//                        }
//                    }
//                }
            }
        }
    }


}