package com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class StorageService {
    private val storage = Firebase.storage

    suspend fun uploadImage(uri: Uri, uid: String): String? {
        return try {
            val ref = storage.reference.child("profile_images/$uid.jpg")
            ref.putFile(uri).await()
            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e(StorageService::class.simpleName, "Image upload failed", e)
            null
        }
    }
}
