package com.example.hospitalmanagementsystem.ui.theme.screens.patient


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.hospitalmanagementsystem.data.PatientViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPatientScreen(NavController: NavController) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var illness by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var date_of_visit by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val patientViewModel: PatientViewModel = viewModel()
    val context = LocalContext.current

    // Create the scroll state
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Patient") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState), // ENABLE SCROLLING HERE
            verticalArrangement = Arrangement.Top, // Changed to Top so it scrolls naturally
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { launcher.launch("image/*") }) {
                Text(text = "Select Image")
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Patient Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text(text = "Age") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(text = "Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text(text = "Gender") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = illness,
                onValueChange = { illness = it },
                label = { Text(text = "Illness/Diagnosis") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date_of_visit,
                onValueChange = { date_of_visit = it },
                label = { Text(text = "Date of Visit") },
                modifier = Modifier.fillMaxWidth()
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    patientViewModel.uploadPatient(
                        imageUri,
                        name,
                        age,
                        phone,
                        illness,
                        gender,
                        date_of_visit,
                        context,
                        NavController
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Save Patient")
            }

            // Extra spacer at the bottom so the last field isn't touching the edge
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddPatientScreenPreview(){
        AddPatientScreen(rememberNavController())}

