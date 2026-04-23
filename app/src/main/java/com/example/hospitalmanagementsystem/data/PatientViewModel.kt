package com.example.hospitalmanagementsystem.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.hospitalmanagementsystem.models.PatientModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.InputStream

class PatientViewModel : ViewModel() {

    private val cloudinaryUrl = "https://api.cloudinary.com/v1_1/djtr5luf6/image/upload"
    private val uploadPreset = "image_folder"
    private val client = OkHttpClient()

    private val _patients = mutableStateListOf<PatientModel>()
    val patients: List<PatientModel> = _patients

    fun uploadPatient(
        imageUri: Uri?,
        name: String,
        age: String,
        phone: String,
        illness: String,
        gender: String,
        date_of_visit: String,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 1. Upload image only if it exists
                val imageUrl = imageUri?.let { uploadToCloudinary(context, it) }

                // 2. Prepare Firebase Reference
                val ref = FirebaseDatabase.getInstance().getReference("Patients").push()
                val patientId = ref.key ?: System.currentTimeMillis().toString()

                val patientData = mapOf(
                    "id" to patientId,
                    "name" to name,
                    "age" to age,
                    "phone" to phone,
                    "illness" to illness,
                    "imageUrl" to imageUrl,
                    "gender" to gender,
                    "date_of_visit" to date_of_visit
                )

                // 3. Save to Firebase
                ref.setValue(patientData).await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Patient saved successfully", Toast.LENGTH_LONG).show()
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private suspend fun uploadToCloudinary(context: Context, uri: Uri): String = withContext(Dispatchers.IO) {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes() ?: throw Exception("Could not read image file")
        inputStream.close()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "image.jpg", RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes))
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url(cloudinaryUrl)
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        if (response.isSuccessful && responseBody != null) {
            val jsonResponse = JSONObject(responseBody)
            jsonResponse.optString("secure_url") // Returns the URL from Cloudinary
        } else {
            throw Exception("Cloudinary upload failed: ${response.message}")
        }
    }

    fun fetchPatient(context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("Patients")

        ref.get().addOnSuccessListener { snapshot ->
            _patients.clear()
            for (child in snapshot.children) {
                val patient = child.getValue(PatientModel::class.java)
                patient?.let {
                    // Ensure your PatientModel has "var id" so this can be assigned
                    it.id = child.key
                    _patients.add(it)
                }
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to load patients", Toast.LENGTH_LONG).show()
        }
    }

    fun updatePatient(
        patientId: String,
        image: Uri?,
        name: String,
        age: String,
        phone: String,
        illness: String,
        gender: String,
        date_of_visit: String,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl = image?.let { uploadToCloudinary(context, it) }

                val updateMap: MutableMap<String, Any?> = mutableMapOf(
                    "name" to name,
                    "age" to age,
                    "phone" to phone,
                    "illness" to illness,
                    "gender" to gender,
                    "date_of_visit" to date_of_visit
                )

                // Only update imageUrl if a new photo was actually picked
                if (imageUrl != null) {
                    updateMap["imageUrl"] = imageUrl
                }

                FirebaseDatabase.getInstance()
                    .getReference("Patients")
                    .child(patientId)
                    .updateChildren(updateMap)
                    .await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Patient updated successfully", Toast.LENGTH_LONG).show()
                    navController.navigate("dashboard")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Update failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}