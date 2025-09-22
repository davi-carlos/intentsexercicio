package com.example.explorarlocal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.intentsexercicio.R
import com.example.intentsexercicio.ui.theme.IntentsexercicioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntentsexercicioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExplorarLocalScreen()
                }
            }
        }
    }
}

@Composable
fun ExplorarLocalScreen() {
    val context = LocalContext.current
    var local by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo do App",
            modifier = Modifier
                .height(200.dp)
                .width((200.dp))
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Digite um local para explorar:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = local,
            onValueChange = {
                local = it
                errorMessage = ""
            },
            placeholder = { Text("Ex: Rio de Janeiro, São Paulo") },
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (local.isBlank()) {
                    errorMessage = "Por favor, digite um local válido."
                } else {
                    val uri = Uri.parse("geo:0,0?q=${Uri.encode(local)}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        val browserUri =
                            Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(local)}")
                        val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
                        context.startActivity(browserIntent)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Explorar")
        }
    }
}
