package com.example.colorpickerproject.data.repository

import com.example.colorpickerproject.domain.model.FireStore
import com.example.colorpickerproject.domain.model.Item
import com.google.firebase.firestore.AggregateQuery
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreImpl @Inject constructor(
    val db : FirebaseFirestore
) : FireStore {
    override suspend fun sendColorData(colorData: Item) {
        db.collection("ColorData").document("${colorData.id}").set(colorData).await()
    }

    override suspend fun getUserDataCount(): Long {
        val query = db.collection("ColorData")
        val countQuery: AggregateQuery = query.count()

        val snapshot = countQuery.get(AggregateSource.SERVER).await()

        // Return the count
        return snapshot.count
    }

//    override fun getUserData(): Flow<List<Item>> {
//        return db.collection("ColorData")
//            .snapshotFlow()
//            .map {snapshot ->
//                snapshot.documents.toModelData()
//            }
//    }
}
