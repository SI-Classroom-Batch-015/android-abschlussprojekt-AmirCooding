package com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService


import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class FireStorageService {
    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    private fun getProfileImagesRef(): StorageReference {
        return storage.reference.child("profileImages")
    }

    suspend fun uploadProfileImage(imageUri: Uri): String {
        val profileImageRef = getProfileImagesRef().child("${System.currentTimeMillis()}.jpg")
        val uploadTask = profileImageRef.putFile(imageUri).await()
        return profileImageRef.downloadUrl.await().toString()
    }
}
