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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun AddPatientScreen(NavController: NavController){
    var name by remember { mutableStateOf("")}
    var age by remember { mutableStateOf("")}
    var phone by remember { mutableStateOf("")}
    var illness by remember { mutableStateOf("")}
    var imageUri by remember { mutableStateOf<Uri?>(null)}
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        imageUri = uri
    }
    val PatientViewModel: PatientViewModel = viewModel()
    val context = LocalContext.current

    Scaffold(topBar={
        TopAppBar(title = {Text(text = "Add Patient")},
            colors= TopAppBarDefaults.topAppBarColors(
                containerColor=Color.Blue,
                titleContentColor=Color.White))
    })
    {padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center)
        {
            Box(modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center)
            {
                if(imageUri != null){
                    Image(painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop)
                } else{
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

        Button(onClick = {launcher.launch("image/*")},
            modifier=Modifier.align(Alignment.CenterHorizontally))
        {Text(text = "Select Image")}
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {Text(text = "Patient Name")},
            modifier=Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age,
            onValueChange = {age = it},
            label = {Text(text = "Age")},
            modifier=Modifier.fillMaxWidth()

        )
        OutlinedTextField(
            value = phone,
            onValueChange = {phone = it},
            label = {Text(text = "Phone")},
            modifier = Modifier.fillMaxWidth()

        )
        OutlinedTextField(
            value = illness,
            onValueChange = {illness = it},
            label = {Text(text = "Illness")},
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {PatientViewModel.uploadPatient(
            imageUri,
            name,
            age,
            phone,
            illness,
            context,
            NavController
        ) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)) {
            Text(text = "save patient")

    }}
}}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddPatientScreenPreview(){
    AddPatientScreen(rememberNavController())}

